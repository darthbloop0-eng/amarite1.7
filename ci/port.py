#!/usr/bin/env python3
"""CI helper for porting Amarite to 1.20.1.   usage: port.py prepare | summarize"""
import io, json, os, re, sys, zipfile, urllib.request, urllib.error, collections

OUT = []

def log(*a):
    msg = ' '.join(str(x) for x in a)
    print(msg)
    OUT.append(msg)


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
    s += "tasks.withType(JavaCompile).configureEach { options.compilerArgs += ['-Xmaxerrs', '5000', '-XDshould-stop.ifError=FLOW'] }\n"
    write('build.gradle', s)

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
    private double randomMotion = 0.0, randomMotionY = 0.0;
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
    public ParticleBuilders randomMotion(double v) { randomMotion = v; randomMotionY = v; return this; }
    public ParticleBuilders randomMotion(double xz, double y) { randomMotion = xz; randomMotionY = y; return this; }
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
        if (randomMotion > 0.0 || randomMotionY > 0.0) pb.setRandomMotion(randomMotion, randomMotionY);
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

# ----------------------------------------------------------------------------- Quilt mappings lookup
QM_NEW = '1.20.1+build.23'
MAVEN = 'https://maven.quiltmc.org/repository/release/org/quiltmc/quilt-mappings/'

def fetch(url, dest):
    if os.path.exists(dest):
        return dest
    last = None
    for u in (url, url.replace('+', '%2B')):
        try:
            urllib.request.urlretrieve(u, dest)
            return dest
        except urllib.error.URLError as e:
            last = e
    raise RuntimeError('download failed: %s (%s)' % (url, last))

def quilt_jar(version):
    return fetch('%s%s/quilt-mappings-%s-intermediary-v2.jar' % (MAVEN, version, version), 'quilt-%s.jar' % version)

def latest_1192():
    fetch(MAVEN + 'maven-metadata.xml', 'qm-meta.xml')
    vs = re.findall(r'<version>(1\.19\.2\+build\.(\d+))</version>', read('qm-meta.xml'))
    return max(vs, key=lambda t: int(t[1]))[0]

def n_params(desc):
    inner = desc[1:desc.index(')')]
    return len(re.findall(r'\[*(?:[BCDFIJSZ]|L[^;]+;)', inner))

class Mappings:
    """Quilt mappings (intermediary -> named)."""
    def __init__(self, jarpath):
        text = zipfile.ZipFile(jarpath).read('mappings/mappings.tiny').decode('utf-8')
        lines = text.split('\n')
        ns = lines[0].split('\t')[3:]
        ii, ni = ns.index('intermediary'), ns.index('named')
        self.c_n2i, self.c_i2n = {}, {}
        self.fields, self.methods = {}, {}
        cur = None
        for l in lines[1:]:
            p = l.split('\t')
            if p[0] == 'c':
                names = p[1:]
                ci = names[ii]
                cn = names[ni] if ni < len(names) and names[ni] else ci
                self.c_n2i[cn] = ci
                self.c_i2n[ci] = cn
                cur = ci
                self.fields[cur] = []
                self.methods[cur] = []
            elif len(p) >= 5 and p[0] == '' and p[1] in ('m', 'f') and cur:
                names = p[3:]
                mi = names[ii]
                mn = names[ni] if ni < len(names) and names[ni] else mi
                (self.methods if p[1] == 'm' else self.fields)[cur].append((mi, mn, p[2]))

    def cls(self, named):
        return self.c_n2i[named]

    def jname(self, named):
        return self.cls(named).replace('/', '.').replace('$', '.')

    def method(self, cnamed, mnamed, nparams=None, desc_has=None):
        if isinstance(mnamed, (list, tuple)):
            for alt in mnamed:
                try:
                    return self.method(cnamed, alt, nparams, desc_has)
                except KeyError:
                    pass
            raise KeyError('method %s.%s not found' % (cnamed, mnamed))
        ci = self.cls(cnamed)
        c = [mi for (mi, mn, d) in self.methods[ci]
             if mn == mnamed and (nparams is None or n_params(d) == nparams) and (desc_has is None or desc_has in d)]
        if len(c) > 1:
            c = [sorted(c, key=lambda x: int(x.split('_')[1]))[0]]
        if len(c) != 1:
            raise KeyError('method %s.%s nparams=%s desc_has=%s -> %s' % (cnamed, mnamed, nparams, desc_has, c))
        return c[0]

    def field(self, cnamed, fnamed):
        ci = self.cls(cnamed)
        c = [fi for (fi, fn, d) in self.fields[ci] if fn == fnamed]
        if len(c) != 1:
            raise KeyError('field %s.%s -> %s' % (cnamed, fnamed, c))
        return c[0]

def inter_members(src):
    """1.20.1 intermediary: class set, class -> field names, class -> method names."""
    fields, methods, classes = {}, {}, set()
    cur = None
    for l in src[1:]:
        p = l.split('\t')
        if p[0] == 'c' and len(p) >= 3:
            cur = p[2]
            classes.add(cur)
            fields[cur], methods[cur] = set(), set()
        elif len(p) >= 5 and p[0] == '' and cur:
            if p[1] == 'f':
                fields[cur].add(p[4])
            elif p[1] == 'm':
                methods[cur].add(p[4])
    return classes, fields, methods

# ----------------------------------------------------------------------------- text-edit helpers
def remove_method(text, header_regex):
    m = re.search(header_regex, text)
    if not m:
        return text, False
    depth, i = 1, m.end()
    while i < len(text) and depth:
        if text[i] == '{':
            depth += 1
        elif text[i] == '}':
            depth -= 1
        i += 1
    return text[:m.start()] + text[i:], True

