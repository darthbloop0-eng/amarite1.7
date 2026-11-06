#!/usr/bin/env python3
"""Nest Reach Entity Attributes (already downloaded by Gradle) into the dev jar as a jar-in-jar,
so the player does not have to install it separately. Output: build/bundled/<name>.jar"""
import glob, json, os, sys, zipfile

def find_dep():
    home = os.path.expanduser('~/.gradle/caches/modules-2/files-2.1/com.jamieswhiteshirt')
    c = [p for p in glob.glob(home + '/reach-entity-attributes/*/*/reach-entity-attributes-*.jar')
         if not p.endswith(('-sources.jar', '-javadoc.jar'))]
    return sorted(c)[-1] if c else None

def main():
    devs = [p for p in glob.glob('build/devlibs/*.jar') if not p.endswith('-sources.jar')]
    if not devs:
        sys.exit('no jar in build/devlibs')
    src = devs[0]
    dep = find_dep()
    if not dep:
        sys.exit('reach-entity-attributes jar not found in the Gradle cache')
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
                data = json.dumps(j, indent=2).encode('utf-8')
            if it.filename != nested:
                zo.writestr(it.filename, data)
        zo.write(dep, nested)
    with zipfile.ZipFile(out) as z:
        n = sum(1 for x in z.namelist() if x.endswith('.class'))
        print('bundled %s into %s (%d class files, nested %s)' % (os.path.basename(dep), out, n, nested))

if __name__ == '__main__':
    main()
