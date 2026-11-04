#!/usr/bin/env python3
"""CI helper for porting Amarite to 1.20.1.   usage: port.py prepare | summarize"""
import io, json, os, re, sys, zipfile, urllib.request, collections

SRC = 'src/main/java'
INTER_URL = 'https://maven.fabricmc.net/net/fabricmc/intermediary/1.20.1/intermediary-1.20.1-v2.jar'

# ----------------------------------------------------------------------------- helpers
def read(p):
    with open(p, encoding='utf-8') as f:
        return f.read()

def write(p, s):
    os.makedirs(os.path.dirname(p) or '.', exist_ok=True)
    with open(p, 'w', encoding='utf-8', newline='\n') as f:
        f.write(s)

def java_files():
    for d, _, fs in os.walk(SRC):
        for f in fs:
            if f.endswith('.java'):
                yield os.path.join(d, f)

# ----------------------------------------------------------------------------- 1. mappings
def get_inter_tiny():
    if not os.path.exists('inter.jar'):
        print('downloading intermediary...')
        urllib.request.urlretrieve(INTER_URL, 'inter.jar')
    return zipfile.ZipFile('inter.jar').read('mappings/mappings.tiny').decode('utf-8').split('\n')

def make_identity(src):
    cmap = {}
    for l in src[1:]:
        p = l.split('\t')
        if p[0] == 'c' and len(p) >= 3 and p[2]:
            cmap[p[1]] = p[2]
    def conv(desc):
        return re.sub(r'L([^;]+);', lambda m: 'L' + cmap.get(m.group(1), m.group(1)) + ';', desc)
    out = ['tiny\t2\t0\tintermediary\tnamed']
    for l in src[1:]:
        p = l.split('\t')
        if p[0] == 'c' and len(p) >= 3 and p[2]:
            out.append('c\t%s\t%s' % (p[2], p[2]))
        elif len(p) >= 5 and p[0] == '' and p[1] in ('m', 'f') and p[4]:
            out.append('\t%s\t%s\t%s\t%s' % (p[1], conv(p[2]), p[4], p[4]))
    os.makedirs('libs', exist_ok=True)
    with zipfile.ZipFile('libs/identity-mappings.jar', 'w') as z:
        z.writestr('mappings/mappings.tiny', '\n'.join(out) + '\n')
    print('identity mappings written:', len(out), 'lines')

def class_index(src):
    """simple name -> import path, for net.minecraft.class_N (top-level and nested)."""
    idx = collections.defaultdict(set)
    for l in src[1:]:
        p = l.split('\t')
        if p[0] == 'c' and len(p) >= 3 and p[2].startswith('net/minecraft/class_'):
            full = p[2][len('net/minecraft/'):]          # class_1$class_2
            parts = full.split('$')
            idx[parts[-1]].add('net.minecraft.' + '.'.join(parts))
    return idx

# ----------------------------------------------------------------------------- 2. build.gradle
def patch_gradle():
    s = read('build.gradle')
    old = "mappings variantOf(libs.quilt.mappings) { classifier 'intermediary-v2' }"
    if old in s:
        s = s.replace(old, 'mappings files("libs/identity-mappings.jar")')
    s = s.replace('release = 21', 'release = 17').replace('VERSION_21', 'VERSION_17')
    s = s.replace('withSourcesJar()', '')
    s += '\ntasks.withType(Jar).configureEach { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }\n'
    s += "tasks.withType(JavaCompile).configureEach { options.compilerArgs += ['-Xmaxerrs', '5000'] }\n"
    write('build.gradle', s)
    if os.path.exists('src/main/resources/amarite-refmap.json'):
        os.remove('src/main/resources/amarite-refmap.json')