def edit(path, fn):
    if not os.path.exists(path):
        return
    t = read(path)
    n = fn(t)
    if n != t:
        write(path, n)

def delete_file(rel):
    p = os.path.join(SRC, rel)
    if os.path.exists(p):
        os.remove(p)
        log('removed', rel)

def drop_mixin_entry(name):
    p = 'src/main/resources/amarite.mixins.json'
    s = read(p)
    s2 = re.sub(r'\s*"%s",?' % re.escape(name), '', s)
    s2 = re.sub(r',(\s*[\]}])', r'\1', s2)
    write(p, s2)

# ----------------------------------------------------------------------------- refmap
def apply_refmap():
    p = 'src/main/resources/amarite-refmap.json'
    if not os.path.exists(p):
        log('WARN no refmap')
        return
    maps = json.loads(read(p))['mappings']
    n = 0
    for cls, entries in maps.items():
        f = os.path.join(SRC, cls + '.java')
        if not os.path.exists(f):
            continue
        t = read(f)
        for k, v in entries.items():
            lit = '"%s"' % k
            if lit in t:
                t = t.replace(lit, '"%s"' % v)
                n += 1
        write(f, t)
    log('refmap strings applied:', n)

# ----------------------------------------------------------------------------- feature rewrites
DT = {'budding': ('no_anger',), 'dash': ('no_anger',), 'accumulate': ('bypasses_armor',),
      'malignancy': ('bypasses_armor', 'bypasses_shield', 'witch_resistant_to'),
      'disc': ('bypasses_armor', 'bypasses_shield', 'witch_resistant_to')}

DAMAGE_TEMPLATE = '''package xyz.amymialee.amarite.registry;

import net.minecraft.class_1282;
import net.minecraft.class_1297;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.Amarite;

public interface AmariteDamageTypes {
@CONSTS@
   private static @RK@<@DTY@> key(String path) {
      return @RK@.@OF@(@RKS@.@FDT@, Amarite.id(path));
   }

   private static class_1282 make(@RK@<@DTY@> key, class_1297... a) {
      class_1297 ref = null;
      for (class_1297 e : a) {
         if (e != null) {
            ref = e;
            break;
         }
      }
      if (ref == null) {
         throw new IllegalStateException("damage source needs at least one entity to find the world");
      }
      net.minecraft.class_1937 world = ref.field_6002;
      if (a.length == 1) {
         return world.@GDS@().@C1@(key, a[0]);
      }
      if (a.length >= 2) {
         return world.@GDS@().@C2@(key, a[0], a[1]);
      }
      return world.@GDS@().@C0@(key);
   }

   static class_1282 buddingFor(@NotNull class_1297 victim) {
      return victim.field_6002.@GDS@().@C0@(BUDDING);
   }
@FUNCS@
   static void init() {
   }
}
'''

def damage_types(Q):
    RK = Q.jname('net/minecraft/registry/RegistryKey')
    DTY = Q.jname('net/minecraft/entity/damage/DamageType')
    RKS = Q.jname('net/minecraft/registry/RegistryKeys')
    DS = 'net/minecraft/entity/damage/DamageSources'
    vals = {
        '@RK@': RK, '@DTY@': DTY, '@RKS@': RKS,
        '@OF@': Q.method('net/minecraft/registry/RegistryKey', 'of', nparams=2, desc_has='class_2960'),
        '@FDT@': Q.field('net/minecraft/registry/RegistryKeys', 'DAMAGE_TYPE'),
        '@GDS@': Q.method('net/minecraft/world/World', 'getDamageSources'),
        '@C0@': Q.method(DS, 'create', nparams=1),
        '@C1@': Q.method(DS, 'create', nparams=2),
        '@C2@': Q.method(DS, 'create', nparams=3),
    }
    consts, funcs = '', ''
    for k in DT:
        consts += '   %s<%s> %s = key("%s");\n' % (RK, DTY, k.upper(), k)
        funcs += '   static class_1282 %s(@NotNull class_1297... a) {\n      return make(%s, a);\n   }\n' % (k, k.upper())
    java = DAMAGE_TEMPLATE.replace('@CONSTS@', consts).replace('@FUNCS@', funcs)
    for k, v in vals.items():
        java = java.replace(k, v)
    write(os.path.join(SRC, 'xyz/amymialee/amarite/registry/AmariteDamageTypes.java'), java)
    tags = collections.defaultdict(list)
    for k, ts in DT.items():
        write('src/main/resources/data/amarite/damage_type/%s.json' % k,
              json.dumps({'message_id': 'amarite.' + k, 'scaling': 'never', 'exhaustion': 0.0}, indent=2) + '\n')
        for t in ts:
            tags[t].append('amarite:' + k)
    for t, v in tags.items():
        write('src/main/resources/data/minecraft/tags/damage_type/%s.json' % t,
              json.dumps({'replace': False, 'values': v}, indent=2) + '\n')
    for p in java_files():
        edit(p, lambda t: re.sub(r'(\w+)\.method_5643\(AmariteDamageTypes\.budding\(\)',
                                 r'\1.method_5643(AmariteDamageTypes.buddingFor(\1)', t))
    base = 'xyz/amymialee/amarite/mixin/'
    edit(os.path.join(SRC, base + 'DamageSourceMixin.java'),
         lambda t: t.replace('@Mixin({class_1282.class, class_1285.class, class_1284.class, class_1286.class})',
                             '@Mixin({class_1282.class})'))
    edit(os.path.join(SRC, base + 'SourcedDamageSourceMixin.java'),
         lambda t: t.replace('@Mixin({class_1285.class, class_1284.class})', '@Mixin({class_1282.class})')
                    .replace(' extends DamageSourceMixin', '')
                    .replace('nameless$removeName', 'nameless$removeAttackerName'))
    log('damage types rewritten for 1.20.1')

