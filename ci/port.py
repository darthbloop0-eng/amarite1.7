#!/usr/bin/env python3
"""Nest Reach Entity Attributes (already downloaded by Gradle) into the dev jar as a jar-in-jar,
so the player does not have to install it separately. Output: build/bundled/<name>.jar"""
import glob, json, os, sys, zipfile

def find_dep():
    home = os.path.expanduser('~/.gradle/caches/modules-2/files-2.1/com.jamieswhiteshirt')
    c = [p for p in glob.glob(home + '/reach-entity-attributes/*/*/reach-entity-attributes-*.jar')
         if not p.endswith(('-sources.jar', '-javadoc.jar'))]
    return sorted(c)[-1] if c else None

def mod_id(jar):
    with zipfile.ZipFile(jar) as z:
        names = z.namelist()
        if 'fabric.mod.json' in names:
            return json.loads(z.read('fabric.mod.json').decode('utf-8'))['id']
        if 'quilt.mod.json' in names:
            return json.loads(z.read('quilt.mod.json').decode('utf-8'))['quilt_loader']['id']
    raise SystemExit('nested jar has no fabric.mod.json / quilt.mod.json')

def main():
    devs = [p for p in glob.glob('build/devlibs/*.jar') if not p.endswith('-sources.jar')]
    if not devs:
        sys.exit('no jar in build/devlibs')
    src = devs[0]
    dep = find_dep()
    if not dep:
        sys.exit('reach-entity-attributes jar not found in the Gradle cache')
    dep_id = mod_id(dep)
    nested = 'META-INF/jars/' + os.path.basename(dep)
    os.makedirs('build/bundled', exist_ok=True)
    out = 'build/bundled/' + os.path.basename(src).replace('-dev', '-bundled')
    with zipfile.ZipFile(src) as zi, zipfile.ZipFile(out, 'w', zipfile.ZIP_DEFLATED) as zo:
        for it in zi.infolist():
            data = zi.read(it.filename)
            if it.filename == 'quilt.mod.json':
                j = json.loads(data.decode('utf-8'))
                jars = j['quilt_loader'].setdefault('jars', [])
                if nested not in jars:
                    jars.append(nested)
                # nested mods default to load_type if_required: something must depend on them
                deps = j['quilt_loader'].setdefault('depends', [])
                if not any(isinstance(d, dict) and d.get('id') == dep_id for d in deps):
                    deps.append({'id': dep_id, 'versions': '*'})
                data = json.dumps(j, indent=2).encode('utf-8')
            if it.filename != nested:
                zo.writestr(it.filename, data)
        zo.write(dep, nested)
    with zipfile.ZipFile(out) as z:
        n = sum(1 for x in z.namelist() if x.endswith('.class'))
        print('bundled %s (mod id %s) into %s (%d class files, nested %s)' % (os.path.basename(dep), dep_id, out, n, nested))

if __name__ == '__main__':
    main()