# ----------------------------------------------------------------------------- 3. shims
PARTICLE_SHIM = '''package xyz.amymialee.amarite.compat;

import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2396;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import team.lodestar.lodestonedav.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestonedav.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestonedav.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestonedav.systems.rendering.particle.data.SpinParticleData;

/** Adapter: the old Lodestone ParticleBuilders API on top of the lodestonedav fork. */
public final class ParticleBuilders {
    private final class_2396<?> type;
    private int lifetime = 20;
    private float alphaStart = 1.0F, alphaEnd = 0.0F;
    private Easing alphaEasing = Easing.LINEAR;
    private float colorCoefficient = 1.0F;
    private Easing colorEasing = Easing.LINEAR;
    private Easing spinEasing = Easing.LINEAR;
    private float r = 1.0F, g = 1.0F, b = 1.0F;
    private float scaleStart = 0.25F, scaleEnd = 0.0F;
    private float spinOffset = 0.0F, spin = 0.0F;
    private double randomMotion = 0.0;
    private boolean hasMotion = false;
    private double mx, my, mz;
    private boolean hasOffset = false;
    private double offXZ, offY;

    private ParticleBuilders(class_2396<?> type) { this.type = type; }

    public static ParticleBuilders create(class_2396<?> type) { return new ParticleBuilders(type); }

    public ParticleBuilders overrideAnimator(Animator animator) { return this; }
    public ParticleBuilders setLifetime(int v) { lifetime = v; return this; }
    public ParticleBuilders setAlpha(float start, float end) { alphaStart = start; alphaEnd = end; return this; }
    public ParticleBuilders setAlphaEasing(Easing e) { alphaEasing = e; return this; }
    public ParticleBuilders setColorCoefficient(float c) { colorCoefficient = c; return this; }
    public ParticleBuilders setColorEasing(Easing e) { colorEasing = e; return this; }
    public ParticleBuilders setSpinEasing(Easing e) { spinEasing = e; return this; }
    public ParticleBuilders setColor(float r, float g, float b) { this.r = r; this.g = g; this.b = b; return this; }
    public ParticleBuilders setColor(float r, float g, float b, float a) { return setColor(r, g, b); }
    public ParticleBuilders setScale(float start, float end) { scaleStart = start; scaleEnd = end; return this; }
    public ParticleBuilders setSpinOffset(float v) { spinOffset = v; return this; }
    public ParticleBuilders setSpin(float v) { spin = v; return this; }
    public ParticleBuilders randomMotion(double v) { randomMotion = v; return this; }
    public ParticleBuilders setMotion(double x, double y, double z) { hasMotion = true; mx = x; my = y; mz = z; return this; }
    public ParticleBuilders addMotion(double x, double y, double z) { hasMotion = true; mx += x; my += y; mz += z; return this; }
    public ParticleBuilders randomOffset(double xz) { hasOffset = true; offXZ = xz; offY = xz; return this; }
    public ParticleBuilders randomOffset(double xz, double y) { hasOffset = true; offXZ = xz; offY = y; return this; }

    private WorldParticleBuilder build() {
        WorldParticleBuilder pb = WorldParticleBuilder.create(type)
            .setLifetime(lifetime)
            .setScaleData(GenericParticleData.create(scaleStart, scaleEnd).build())
            .setTransparencyData(GenericParticleData.create(alphaStart, alphaEnd).setEasing(alphaEasing).build())
            .setColorData(ColorParticleData.create(r, g, b, r, g, b).setCoefficient(colorCoefficient).setEasing(colorEasing).build())
            .setSpinData(SpinParticleData.create(spin).setSpinOffset(spinOffset).setEasing(spinEasing).build());
        if (randomMotion > 0.0) pb.setRandomMotion(randomMotion);
        if (hasMotion) pb.setMotion(mx, my, mz);
        if (hasOffset) pb.setRandomOffset(offXZ, offY);
        return pb;
    }

    public ParticleBuilders spawn(class_1937 world, double x, double y, double z) {
        build().spawn(world, x, y, z);
        return this;
    }

    public ParticleBuilders spawnAtEdges(class_1937 world, class_2338 pos) {
        build().spawnAtRandomFace(world, pos);
        return this;
    }
}
'''
ANIMATOR_SHIM = '''package xyz.amymialee.amarite.compat;

public enum Animator {
    WITH_AGE
}
'''

# ----------------------------------------------------------------------------- 4. text fixes
CLIENT_FIXES = [
    ('import net.minecraft.util.math.BlockPos;', 'import net.minecraft.class_2338;'),
    ('import net.minecraft.util.math.Vec3d;', 'import net.minecraft.class_243;'),
    ('BlockPos pos = buf.readBlockPos();', 'class_2338 pos = buf.method_10811();'),
    ('Vec3d point = Vec3d.ofCenter(pos);', 'class_243 point = class_243.method_24953(pos);'),
    ('client.world != null', 'client.field_1687 != null'),
    ('client.world.getRandom().method_43048(360)', 'client.field_1687.method_8409().method_43048(360)'),
]
GLOBAL_REPLACE = [
    ('net.minecraft.class_809.class_811', 'net.minecraft.class_811'),
    ('com.sammy.lodestone.systems.rendering.particle.ParticleBuilders', 'xyz.amymialee.amarite.compat.ParticleBuilders'),
    ('com.sammy.lodestone.systems.rendering.particle.SimpleParticleEffect.Animator', 'xyz.amymialee.amarite.compat.Animator'),
    ('com.sammy.lodestone.', 'team.lodestar.lodestonedav.'),
]
KNOWN_IMPORTS = {
    'ParticleBuilders': 'xyz.amymialee.amarite.compat.ParticleBuilders',
    'Animator': 'xyz.amymialee.amarite.compat.Animator',
    'Easing': 'team.lodestar.lodestonedav.systems.rendering.particle.Easing',
    'LodestoneParticles': 'team.lodestar.lodestonedav.setup.LodestoneParticles',
}

def add_imports(text, wanted):
    if not wanted:
        return text
    block = ''.join('import %s;\n' % w for w in sorted(wanted))
    m = re.search(r'^package [^;]+;\s*\n', text, re.M)
    if not m:
        return block + text
    return text[:m.end()] + '\n' + block + text[m.end():]