GROUP_TEMPLATE = '''package xyz.amymialee.amarite.compat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.class_1761;
import net.minecraft.class_1799;
import net.minecraft.class_2960;

/** Adapter: the old FabricItemGroupBuilder API on top of the 1.20 item group system. */
public final class FabricItemGroupBuilder {
    private final class_2960 id;
    private Supplier<class_1799> icon = () -> class_1799.field_8037;
    private Consumer<List<class_1799>> appender = l -> { };

    private FabricItemGroupBuilder(class_2960 id) { this.id = id; }

    public static FabricItemGroupBuilder create(class_2960 id) { return new FabricItemGroupBuilder(id); }
    public FabricItemGroupBuilder icon(Supplier<class_1799> icon) { this.icon = icon; return this; }
    public FabricItemGroupBuilder appendItems(Consumer<List<class_1799>> appender) { this.appender = appender; return this; }

    public class_1761 build() {
        class_1761 group = net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup.builder()
            .@ICON@(this.icon)
            .@DNAME@(net.minecraft.class_2561.@TRANS@("itemGroup." + this.id.@NS@() + "." + this.id.@PATH@()))
            .@ENTRIES@((ctx, entries) -> {
                List<class_1799> list = new ArrayList<>();
                this.appender.accept(list);
                for (class_1799 stack : list) {
                    entries.@ADD@(stack);
                }
            })
            .@BUILD@();
        @REG@.@REGISTER@(@REGS@.@FIG@, this.id, group);
        return group;
    }
}
'''

def item_groups(Q=None):
    # round 4: hardcoded 1.20.1 intermediary ids (verified against Yarn 1.20.1), no Quilt-name lookups
    vals = {
        '@ICON@': 'method_47320', '@DNAME@': 'method_47321', '@ENTRIES@': 'method_47317', '@BUILD@': 'method_47324',
        '@ADD@': 'method_45420', '@TRANS@': 'method_43471', '@NS@': 'method_12836', '@PATH@': 'method_12832',
        '@REGISTER@': 'method_10230', '@FIG@': 'field_44687',
        '@REGS@': 'net.minecraft.class_7923', '@REG@': 'net.minecraft.class_2378',
    }
    java = GROUP_TEMPLATE
    for k, v in vals.items():
        java = java.replace(k, v)
    write(os.path.join(SRC, 'xyz/amymialee/amarite/compat/FabricItemGroupBuilder.java'), java)
    for p in java_files():
        edit(p, lambda t: t.replace('net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder',
                                    'xyz.amymialee.amarite.compat.FabricItemGroupBuilder'))
    log('item group adapter written (hardcoded ids)')

REG_MIGRATE = {
    'field_16645': 'field_41146', 'field_16792': 'field_41185', 'field_16793': 'field_41162',
    'field_16794': 'field_41161', 'field_17429': 'field_41187', 'field_23398': 'field_41186',
    'field_23781': 'field_41190', 'field_25294': 'field_41134', 'field_35090': 'field_41142',
}

def registry_migrate():
    pat = re.compile(r'\bclass_2378\.(%s)\b' % '|'.join(REG_MIGRATE))
    n = [0]
    def f(t):
        def rep(m):
            n[0] += 1
            return 'class_7923.' + REG_MIGRATE[m.group(1)]
        return pat.sub(rep, t)
    for p in java_files():
        edit(p, f)
    log('hardcoded registry migrations:', n[0])

def migrate_members(fields_new, methods_new, Qnew):
    """Rewrite static references to members that moved in 1.20.1 (e.g. Registry.ITEM -> Registries.ITEM)."""
    try:
        old = Mappings(quilt_jar(latest_1192()))
    except Exception as e:
        log('WARN could not load 1.19.2 mappings:', e)
        return
    new_by_name = collections.defaultdict(list)
    for ci, lst in Qnew.fields.items():
        for (fi, fn, d) in lst:
            new_by_name[fn].append((ci, fi))
    old_f = {(ci, fi): (ci, fn) for ci, lst in old.fields.items() for (fi, fn, d) in lst}
    old_m = {(ci, mi): (ci, mn) for ci, lst in old.methods.items() for (mi, mn, d) in lst}
    pat = re.compile(r'\b(class_\d+)\.((?:field|method)_\d+)\b')
    missing = collections.Counter()
    migrated = collections.Counter()

    def fix(text):
        def rep(m):
            tok, member = m.group(1), m.group(2)
            ci = 'net/minecraft/' + tok
            if ci not in fields_new:
                return m.group(0)
            is_field = member.startswith('field_')
            if member in (fields_new if is_field else methods_new)[ci]:
                return m.group(0)
            if not is_field:
                missing['%s.%s (was %s)' % (tok, member, old_m.get((ci, member), ('?', '?'))[1])] += 1
                return m.group(0)
            oc, fn = old_f.get((ci, member), (None, None))
            if not fn:
                missing['%s.%s (unknown in 1.19.2)' % (tok, member)] += 1
                return m.group(0)
            names = [fn[:-4], fn] if fn.endswith('_KEY') else [fn]
            cands = []
            for nm in names:
                cands = new_by_name.get(nm, [])
                if cands:
                    if fn.endswith('_KEY') and nm == fn[:-4]:
                        cands = [c for c in cands if Qnew.c_i2n.get(c[0], '').endswith('/RegistryKeys')] or cands
                    elif old.c_i2n.get(oc, '').endswith('/Registry'):
                        cands = [c for c in cands if Qnew.c_i2n.get(c[0], '').endswith('/Registries')]
                    break
            cands = [c for c in cands if '$' not in c[0]]
            if len(cands) != 1:
                missing['%s.%s (was %s.%s, %d candidates)' % (tok, member, old.c_i2n.get(oc, '?').split('/')[-1], fn, len(cands))] += 1
                return m.group(0)
            migrated['%s.%s -> %s.%s' % (tok, member, cands[0][0].split('/')[-1], cands[0][1])] += 1
            return '%s.%s' % (cands[0][0].split('/')[-1], cands[0][1])
        return pat.sub(rep, text)

    for p in java_files():
        edit(p, fix)
    log('static member migration: %d rewritten (%d distinct)' % (sum(migrated.values()), len(migrated)))
    for k, v in list(migrated.items())[:20]:
        log('   migrated', v, 'x', k)
    if missing:
        log('STATIC MEMBERS MISSING IN 1.20.1 (%d distinct):' % len(missing))
        for k, v in missing.most_common(60):
            log('   %3d x %s' % (v, k))

def material_fix(text):
    text = re.sub(r'QuiltBlockSettings\.of\(class_3614\.\w+,\s*(class_3620\.\w+)\)', r'QuiltBlockSettings.create().mapColor(\1)', text)
    text = re.sub(r'QuiltBlockSettings\.of\(class_3614\.\w+\)', 'QuiltBlockSettings.create()', text)
    text = re.sub(r'class_2251\.method_9639\(class_3614\.\w+,\s*(class_3620\.\w+)\)', r'QuiltBlockSettings.create().mapColor(\1)', text)
    text = re.sub(r'class_2251\.method_9637\(class_3614\.\w+\)', 'QuiltBlockSettings.create()', text)
    return text

def strip_arsenal(text):
    text = re.sub(r'\n[ \t]*if \(QuiltLoader\.isModLoaded\("arsenal"\)\) \{[^{}]*\}', '', text)
    text = re.sub(r'import xyz\.amymialee\.amarite\.compat\.Amarite(?:ArsenalCompat|LongswordFeatureRenderer);\n', '', text)
    return text

def cleanup_missing_imports(classes):
    """Drop imports of net.minecraft classes that do not exist in 1.20.1."""
    gone = collections.Counter()

    def f(t):
        def rep(m):
            parts = m.group(1).split('.')
            internal = 'net/minecraft/' + '$'.join(parts[2:])
            if internal in classes:
                return m.group(0)
            gone[parts[-1]] += 1
            return ''
        return re.sub(r'^import (net\.minecraft\.class_[\w.]+);\n', rep, t, flags=re.M)

    for p in java_files():
        edit(p, f)
    if gone:
        log('CLASSES THAT NO LONGER EXIST IN 1.20.1 (imports removed):', dict(gone))

# ----------------------------------------------------------------------------- round 3 rules
def remove_call(text, start_token):
    i = text.find(start_token)
    if i < 0:
        return text
    j = text.index('(', i + len(start_token) - 1)
    depth, k = 1, j + 1
    while k < len(text) and depth:
        if text[k] == '(':
            depth += 1
        elif text[k] == ')':
            depth -= 1
        k += 1
    while k < len(text) and text[k] in ' \t;':
        k += 1
    ls = text.rfind('\n', 0, i)
    return text[:ls + 1 if ls >= 0 else 0] + text[k:].lstrip('\n')

def remove_annotated_method(text, marker):
    i = text.find(marker)
    if i < 0:
        return text
    start = max(text.rfind('@Inject', 0, i), text.rfind('@WrapOperation', 0, i), text.rfind('@Redirect', 0, i))
    if start < 0:
        return text
    ls = text.rfind('\n', 0, start)
    b = text.index('{', i)
    depth, k = 1, b + 1
    while k < len(text) and depth:
        if text[k] == '{':
            depth += 1
        elif text[k] == '}':
            depth -= 1
        k += 1
    return text[:ls + 1] + text[k:].lstrip('\n')

MIRROR_STUB = '''package xyz.amymialee.amarite.screens;

import net.minecraft.class_1661;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_465;

/** Stub: the original beacon-style mirror GUI still has to be ported to 1.20 rendering. */
public class MirrorScreen extends class_465<MirrorScreenHandler> {
   public MirrorScreen(MirrorScreenHandler handler, class_1661 inventory, class_2561 title) {
      super(handler, inventory, title);
   }

   protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
   }
}
'''
COMPAT_UTIL = '''package xyz.amymialee.amarite.compat;

public final class Compat {
    private Compat() { }
    public static int packRgb(int r, int g, int b) { return (r << 16) | (g << 8) | b; }
}
'''

def sp(path):
    return os.path.join(SRC, 'xyz/amymialee/' + path)

def model_transform(Qn, old):
    new_i = Qn.cls('net/minecraft/client/render/model/json/ModelTransformationMode')
    old_i = old.cls('net/minecraft/client/render/model/json/ModelTransformation$Mode')
    new_simple = new_i.split('/')[-1]
    old_f = {fi: fn for (fi, fn, d) in old.fields[old_i]}
    new_f = {fn: fi for (fi, fn, d) in Qn.fields[new_i]}
    fmap = {fi: new_f[fn] for fi, fn in old_f.items() if fn in new_f}
    def f(t):
        t = t.replace('net.minecraft.class_809.class_811', 'net.minecraft.' + new_simple)
        t = t.replace('Lnet/minecraft/class_809$class_811;', 'L' + new_i + ';')
        if new_simple != 'class_811':
            t = re.sub(r'\bclass_811\.(field_\d+)\b', lambda m: new_simple + '.' + fmap.get(m.group(1), m.group(1)), t)
            t = re.sub(r'(?<![\w.$])class_811\b', new_simple, t)
        else:
            t = re.sub(r'\bclass_811\.(field_\d+)\b', lambda m: 'class_811.' + fmap.get(m.group(1), m.group(1)), t)
        return t
    for p in java_files():
        edit(p, f)
    log('ModelTransformation.Mode ->', new_i, '(%d constants mapped)' % len(fmap))