def fix_file(path, idx, unresolved):
    text = read(path)
    orig = text
    if path.endswith('AmariteClient.java'):
        for a, b in CLIENT_FIXES:
            text = text.replace(a, b)
    for a, b in GLOBAL_REPLACE:
        text = text.replace(a, b)
    text = re.sub(r'\bParticleBuilder\b', 'ParticleBuilders', text)

    imported = set(re.findall(r'^import\s+[\w.$]*\.(\w+);', text, re.M))
    declared = set(re.findall(r'\b(?:class|interface|enum|record)\s+(\w+)', text))
    wanted = set()

    # known library names
    for name, fq in KNOWN_IMPORTS.items():
        if name not in imported and name not in declared and re.search(r'(?<![\w.])%s\b' % name, text) \
                and not path.endswith('compat/ParticleBuilders.java'):
            wanted.add(fq)
            imported.add(name)

    # missing net.minecraft.class_N imports
    for tok in sorted(set(re.findall(r'(?<![\w.$])(class_\d+)\b', text))):
        if tok in imported or tok in declared:
            continue
        cands = idx.get(tok)
        if not cands:
            unresolved.add(tok)
            continue
        if len(cands) > 1:
            tops = [c for c in cands if c.count('.') == 2]
            cands = set(tops) if len(tops) == 1 else cands
        if len(cands) == 1:
            wanted.add(next(iter(cands)))
        else:
            unresolved.add(tok + ' (ambiguous: %s)' % ', '.join(sorted(cands)))
    text = add_imports(text, wanted)
    if text != orig:
        write(path, text)
    return len(wanted)

def remove_datagen():
    for rel in ('xyz/amymialee/amarite/AmariteDatagen.java', 'xyz/amymialee/mialib/data/MDataGen.java'):
        p = os.path.join(SRC, rel)
        if os.path.exists(p):
            os.remove(p)
            print('removed', rel)
    p = 'src/main/resources/quilt.mod.json'
    s = read(p)
    s2 = re.sub(r'"fabric-datagen":\s*"[^"]*",\s*', '', s)
    if s2 != s:
        write(p, s2)
        print('removed datagen entrypoint')

def prepare():
    src = get_inter_tiny()
    make_identity(src)
    patch_gradle()
    remove_datagen()
    write(os.path.join(SRC, 'xyz/amymialee/amarite/compat/ParticleBuilders.java'), PARTICLE_SHIM)
    write(os.path.join(SRC, 'xyz/amymialee/amarite/compat/Animator.java'), ANIMATOR_SHIM)
    idx = class_index(src)
    unresolved = set()
    added = 0
    for p in java_files():
        added += fix_file(p, idx, unresolved)
    print('imports added:', added)
    if unresolved:
        print('UNRESOLVED class tokens:', sorted(unresolved))

# ----------------------------------------------------------------------------- 5. summary
ERR = re.compile(r'^(?P<file>\S+\.java):(?P<line>\d+): error: (?P<msg>.*)$')

def summarize():
    try:
        lines = read('build.log').split('\n')
    except FileNotFoundError:
        print('no build.log'); return
    errs = {}
    i = 0
    while i < len(lines):
        m = ERR.match(lines[i].strip() if lines[i].startswith(' ') else lines[i])
        if m:
            sym = ''
            for j in range(i + 1, min(i + 6, len(lines))):
                if 'symbol:' in lines[j]:
                    sym = lines[j].split('symbol:')[1].strip()
                    break
                if ERR.match(lines[j].strip()):
                    break
            f = m.group('file')
            f = f.split('src/main/java/')[-1]
            key = (f, m.group('line'), m.group('msg'))
            errs[key] = sym
        i += 1
    out = []
    if not errs:
        wrong = []
        for k, l in enumerate(lines):
            if 'What went wrong' in l:
                wrong = lines[k:k + 8]; break
        out.append('No Java compile errors found.')
        out += wrong
    else:
        groups = collections.defaultdict(list)
        for (f, ln, msg), sym in errs.items():
            groups[(msg, sym)].append('%s:%s' % (f.split('/')[-1], ln))
        out.append('TOTAL ERRORS: %d   (%d distinct problems)' % (len(errs), len(groups)))
        out.append('')
        for (msg, sym), locs in sorted(groups.items(), key=lambda kv: -len(kv[1]))[:120]:
            out.append('%4d x  %s%s   e.g. %s' % (len(locs), msg, (' [' + sym + ']') if sym else '', locs[0]))
    text = '\n'.join(out)
    print(text)
    p = os.environ.get('GITHUB_STEP_SUMMARY')
    if p:
        with open(p, 'a') as f:
            f.write('```\n' + text + '\n```\n')

if __name__ == '__main__':
    {'prepare': prepare, 'summarize': summarize}[sys.argv[1]]()