def rotation_axis(Qn=None, old=None):
    # Vec3f.POSITIVE_X/Y/Z (field_20703/5/7).getDegreesQuaternion (method_23214) -> RotationAxis (class_7833)
    axes = {'field_20703': 'field_40714', 'field_20705': 'field_40716', 'field_20707': 'field_40718'}
    pat = re.compile(r'class_1160\.(field_20703|field_20705|field_20707)\.method_23214\(')
    n = [0]
    def f(t):
        def rep(m):
            n[0] += 1
            return 'net.minecraft.class_7833.%s.rotationDegrees(' % axes[m.group(1)]
        return pat.sub(rep, t)
    for p in java_files():
        edit(p, f)
    log('Vec3f rotation -> RotationAxis: %d call sites' % n[0])

def vanished_renames(Qn, old, all_f, all_m):
    text = ''.join(read(p) for p in java_files())
    ids_m = set('method_' + x for x in re.findall(r'\bmethod_(\d+)\b', text))
    ids_f = set('field_' + x for x in re.findall(r'\bfield_(\d+)\b', text))
    om = {mi: (ci, mn, d) for ci, lst in old.methods.items() for (mi, mn, d) in lst}
    of = {fi: (ci, fn, d) for ci, lst in old.fields.items() for (fi, fn, d) in lst}
    ALIAS = {'offsetType': ['offset']}
    renames, report = {}, []
    for ids, table, new_tab, kind in ((ids_m, om, Qn.methods, 'm'), (ids_f, of, Qn.fields, 'f')):
        for i in sorted(ids):
            if i in (all_m if kind == 'm' else all_f):
                continue
            if i not in table:
                report.append('%s (unknown in 1.19.2)' % i)
                continue
            ci, mn, d = table[i]
            lst = new_tab.get(ci)
            if lst is None:
                report.append('%s = %s.%s (class gone)' % (i, ci.split('/')[-1], mn))
                continue
            names = [mn] + ALIAS.get(mn, [])
            c = [x[0] for x in lst if x[1] in names and (kind == 'f' or n_params(x[2]) == n_params(d))]
            if len(c) == 1:
                renames[i] = c[0]
            else:
                report.append('%s = %s.%s (%d matches in 1.20.1)' % (i, Qn.c_i2n.get(ci, ci).split('/')[-1], mn, len(c)))
    if renames:
        pat = re.compile(r'\b(%s)\b' % '|'.join(re.escape(k) for k in renames))
        for p in java_files():
            edit(p, lambda t: pat.sub(lambda m: renames[m.group(1)], t))
    log('vanished members auto-renamed:', len(renames))
    if report:
        log('VANISHED MEMBERS WITHOUT AN EQUIVALENT (%d):' % len(report))
        for r in report[:70]:
            log('   ', r)

def step(label, fn, *a):
    try:
        return fn(*a)
    except Exception as e:
        log('WARN sub-step %s failed: %r' % (label, e))

def misc_rewrites(Qn, methods_new):
    # round 4: hardcoded intermediary ids (Yarn 1.20.1) and every sub-step isolated
    getworld = 'method_37908'
    isof = 'method_49708'      # DamageSource.isOf(RegistryKey)
    dtypes = 'class_8111'      # DamageTypes
    sound_of = 'method_47908'  # SoundEvent.of(Identifier)
    try:
        tagexpr = 'source.method_48789(net.minecraft.class_6862.method_40092(%s.%s, new net.minecraft.class_2960("minecraft", "bypasses_shield")))' % (
            Qn.jname('net/minecraft/registry/RegistryKeys'), Qn.field('net/minecraft/registry/RegistryKeys', 'DAMAGE_TYPE'))
    except Exception as e:
        log('WARN no DAMAGE_TYPE key, isUnblockable -> false:', repr(e))
        tagexpr = 'false'
    for p in java_files():
        def f(t, p=p):
            t = re.sub(r'\.field_6002\b(?!\s*=[^=])', '.%s()' % getworld, t)
            t = re.sub(r'(?<![\w.])field_6002\b(?!\s*=[^=])', '%s()' % getworld, t)
            if '/mixin/' in p.replace('\\', '/'):
                t = re.sub(r'\((class_\d+)\)this\b', r'(\1)(Object)this', t)
            t = re.sub(r'Objects\.equals\((\w+)\.field_5841,\s*"amarite\.(\w+)"\)',
                       lambda m: '%s.%s(xyz.amymialee.amarite.registry.AmariteDamageTypes.%s)' % (m.group(1), isof, m.group(2).upper()), t)
            t = re.sub(r'(\w+) == (%s\.field_\d+)' % dtypes, r'\1.%s(\2)' % isof, t)
            t = t.replace('public class_2596<?> method_18002()', 'public class_2596<net.minecraft.class_2602> method_18002()')
            t = re.sub(r'RenderSystem\.(?:disable|enable)Texture\(\);\s*', '', t)
            t = t.replace('class_3532.method_15383(', 'xyz.amymialee.amarite.compat.Compat.packRgb(')
            t = re.sub(r'\.method_8482\(', '.method_49804(', t)
            t = t.replace('!source.method_5504()', '!' + tagexpr)
            if 'extends class_897<' in t or p.endswith('EntityRenderer.java'):
                t = t.replace('public class_2960 getTexture(', 'public class_2960 method_3931(')
            return t
        edit(p, f)
    def groups():
        for rel in ('amarite/registry/AmariteItems.java', 'mialib/MRegistry.java'):
            edit(sp(rel), lambda t: re.sub(r'\.group\(\s*[A-Za-z_][\w.\[\]]*\s*\)', '', t))
    step('group()', groups)
    step('END_DATA_PACK_RELOAD', lambda: edit(sp('amarite/registry/AmariteItems.java'),
         lambda t: remove_call(t, 'ResourceLoaderEvents.END_DATA_PACK_RELOAD.register(')))
    step('DiscEntityRenderer', lambda: edit(sp('amarite/client/DiscEntityRenderer.java'),
         lambda t: t.replace('vertexConsumers, disc.method_5628())', 'vertexConsumers, disc.%s(), disc.method_5628())' % getworld)))
    step('MalignancyRenderer', lambda: edit(sp('amarite/client/MalignancyEntityRenderer.java'),
         lambda t: t.replace('matrixStack, consumers, 0);', 'matrixStack, consumers, malignancy.%s(), 0);' % getworld)))
    step('MRegistry sound/register', lambda: edit(sp('mialib/MRegistry.java'), lambda t: t.replace('new class_3414(id)', 'class_3414.%s(id)' % sound_of)
         .replace('class_2378.method_10230(registry,', 'class_2378.method_10230((class_2378<Object>)(Object)registry,')))
    step('DEFAULT_REGISTRIES', lambda: edit(sp('mialib/MRegistry.java'),
         lambda t: re.sub(r'[ \t]*DEFAULT_REGISTRIES\.put\(class_\d+\.class, class_2378\.field_\d+\);\n', '', t)))
    step('Carnation', lambda: edit(sp('amarite/blocks/YellowCarnationPlantBlock.java'), lambda t: t.replace('method_9651(class_1922 ', 'method_9651(class_4538 ')))
    step('MaskItem', lambda: edit(sp('amarite/items/MaskItem.java'), lambda t: t if 'method_7685' in t else t.replace(
        'public class MaskItem extends TrinketItem implements class_5151 {',
        'public class MaskItem extends TrinketItem implements class_5151 {\n   public net.minecraft.class_1304 method_7685() {\n      return net.minecraft.class_1304.field_6169;\n   }\n', 1)))
    step('budding', lambda: edit(sp('amarite/mixin/BuddingAmethystMixin.java'), lambda t: remove_annotated_method(t, 'amarite$nonBreaking')))
    bst = 'net.minecraft.class_8177.field_42821'   # BlockSetType.STONE
    step('pressure plate', lambda: edit(sp('amarite/blocks/AmethystPressurePlateBlock.java'), lambda t: t.replace('super(settings);', 'super(settings, %s);' % bst)))
    step('button', lambda: edit(sp('amarite/blocks/AmethystButtonBlock.java'),
         lambda t: remove_method(t.replace('super(wooden, settings);', 'super(settings, %s, 20, wooden);' % bst),
                                 r'\n[ \t]*protected class_3414 method_9712\(boolean powered\) \{')[0]))
    cat = 'net.minecraft.class_7710'
    def food(t):
        t = t.replace('public AmariteFoodRecipe(class_2960 identifier) {\n      super(identifier);',
                      'public AmariteFoodRecipe(class_2960 identifier, %s category) {\n      super(identifier, category);' % cat)
        t = t.replace('public boolean matches(@NotNull class_1715 craftingInventory, class_1937 world)',
                      'public boolean method_8115(@NotNull net.minecraft.class_8566 craftingInventory, class_1937 world)')
        t = t.replace('public class_1799 craft(@NotNull class_1715 craftingInventory)',
                      'public class_1799 method_8116(@NotNull net.minecraft.class_8566 craftingInventory, net.minecraft.class_5455 registryManager)')
        return t
    step('food recipe', lambda: edit(sp('amarite/recipes/AmariteFoodRecipe.java'), food))
    # CCA: decompiled "super.clientTick()" must name the interface
    step('DiscComponent', lambda: edit(sp('amarite/cca/DiscComponent.java'),
         lambda t: t.replace('super.clientTick();', 'CommonTickingComponent.super.clientTick();').replace('super.serverTick();', 'CommonTickingComponent.super.serverTick();')))
    # armor material: new abstract methods take ArmorItem.Type (class_1738$class_8051)
    T = 'net.minecraft.class_1738.class_8051'
    ins = ('public class MArmorMaterial implements class_1741 {\n'
           '   public int method_48402(%s type) {\n      return this.armorDurability.apply(type.method_48399());\n   }\n\n'
           '   public int method_48403(%s type) {\n      return this.protection.apply(type.method_48399());\n   }\n') % (T, T)
    step('armor material', lambda: edit(sp('mialib/templates/MArmorMaterial.java'),
         lambda t: t if 'method_48402' in t else t.replace('public class MArmorMaterial implements class_1741 {\n', ins, 1)))
    step('mirror stub', lambda: write(sp('amarite/screens/MirrorScreen.java'), MIRROR_STUB))
    step('Compat', lambda: write(sp('amarite/compat/Compat.java'), COMPAT_UTIL))
    step('accessors', accessor_rewrite)
    log('misc 1.20 rewrites applied')

ACC_BEACON = """package xyz.amymialee.mialib.duck;

import java.util.List;
import net.minecraft.class_1291;
import net.minecraft.class_2580.class_2581;

public interface BeaconBlockEntityAccessor {
   int getLevel();
   class_1291 getPrimary();
   class_1291 getSecondary();
   List<class_2581> getField();
   void setMinY(int v);
   void setLevel(int v);
   void setBeamSegments(List<class_2581> v);
}
"""
ACC_BEACON_MIXIN = """package xyz.amymialee.mialib.mixin.accessors;

import java.util.List;
import net.minecraft.class_1291;
import net.minecraft.class_2580;
import net.minecraft.class_2580.class_2581;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import xyz.amymialee.mialib.duck.BeaconBlockEntityAccessor;

@Mixin({class_2580.class})
public abstract class BeaconBlockEntityAccessorMixin implements BeaconBlockEntityAccessor {
   @Shadow int field_11803;
   @Shadow class_1291 field_11795;
   @Shadow class_1291 field_11799;
   @Shadow int field_19179;
   @Shadow @Mutable @Final List<class_2581> field_19177;
   @Shadow @Mutable @Final List<class_2581> field_19178;

   public int getLevel() { return this.field_11803; }
   public class_1291 getPrimary() { return this.field_11795; }
   public class_1291 getSecondary() { return this.field_11799; }
   public List<class_2581> getField() { return this.field_19178; }
   public void setMinY(int v) { this.field_19179 = v; }
   public void setLevel(int v) { this.field_11803 = v; }
   public void setBeamSegments(List<class_2581> v) { this.field_19177 = v; }
}
"""
ACC_BRM = """package xyz.amymialee.amarite.duck;

import net.minecraft.class_778;

public interface BlockRenderManagerAccessor {
   class_778 getModelRenderer();
}
"""
ACC_BRM_MIXIN = """package xyz.amymialee.amarite.mixin;

import net.minecraft.class_776;
import net.minecraft.class_778;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.amymialee.amarite.duck.BlockRenderManagerAccessor;

@Mixin({class_776.class})
public abstract class BlockRenderManagerAccessorMixin implements BlockRenderManagerAccessor {
   @Shadow class_778 field_4170;

   public class_778 getModelRenderer() { return this.field_4170; }
}
"""

def accessor_rewrite():
    """@Accessor targets were rejected by the Mixin AP; use @Shadow mixin classes + plain duck interfaces."""
    write(sp('mialib/duck/BeaconBlockEntityAccessor.java'), ACC_BEACON)
    write(sp('mialib/mixin/accessors/BeaconBlockEntityAccessorMixin.java'), ACC_BEACON_MIXIN)
    write(sp('amarite/duck/BlockRenderManagerAccessor.java'), ACC_BRM)
    write(sp('amarite/mixin/BlockRenderManagerAccessorMixin.java'), ACC_BRM_MIXIN)
    delete_file('xyz/amymialee/mialib/mixin/accessors/BeaconBlockEntityAccessor.java')
    delete_file('xyz/amymialee/amarite/mixin/BlockRenderManagerAccessor.java')
    for p in java_files():
        edit(p, lambda t: t.replace('xyz.amymialee.mialib.mixin.accessors.BeaconBlockEntityAccessor', 'xyz.amymialee.mialib.duck.BeaconBlockEntityAccessor')
                           .replace('xyz.amymialee.amarite.mixin.BlockRenderManagerAccessor', 'xyz.amymialee.amarite.duck.BlockRenderManagerAccessor'))
    edit('src/main/resources/amarite.mixins.json', lambda t: t.replace('"BlockRenderManagerAccessor"', '"BlockRenderManagerAccessorMixin"'))
    edit('src/main/resources/mialib.mixins.json', lambda t: t.replace('"accessors.BeaconBlockEntityAccessor"', '"accessors.BeaconBlockEntityAccessorMixin"'))
    log('accessors converted to @Shadow mixins + duck interfaces')

def remove_inject_for(text, mid):
    """Remove the whole @Inject method whose target string mentions mid."""
    i = text.find(mid)
    if i < 0:
        return text
    start = text.rfind('@Inject', 0, i)
    if start < 0:
        return text
    p = text.index('(', start)
    depth, k = 1, p + 1
    while k < len(text) and depth:
        if text[k] == '(':
            depth += 1
        elif text[k] == ')':
            depth -= 1
        k += 1
    b = text.index('{', k)
    depth, k = 1, b + 1
    while k < len(text) and depth:
        if text[k] == '{':
            depth += 1
        elif text[k] == '}':
            depth -= 1
        k += 1
    ls = text.rfind('\n', 0, start)
    return text[:ls + 1] + text[k:].lstrip('\n')

def runtime_mixin_cleanup():
    """Mixins whose targets do not exist in 1.20.1 would crash the game on load (defaultRequire 1)."""
    delete_file('xyz/amymialee/amarite/mixin/DamageRecordMixin.java')
    drop_mixin_entry('DamageRecordMixin')
    def f(t):
        for mid in ('method_4026', 'method_4010', 'method_4022'):
            t = remove_inject_for(t, mid)
        return t
    edit(sp('mialib/mixin/client/ItemRendererMixin.java'), f)
    log('removed mixins with vanished targets: DamageRecordMixin, ItemRendererMixin GUI hooks')
    # --- round 5: descriptors inside mixin target strings that changed in 1.20.1 ---
    for p in java_files():
        edit(p, lambda t: t.replace('class_809$class_811', 'class_811'))
    step('NoteBlockMixin', lambda: edit(sp('amarite/mixin/NoteBlockMixin.java'), lambda t: t
        .replace('class_2766;method_11886()Lnet/minecraft/class_3414;', 'class_2766;method_11886()Lnet/minecraft/class_6880;')
        .replace('private class_3414 amarite$squeak(class_2766 instance, Operation<class_3414> original,',
                 'private net.minecraft.class_6880<class_3414> amarite$squeak(class_2766 instance, Operation<net.minecraft.class_6880<class_3414>> original,')
        .replace('? AmariteSoundEvents.WIN_PLUSHIE', '? net.minecraft.class_6880.method_40223(AmariteSoundEvents.WIN_PLUSHIE)')
        .replace('(class_3414)original.call(', '(net.minecraft.class_6880<class_3414>)original.call(')))
    step('EnchantCommandMixin', lambda: edit(sp('mialib/mixin/EnchantCommandMixin.java'),
        lambda t: t.replace('Ljava/util/Collection;Lnet/minecraft/class_1887;I)I', 'Ljava/util/Collection;Lnet/minecraft/class_6880;I)I')))
    log('mixin target descriptors updated (NoteBlock, EnchantCommand, ModelTransformationMode)')

def post_migration_fixes():
    pat = re.compile(r'(\w+) == (class_8111\.field_\d+)')
    n = [0]
    def f(t):
        def rep(m):
            n[0] += 1
            return '%s.method_49708(%s)' % (m.group(1), m.group(2))
        return pat.sub(rep, t)
    for p in java_files():
        edit(p, f)
    log('post-migration DamageSource comparisons fixed:', n[0])

def prepare():
    src = get_inter_tiny()
    make_identity(src)
    patch_gradle()
    classes, fields_new, methods_new = inter_members(src)
    all_f = set().union(*fields_new.values())
    all_m = set().union(*methods_new.values())
    remove_datagen()
    for rel in ('xyz/amymialee/amarite/compat/AmariteArsenalCompat.java',
                'xyz/amymialee/amarite/compat/AmariteLongswordFeatureRenderer.java',
                'xyz/amymialee/amarite/mixin/client/InGameHudMixin.java'):
        delete_file(rel)
    drop_mixin_entry('client.InGameHudMixin')
    for p in java_files():
        edit(p, strip_arsenal)
    apply_refmap()
    if os.path.exists('src/main/resources/amarite-refmap.json'):
        os.remove('src/main/resources/amarite-refmap.json')
    for p in java_files():
        edit(p, material_fix)
    write(os.path.join(SRC, 'xyz/amymialee/amarite/compat/ParticleBuilders.java'), PARTICLE_SHIM)
    write(os.path.join(SRC, 'xyz/amymialee/amarite/compat/Animator.java'), ANIMATOR_SHIM)
    # item-group related edits that need no mapping lookups
    for p in java_files():
        edit(p, lambda t: t.replace('net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder', 'xyz.amymialee.amarite.compat.FabricItemGroupBuilder'))
    edit(sp('amarite/Amarite.java'), lambda t: re.sub(r'\n\s*\.method_7753\("[^"]*"\)', '', t))
    for rel in ('amarite/items/AmariteLongswordItem.java', 'amarite/items/AmariteDiscItem.java'):
        edit(sp(rel), lambda t: remove_method(t, r'\n[ \t]*public void method_7850\(class_1761 group, class_2371<class_1799> stacks\) \{')[0])
    Qn = old = None
    try:
        Qn = Mappings(quilt_jar(QM_NEW))
        old = Mappings(quilt_jar(latest_1192()))
    except Exception as e:
        log('WARN could not load quilt mappings:', repr(e))
    if Qn and old:
        for label, fn, args in (('damage_types', damage_types, (Qn,)), ('item_groups', item_groups, (Qn,)),
                                ('model_transform', model_transform, (Qn, old)), ('rotation_axis', rotation_axis, (Qn, old)),
                                ('registry_migrate', registry_migrate, ()), ('misc_rewrites', misc_rewrites, (Qn, methods_new)),
                                ('runtime_mixin_cleanup', runtime_mixin_cleanup, ())):
            try:
                fn(*args)
            except Exception as e:
                log('WARN step %s failed: %r' % (label, e))
        try:
            migrate_members(fields_new, methods_new, Qn)
            post_migration_fixes()
            vanished_renames(Qn, old, all_f, all_m)
        except Exception as e:
            log('WARN member migration failed: %r' % (e,))
    cleanup_missing_imports(classes)
    idx = class_index(src)
    unresolved = set()
    added = 0
    for p in java_files():
        added += fix_file(p, idx, unresolved)
    log('imports added:', added)
    if unresolved:
        names = {}
        if old:
            names = {u.split(' ')[0]: old.c_i2n.get('net/minecraft/' + u.split(' ')[0], '?') for u in unresolved}
        log('UNRESOLVED class tokens (1.19.2 name):', names or sorted(unresolved))
    write('prepare.out', '\n'.join(OUT) + '\n')


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

    try:
        import glob
        oj = sorted(glob.glob('quilt-1.19.2*.jar'))
        if oj and errs:
            om = Mappings(oj[-1])
            ids = set(re.findall(r'\b((?:method|field)_\d+)\b', ' '.join(s for s in errs.values())))
            tab = {}
            for ci, lst in list(om.methods.items()) + list(om.fields.items()):
                for (mi, mn, d) in lst:
                    if mi in ids:
                        tab[mi] = '%s.%s' % (om.c_i2n.get(ci, ci).split('/')[-1], mn)
            if tab:
                out.append('')
                out.append('--- WHAT THE MISSING NAMES WERE IN 1.19.2 ---')
                for k, v in sorted(tab.items())[:60]:
                    out.append('%s = %s' % (k, v))
    except Exception as e:
        out.append('(hint lookup failed: %r)' % (e,))
    try:
        prep = read('prepare.out').strip().split('\n')
        out.append('')
        out.append('--- PREPARE NOTES ---')
        out += prep[:90]
    except FileNotFoundError:
        pass
    text = '\n'.join(out)
    print(text)
    p = os.environ.get('GITHUB_STEP_SUMMARY')
    if p:
        with open(p, 'a') as f:
            f.write('```\n' + text + '\n```\n')

if __name__ == '__main__':
    {'prepare': prepare, 'summarize': summarize}[sys.argv[1]]()
