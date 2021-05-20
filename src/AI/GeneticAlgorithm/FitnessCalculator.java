package AI.GeneticAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * This class provides some helper methods that you could use with assignment 2. You are free to copy or use any code you like
 * from this file.
 * */
public class FitnessCalculator{

    private static int processChar(char cur) {
        int ret;
        if(cur <= 'z' && cur >= 'a') {
            ret = cur - 'a';
        }else if(cur <= 'Z' && cur >= 'A'){
            ret = cur - 'A';
        } else {
            ret = -1;
        }
        return ret;
    }

    //The idea behind this fitness function is fairly simple, it's based on the expected frequency of letter combinations in english.
    //This is only one possible implementation of a fitness function. Feel free to do some research on your own, and test different approaches
    //The general idea in this function is to compare the frequencies which letter combinations occur in the de-shredded document, to the frequencies you would expect to observe in english
    //Smaller values indicate better solutions
    public static double fitness(char[][] shreds, int[] perm) {
        //The expected frequency of different character combinations in english language text according to
        //http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/english-letter-frequencies/
        double[][] expectedFrequencies = new double[26][26];
        expectedFrequencies['t'-'a']['h'-'a'] = 0.027056980400061274; //Expected frequency of th
        expectedFrequencies['h'-'a']['e'-'a'] = 0.0232854497343354; //Expected frequency of he
        expectedFrequencies['i'-'a']['n'-'a'] = 0.020275533912479046; //Expected frequency of in
        expectedFrequencies['e'-'a']['r'-'a'] = 0.017838136076634363; //Expected frequency of er
        expectedFrequencies['a'-'a']['n'-'a'] = 0.016136243079947415; //Expected frequency of an
        expectedFrequencies['r'-'a']['e'-'a'] = 0.014089222456964019; //Expected frequency of re
        expectedFrequencies['e'-'a']['s'-'a'] = 0.013198141738779546; //Expected frequency of es
        expectedFrequencies['o'-'a']['n'-'a'] = 0.013162249877258834; //Expected frequency of on
        expectedFrequencies['s'-'a']['t'-'a'] = 0.012492322191729358; //Expected frequency of st
        expectedFrequencies['n'-'a']['t'-'a'] = 0.011725158252060271; //Expected frequency of nt
        expectedFrequencies['e'-'a']['n'-'a'] = 0.011329747191802887; //Expected frequency of en
        expectedFrequencies['a'-'a']['t'-'a'] = 0.011164000013278053; //Expected frequency of at
        expectedFrequencies['e'-'a']['d'-'a'] = 0.010787830752016612; //Expected frequency of ed
        expectedFrequencies['n'-'a']['d'-'a'] = 0.010682918499219806; //Expected frequency of nd
        expectedFrequencies['t'-'a']['o'-'a'] = 0.010664621630644244; //Expected frequency of to
        expectedFrequencies['o'-'a']['r'-'a'] = 0.010574430727766728; //Expected frequency of or
        expectedFrequencies['e'-'a']['a'-'a'] = 0.010020473709826474; //Expected frequency of ea
        expectedFrequencies['t'-'a']['i'-'a'] = 0.009918454525937884; //Expected frequency of ti
        expectedFrequencies['a'-'a']['r'-'a'] = 0.009794636726918318; //Expected frequency of ar
        expectedFrequencies['t'-'a']['e'-'a'] = 0.009781351042209434; //Expected frequency of te
        expectedFrequencies['n'-'a']['g'-'a'] = 0.008919108277644921; //Expected frequency of ng
        expectedFrequencies['a'-'a']['l'-'a'] = 0.008836830184180957; //Expected frequency of al
        expectedFrequencies['i'-'a']['t'-'a'] = 0.008773684503494425; //Expected frequency of it
        expectedFrequencies['a'-'a']['s'-'a'] = 0.008735606073905992; //Expected frequency of as
        expectedFrequencies['i'-'a']['s'-'a'] = 0.00863757543993427; //Expected frequency of is
        expectedFrequencies['h'-'a']['a'-'a'] = 0.008318866088601775; //Expected frequency of ha
        expectedFrequencies['e'-'a']['t'-'a'] = 0.007602122951633152; //Expected frequency of et
        expectedFrequencies['s'-'a']['e'-'a'] = 0.00729216912299171; //Expected frequency of se
        expectedFrequencies['o'-'a']['u'-'a'] = 0.007195042486331116; //Expected frequency of ou
        expectedFrequencies['o'-'a']['f'-'a'] = 0.0070629048594105116; //Expected frequency of of
        expectedFrequencies['l'-'a']['e'-'a'] = 0.007026448490998915; //Expected frequency of le
        expectedFrequencies['s'-'a']['a'-'a'] = 0.0069563462630839205; //Expected frequency of sa
        expectedFrequencies['v'-'a']['e'-'a'] = 0.006780783001195525; //Expected frequency of ve
        expectedFrequencies['r'-'a']['o'-'a'] = 0.006759922609930308; //Expected frequency of ro
        expectedFrequencies['r'-'a']['a'-'a'] = 0.006624590581664445; //Expected frequency of ra
        expectedFrequencies['r'-'a']['i'-'a'] = 0.006390801475057014; //Expected frequency of ri
        expectedFrequencies['h'-'a']['i'-'a'] = 0.0063585866555539395; //Expected frequency of hi
        expectedFrequencies['n'-'a']['e'-'a'] = 0.006320736942604214; //Expected frequency of ne
        expectedFrequencies['m'-'a']['e'-'a'] = 0.006299011868313592; //Expected frequency of me
        expectedFrequencies['d'-'a']['e'-'a'] = 0.006250933272000211; //Expected frequency of de
        expectedFrequencies['c'-'a']['o'-'a'] = 0.0061832354595479444; //Expected frequency of co
        expectedFrequencies['t'-'a']['a'-'a'] = 0.006046905542206225; //Expected frequency of ta
        expectedFrequencies['e'-'a']['c'-'a'] = 0.005960924043027139; //Expected frequency of ec
        expectedFrequencies['s'-'a']['i'-'a'] = 0.005957002558656506; //Expected frequency of si
        expectedFrequencies['l'-'a']['l'-'a'] = 0.005697536135740754; //Expected frequency of ll
        expectedFrequencies['s'-'a']['o'-'a'] = 0.005527965758559595; //Expected frequency of so
        expectedFrequencies['n'-'a']['a'-'a'] = 0.005445612274171245; //Expected frequency of na
        expectedFrequencies['l'-'a']['i'-'a'] = 0.005386327487603231; //Expected frequency of li
        expectedFrequencies['l'-'a']['a'-'a'] = 0.005360229277177168; //Expected frequency of la
        expectedFrequencies['e'-'a']['l'-'a'] = 0.005340324916836537; //Expected frequency of el
        expectedFrequencies['m'-'a']['a'-'a'] = 0.005048041703325137; //Expected frequency of ma
        expectedFrequencies['d'-'a']['i'-'a'] = 0.005012339706678417; //Expected frequency of di
        expectedFrequencies['i'-'a']['c'-'a'] = 0.004964795784650871; //Expected frequency of ic
        expectedFrequencies['r'-'a']['t'-'a'] = 0.004961939023641823; //Expected frequency of rt
        expectedFrequencies['n'-'a']['s'-'a'] = 0.004927333664306275; //Expected frequency of ns
        expectedFrequencies['r'-'a']['s'-'a'] = 0.004911339225311066; //Expected frequency of rs
        expectedFrequencies['i'-'a']['o'-'a'] = 0.0049050722969062885; //Expected frequency of io
        expectedFrequencies['o'-'a']['m'-'a'] = 0.004871769859251708; //Expected frequency of om
        expectedFrequencies['c'-'a']['h'-'a'] = 0.004655909917018074; //Expected frequency of ch
        expectedFrequencies['o'-'a']['t'-'a'] = 0.004645572109956916; //Expected frequency of ot
        expectedFrequencies['c'-'a']['a'-'a'] = 0.0046091962202008; //Expected frequency of ca
        expectedFrequencies['c'-'a']['e'-'a'] = 0.004579794915992478; //Expected frequency of ce
        expectedFrequencies['h'-'a']['o'-'a'] = 0.004562544501198666; //Expected frequency of ho
        expectedFrequencies['b'-'a']['e'-'a'] = 0.004502292583201862; //Expected frequency of be
        expectedFrequencies['t'-'a']['t'-'a'] = 0.004478931340843644; //Expected frequency of tt
        expectedFrequencies['f'-'a']['o'-'a'] = 0.004376321055106181; //Expected frequency of fo
        expectedFrequencies['t'-'a']['s'-'a'] = 0.004376031979475863; //Expected frequency of ts
        expectedFrequencies['s'-'a']['s'-'a'] = 0.004374453395273826; //Expected frequency of ss
        expectedFrequencies['n'-'a']['o'-'a'] = 0.004369461637289505; //Expected frequency of no
        expectedFrequencies['e'-'a']['e'-'a'] = 0.004277843394579735; //Expected frequency of ee
        expectedFrequencies['e'-'a']['m'-'a'] = 0.004196289840275599; //Expected frequency of em
        expectedFrequencies['a'-'a']['c'-'a'] = 0.004140646019086559; //Expected frequency of ac
        expectedFrequencies['i'-'a']['l'-'a'] = 0.004134382790849851; //Expected frequency of il
        expectedFrequencies['d'-'a']['a'-'a'] = 0.004066497426128634; //Expected frequency of da
        expectedFrequencies['n'-'a']['i'-'a'] = 0.004035982371331825; //Expected frequency of ni
        expectedFrequencies['u'-'a']['r'-'a'] = 0.0040104542180487476; //Expected frequency of ur
        expectedFrequencies['w'-'a']['a'-'a'] = 0.0038941479914678546; //Expected frequency of wa
        expectedFrequencies['s'-'a']['h'-'a'] = 0.0038789618079350125; //Expected frequency of sh
        expectedFrequencies['e'-'a']['i'-'a'] = 0.0037063924445346875; //Expected frequency of ei
        expectedFrequencies['a'-'a']['m'-'a'] = 0.0036946134220110184; //Expected frequency of am
        expectedFrequencies['t'-'a']['r'-'a'] = 0.0036588247026752034; //Expected frequency of tr
        expectedFrequencies['d'-'a']['t'-'a'] = 0.003644589924856862; //Expected frequency of dt
        expectedFrequencies['u'-'a']['s'-'a'] = 0.0036306402912402655; //Expected frequency of us
        expectedFrequencies['l'-'a']['o'-'a'] = 0.003606810515100429; //Expected frequency of lo
        expectedFrequencies['p'-'a']['e'-'a'] = 0.003601493373586623; //Expected frequency of pe
        expectedFrequencies['u'-'a']['n'-'a'] = 0.0035238779544094273; //Expected frequency of un
        expectedFrequencies['n'-'a']['c'-'a'] = 0.003518541387013264; //Expected frequency of nc
        expectedFrequencies['w'-'a']['i'-'a'] = 0.0035181702139039364; //Expected frequency of wi
        expectedFrequencies['u'-'a']['t'-'a'] = 0.003500629335916781; //Expected frequency of ut
        expectedFrequencies['a'-'a']['d'-'a'] = 0.0034405166367435383; //Expected frequency of ad
        expectedFrequencies['e'-'a']['w'-'a'] = 0.0034171991026206243; //Expected frequency of ew
        expectedFrequencies['o'-'a']['w'-'a'] = 0.0033788151779060717; //Expected frequency of ow
        expectedFrequencies['g'-'a']['e'-'a'] = 0.0033359380928543697; //Expected frequency of ge
        expectedFrequencies['e'-'a']['p'-'a'] = 0.0032432844968670544; //Expected frequency of ep
        expectedFrequencies['a'-'a']['i'-'a'] = 0.003231846814847664; //Expected frequency of ai
        expectedFrequencies['l'-'a']['y'-'a'] = 0.0031779890185329777; //Expected frequency of ly
        expectedFrequencies['o'-'a']['l'-'a'] = 0.003174395230296872; //Expected frequency of ol
        expectedFrequencies['f'-'a']['t'-'a'] = 0.0031673619045809974; //Expected frequency of ft
        expectedFrequencies['o'-'a']['s'-'a'] = 0.0031442790998699012; //Expected frequency of os
        expectedFrequencies['e'-'a']['o'-'a'] = 0.003127610073983783; //Expected frequency of eo
        expectedFrequencies['e'-'a']['f'-'a'] = 0.0030647166985073916; //Expected frequency of ef
        expectedFrequencies['p'-'a']['r'-'a'] = 0.003050599401025211; //Expected frequency of pr
        expectedFrequencies['w'-'a']['e'-'a'] = 0.0030491965748064066; //Expected frequency of we
        expectedFrequencies['d'-'a']['o'-'a'] = 0.0030342122816937782; //Expected frequency of do
        expectedFrequencies['m'-'a']['o'-'a'] = 0.0029950011381555093; //Expected frequency of mo
        expectedFrequencies['i'-'a']['d'-'a'] = 0.0029825174648753787; //Expected frequency of id
        expectedFrequencies['i'-'a']['e'-'a'] = 0.0028920388739305716; //Expected frequency of ie
        expectedFrequencies['m'-'a']['i'-'a'] = 0.002814196125677694; //Expected frequency of mi
        expectedFrequencies['p'-'a']['a'-'a'] = 0.0027910157290338025; //Expected frequency of pa
        expectedFrequencies['f'-'a']['i'-'a'] = 0.002773699867517286; //Expected frequency of fi
        expectedFrequencies['p'-'a']['o'-'a'] = 0.0027560551535637206; //Expected frequency of po
        expectedFrequencies['c'-'a']['t'-'a'] = 0.00274939878246978; //Expected frequency of ct
        expectedFrequencies['w'-'a']['h'-'a'] = 0.002741109712215807; //Expected frequency of wh
        expectedFrequencies['i'-'a']['r'-'a'] = 0.002701435585148022; //Expected frequency of ir
        expectedFrequencies['a'-'a']['y'-'a'] = 0.0026649109948876704; //Expected frequency of ay
        expectedFrequencies['g'-'a']['a'-'a'] = 0.00259931904058714; //Expected frequency of ga
        expectedFrequencies['s'-'a']['c'-'a'] = 0.0024977605276230237; //Expected frequency of sc
        expectedFrequencies['k'-'a']['e'-'a'] = 0.0024630793148420803; //Expected frequency of ke
        expectedFrequencies['e'-'a']['v'-'a'] = 0.0024453511158464792; //Expected frequency of ev
        expectedFrequencies['s'-'a']['p'-'a'] = 0.00244456829903958; //Expected frequency of sp
        expectedFrequencies['i'-'a']['m'-'a'] = 0.00243850834878611; //Expected frequency of im
        expectedFrequencies['o'-'a']['p'-'a'] = 0.0024188588375211673; //Expected frequency of op
        expectedFrequencies['d'-'a']['s'-'a'] = 0.0024120209269313875; //Expected frequency of ds
        expectedFrequencies['l'-'a']['d'-'a'] = 0.0023693977659133563; //Expected frequency of ld
        expectedFrequencies['u'-'a']['l'-'a'] = 0.002352721339691102; //Expected frequency of ul
        expectedFrequencies['o'-'a']['o'-'a'] = 0.002351654766245483; //Expected frequency of oo
        expectedFrequencies['s'-'a']['u'-'a'] = 0.0023197752744735763; //Expected frequency of su
        expectedFrequencies['i'-'a']['a'-'a'] = 0.0023130703386737422; //Expected frequency of ia
        expectedFrequencies['g'-'a']['h'-'a'] = 0.0022849460549699105; //Expected frequency of gh
        expectedFrequencies['p'-'a']['l'-'a'] = 0.002269180332613408; //Expected frequency of pl
        expectedFrequencies['e'-'a']['b'-'a'] = 0.0022521993363070515; //Expected frequency of eb
        expectedFrequencies['i'-'a']['g'-'a'] = 0.0022040453490692834; //Expected frequency of ig
        expectedFrequencies['v'-'a']['i'-'a'] = 0.0021692320865404112; //Expected frequency of vi
        expectedFrequencies['i'-'a']['v'-'a'] = 0.002111230795771007; //Expected frequency of iv
        expectedFrequencies['w'-'a']['o'-'a'] = 0.002106007777282433; //Expected frequency of wo
        expectedFrequencies['y'-'a']['o'-'a'] = 0.002101810399130224; //Expected frequency of yo
        expectedFrequencies['r'-'a']['d'-'a'] = 0.0020872733638328225; //Expected frequency of rd
        expectedFrequencies['t'-'a']['w'-'a'] = 0.002060589833070493; //Expected frequency of tw
        expectedFrequencies['b'-'a']['a'-'a'] = 0.002050693502311955; //Expected frequency of ba
        expectedFrequencies['a'-'a']['g'-'a'] = 0.0020372352972668983; //Expected frequency of ag
        expectedFrequencies['r'-'a']['y'-'a'] = 0.002032441960795227; //Expected frequency of ry
        expectedFrequencies['a'-'a']['b'-'a'] = 0.002029445518441609; //Expected frequency of ab
        expectedFrequencies['l'-'a']['s'-'a'] = 0.0020062894041506644; //Expected frequency of ls
        expectedFrequencies['s'-'a']['w'-'a'] = 0.0020057764683522293; //Expected frequency of sw
        expectedFrequencies['a'-'a']['p'-'a'] = 0.001978181771203139; //Expected frequency of ap
        expectedFrequencies['f'-'a']['e'-'a'] = 0.001972487675067399; //Expected frequency of fe
        expectedFrequencies['t'-'a']['u'-'a'] = 0.0019605097685100716; //Expected frequency of tu
        expectedFrequencies['c'-'a']['i'-'a'] = 0.0019532456448109517; //Expected frequency of ci
        expectedFrequencies['f'-'a']['a'-'a'] = 0.0019328588750584475; //Expected frequency of fa
        expectedFrequencies['h'-'a']['t'-'a'] = 0.001931383895562316; //Expected frequency of ht
        expectedFrequencies['f'-'a']['r'-'a'] = 0.0019285682989230244; //Expected frequency of fr
        expectedFrequencies['a'-'a']['v'-'a'] = 0.00191689172480274; //Expected frequency of av
        expectedFrequencies['e'-'a']['g'-'a'] = 0.001916331611861437; //Expected frequency of eg
        expectedFrequencies['g'-'a']['o'-'a'] = 0.0018937247412680951; //Expected frequency of go
        expectedFrequencies['b'-'a']['o'-'a'] = 0.001889952188662201; //Expected frequency of bo
        expectedFrequencies['b'-'a']['u'-'a'] = 0.0018762791426086922; //Expected frequency of bu
        expectedFrequencies['t'-'a']['y'-'a'] = 0.0018521464152082832; //Expected frequency of ty
        expectedFrequencies['m'-'a']['p'-'a'] = 0.0018119658276361818; //Expected frequency of mp
        expectedFrequencies['o'-'a']['c'-'a'] = 0.0017684379755255094; //Expected frequency of oc
        expectedFrequencies['o'-'a']['d'-'a'] = 0.001759941927120229; //Expected frequency of od
        expectedFrequencies['e'-'a']['h'-'a'] = 0.0017481307593864685; //Expected frequency of eh
        expectedFrequencies['y'-'a']['s'-'a'] = 0.001743616554343432; //Expected frequency of ys
        expectedFrequencies['e'-'a']['y'-'a'] = 0.0017410081671159522; //Expected frequency of ey
        expectedFrequencies['r'-'a']['m'-'a'] = 0.0017062374565198627; //Expected frequency of rm
        expectedFrequencies['o'-'a']['v'-'a'] = 0.0016997679439133594; //Expected frequency of ov
        expectedFrequencies['g'-'a']['t'-'a'] = 0.0016992998726527494; //Expected frequency of gt
        expectedFrequencies['y'-'a']['a'-'a'] = 0.001674221521050446; //Expected frequency of ya
        expectedFrequencies['c'-'a']['k'-'a'] = 0.0016662529778553687; //Expected frequency of ck
        expectedFrequencies['g'-'a']['i'-'a'] = 0.0016426757381861775; //Expected frequency of gi
        expectedFrequencies['r'-'a']['n'-'a'] = 0.0016337710524698804; //Expected frequency of rn
        expectedFrequencies['g'-'a']['r'-'a'] = 0.0016165023680962318; //Expected frequency of gr
        expectedFrequencies['r'-'a']['c'-'a'] = 0.0016128253260785945; //Expected frequency of rc
        expectedFrequencies['b'-'a']['l'-'a'] = 0.0016051893354886344; //Expected frequency of bl
        expectedFrequencies['l'-'a']['t'-'a'] = 0.0015765659916166226; //Expected frequency of lt
        expectedFrequencies['y'-'a']['t'-'a'] = 0.00155271794589695; //Expected frequency of yt
        expectedFrequencies['o'-'a']['a'-'a'] = 0.001515732453451621; //Expected frequency of oa
        expectedFrequencies['y'-'a']['e'-'a'] = 0.0015030325516000127; //Expected frequency of ye
        expectedFrequencies['o'-'a']['b'-'a'] = 0.0014367086578035188; //Expected frequency of ob
        expectedFrequencies['d'-'a']['b'-'a'] = 0.001412242915276984; //Expected frequency of db
        expectedFrequencies['f'-'a']['f'-'a'] = 0.0014073401925868009; //Expected frequency of ff
        expectedFrequencies['s'-'a']['f'-'a'] = 0.0014046751465357787; //Expected frequency of sf
        expectedFrequencies['r'-'a']['r'-'a'] = 0.0013635609603080043; //Expected frequency of rr
        expectedFrequencies['d'-'a']['u'-'a'] = 0.0013554897374490383; //Expected frequency of du
        expectedFrequencies['k'-'a']['i'-'a'] = 0.0013446311317323; //Expected frequency of ki
        expectedFrequencies['u'-'a']['c'-'a'] = 0.0013279868507201369; //Expected frequency of uc
        expectedFrequencies['i'-'a']['f'-'a'] = 0.0013275310362662524; //Expected frequency of if
        expectedFrequencies['a'-'a']['f'-'a'] = 0.0013187785199617543; //Expected frequency of af
        expectedFrequencies['d'-'a']['r'-'a'] = 0.0013186194127348277; //Expected frequency of dr
        expectedFrequencies['c'-'a']['l'-'a'] = 0.0013143006228178858; //Expected frequency of cl
        expectedFrequencies['e'-'a']['x'-'a'] = 0.001306474536093429; //Expected frequency of ex
        expectedFrequencies['s'-'a']['m'-'a'] = 0.001290608215417576; //Expected frequency of sm
        expectedFrequencies['p'-'a']['i'-'a'] = 0.0012856257078534253; //Expected frequency of pi
        expectedFrequencies['s'-'a']['b'-'a'] = 0.0012843477623069182; //Expected frequency of sb
        expectedFrequencies['c'-'a']['r'-'a'] = 0.0012752506678510819; //Expected frequency of cr
        expectedFrequencies['t'-'a']['l'-'a'] = 0.0012495321871730035; //Expected frequency of tl
        expectedFrequencies['o'-'a']['i'-'a'] = 0.0012341485071695286; //Expected frequency of oi
        expectedFrequencies['r'-'a']['u'-'a'] = 0.0012327472997742542; //Expected frequency of ru
        expectedFrequencies['u'-'a']['p'-'a'] = 0.0012272874705293235; //Expected frequency of up
        expectedFrequencies['b'-'a']['y'-'a'] = 0.0012099720715338155; //Expected frequency of by
        expectedFrequencies['t'-'a']['c'-'a'] = 0.0012018185199353351; //Expected frequency of tc
        expectedFrequencies['n'-'a']['n'-'a'] = 0.0011981373152286214; //Expected frequency of nn
        expectedFrequencies['a'-'a']['k'-'a'] = 0.0011880571323692015; //Expected frequency of ak
        expectedFrequencies['s'-'a']['l'-'a'] = 0.0011482111787467556; //Expected frequency of sl
        expectedFrequencies['n'-'a']['f'-'a'] = 0.0011448165058048124; //Expected frequency of nf
        expectedFrequencies['u'-'a']['e'-'a'] = 0.0011396140695011162; //Expected frequency of ue
        expectedFrequencies['d'-'a']['w'-'a'] = 0.0011347522799201861; //Expected frequency of dw
        expectedFrequencies['a'-'a']['u'-'a'] = 0.001129515154540852; //Expected frequency of au
        expectedFrequencies['p'-'a']['p'-'a'] = 0.0011270233226075158; //Expected frequency of pp
        expectedFrequencies['u'-'a']['g'-'a'] = 0.001117525916218816; //Expected frequency of ug
        expectedFrequencies['r'-'a']['l'-'a'] = 0.0011108010920156163; //Expected frequency of rl
        expectedFrequencies['r'-'a']['g'-'a'] = 0.0010744219646124408; //Expected frequency of rg
        expectedFrequencies['b'-'a']['r'-'a'] = 0.0010686732909976969; //Expected frequency of br
        expectedFrequencies['c'-'a']['u'-'a'] = 0.0010647337683077315; //Expected frequency of cu
        expectedFrequencies['u'-'a']['a'-'a'] = 0.0010614850207439723; //Expected frequency of ua
        expectedFrequencies['d'-'a']['h'-'a'] = 0.0010605063262899698; //Expected frequency of dh
        expectedFrequencies['r'-'a']['k'-'a'] = 0.001038683428806049; //Expected frequency of rk
        expectedFrequencies['y'-'a']['i'-'a'] = 0.00103170259922464; //Expected frequency of yi
        expectedFrequencies['l'-'a']['u'-'a'] = 0.0010182261245997473; //Expected frequency of lu
        expectedFrequencies['u'-'a']['m'-'a'] = 0.0010151688607335104; //Expected frequency of um
        expectedFrequencies['b'-'a']['i'-'a'] = 0.0010074775988830336; //Expected frequency of bi
        expectedFrequencies['n'-'a']['y'-'a'] = 0.001004431435521001; //Expected frequency of ny
        expectedFrequencies['n'-'a']['w'-'a'] = 0.0009749866543378793; //Expected frequency of nw
        expectedFrequencies['q'-'a']['u'-'a'] = 0.0009642230966883892; //Expected frequency of qu
        expectedFrequencies['o'-'a']['g'-'a'] = 0.0009627666180325981; //Expected frequency of og
        expectedFrequencies['s'-'a']['n'-'a'] = 0.00096157886408275; //Expected frequency of sn
        expectedFrequencies['m'-'a']['b'-'a'] = 0.0009532012210556475; //Expected frequency of mb
        expectedFrequencies['v'-'a']['a'-'a'] = 0.0009507986556769535; //Expected frequency of va
        expectedFrequencies['d'-'a']['f'-'a'] = 0.0009328766603787876; //Expected frequency of df
        expectedFrequencies['d'-'a']['d'-'a'] = 0.0009253368741585971; //Expected frequency of dd
        expectedFrequencies['m'-'a']['s'-'a'] = 0.0009072014254150048; //Expected frequency of ms
        expectedFrequencies['g'-'a']['s'-'a'] = 0.0009066972775157312; //Expected frequency of gs
        expectedFrequencies['a'-'a']['w'-'a'] = 0.0009063006657509358; //Expected frequency of aw
        expectedFrequencies['n'-'a']['h'-'a'] = 0.0009054796909608344; //Expected frequency of nh
        expectedFrequencies['p'-'a']['u'-'a'] = 0.0008922372519662465; //Expected frequency of pu
        expectedFrequencies['h'-'a']['r'-'a'] = 0.0008887343491083124; //Expected frequency of hr
        expectedFrequencies['s'-'a']['d'-'a'] = 0.0008885606724696177; //Expected frequency of sd
        expectedFrequencies['t'-'a']['b'-'a'] = 0.000882364972300151; //Expected frequency of tb
        expectedFrequencies['p'-'a']['t'-'a'] = 0.0008816748909554573; //Expected frequency of pt
        expectedFrequencies['n'-'a']['m'-'a'] = 0.0008780794838958217; //Expected frequency of nm
        expectedFrequencies['d'-'a']['c'-'a'] = 0.0008747384633908653; //Expected frequency of dc
        expectedFrequencies['g'-'a']['u'-'a'] = 0.0008714890220455935; //Expected frequency of gu
        expectedFrequencies['t'-'a']['m'-'a'] = 0.0008695073507846416; //Expected frequency of tm
        expectedFrequencies['m'-'a']['u'-'a'] = 0.0008685760647340111; //Expected frequency of mu
        expectedFrequencies['n'-'a']['u'-'a'] = 0.0008632034206991841; //Expected frequency of nu
        expectedFrequencies['m'-'a']['m'-'a'] = 0.0008627191612032764; //Expected frequency of mm
        expectedFrequencies['n'-'a']['l'-'a'] = 0.0008540415733021567; //Expected frequency of nl
        expectedFrequencies['e'-'a']['u'-'a'] = 0.0008496811564944489; //Expected frequency of eu
        expectedFrequencies['w'-'a']['n'-'a'] = 0.0008440118052326642; //Expected frequency of wn
        expectedFrequencies['n'-'a']['b'-'a'] = 0.0008331603685915576; //Expected frequency of nb
        expectedFrequencies['r'-'a']['p'-'a'] = 0.0008298061662378588; //Expected frequency of rp
        expectedFrequencies['d'-'a']['m'-'a'] = 0.0008197965178322364; //Expected frequency of dm
        expectedFrequencies['s'-'a']['r'-'a'] = 0.0008126050099314522; //Expected frequency of sr
        expectedFrequencies['u'-'a']['d'-'a'] = 0.0008093042287542361; //Expected frequency of ud
        expectedFrequencies['u'-'a']['i'-'a'] = 0.0008051292828709401; //Expected frequency of ui
        expectedFrequencies['r'-'a']['f'-'a'] = 0.0007946647450534503; //Expected frequency of rf
        expectedFrequencies['o'-'a']['k'-'a'] = 0.0007857237514379853; //Expected frequency of ok
        expectedFrequencies['y'-'a']['w'-'a'] = 0.0007814440445462624; //Expected frequency of yw
        expectedFrequencies['t'-'a']['f'-'a'] = 0.0007789899080751198; //Expected frequency of tf
        expectedFrequencies['i'-'a']['p'-'a'] = 0.0007744037810152603; //Expected frequency of ip
        expectedFrequencies['r'-'a']['w'-'a'] = 0.0007742613245446399; //Expected frequency of rw
        expectedFrequencies['r'-'a']['b'-'a'] = 0.0007738466744605126; //Expected frequency of rb
        expectedFrequencies['o'-'a']['h'-'a'] = 0.0007526740815145536; //Expected frequency of oh
        expectedFrequencies['k'-'a']['s'-'a'] = 0.000746354656975311; //Expected frequency of ks
        expectedFrequencies['d'-'a']['p'-'a'] = 0.0007273242300802561; //Expected frequency of dp
        expectedFrequencies['f'-'a']['u'-'a'] = 0.0007259035968026243; //Expected frequency of fu
        expectedFrequencies['y'-'a']['c'-'a'] = 0.000723395114112982; //Expected frequency of yc
        expectedFrequencies['t'-'a']['p'-'a'] = 0.0007100684962948457; //Expected frequency of tp
        expectedFrequencies['m'-'a']['t'-'a'] = 0.0007067196129327448; //Expected frequency of mt
        expectedFrequencies['d'-'a']['l'-'a'] = 0.0007055630791509709; //Expected frequency of dl
        expectedFrequencies['n'-'a']['k'-'a'] = 0.0007037719665455243; //Expected frequency of nk
        expectedFrequencies['c'-'a']['c'-'a'] = 0.0006999080660404499; //Expected frequency of cc
        expectedFrequencies['u'-'a']['b'-'a'] = 0.0006916696418369083; //Expected frequency of ub
        expectedFrequencies['r'-'a']['h'-'a'] = 0.0006865444465416329; //Expected frequency of rh
        expectedFrequencies['n'-'a']['p'-'a'] = 0.0006864103154491656; //Expected frequency of np
        expectedFrequencies['j'-'a']['u'-'a'] = 0.0006763941917494241; //Expected frequency of ju
        expectedFrequencies['f'-'a']['l'-'a'] = 0.000668536884856893; //Expected frequency of fl
        expectedFrequencies['d'-'a']['n'-'a'] = 0.0006569005500643486; //Expected frequency of dn
        expectedFrequencies['k'-'a']['a'-'a'] = 0.0006551697964505124; //Expected frequency of ka
        expectedFrequencies['p'-'a']['h'-'a'] = 0.0006533904781307827; //Expected frequency of ph
        expectedFrequencies['h'-'a']['u'-'a'] = 0.0006410148035061384; //Expected frequency of hu
        expectedFrequencies['j'-'a']['o'-'a'] = 0.0006293396169488794; //Expected frequency of jo
        expectedFrequencies['l'-'a']['f'-'a'] = 0.0006249866004773079; //Expected frequency of lf
        expectedFrequencies['y'-'a']['b'-'a'] = 0.0006236600902249075; //Expected frequency of yb
        expectedFrequencies['r'-'a']['v'-'a'] = 0.0006226561883759411; //Expected frequency of rv
        expectedFrequencies['o'-'a']['e'-'a'] = 0.0006050487073635606; //Expected frequency of oe
        expectedFrequencies['i'-'a']['b'-'a'] = 0.0006009174697155686; //Expected frequency of ib
        expectedFrequencies['i'-'a']['k'-'a'] = 0.0005978370797989063; //Expected frequency of ik
        expectedFrequencies['y'-'a']['p'-'a'] = 0.0005970829392945344; //Expected frequency of yp
        expectedFrequencies['g'-'a']['l'-'a'] = 0.0005959090609749415; //Expected frequency of gl
        expectedFrequencies['l'-'a']['p'-'a'] = 0.0005883167786202853; //Expected frequency of lp
        expectedFrequencies['y'-'a']['m'-'a'] = 0.0005819145628205199; //Expected frequency of ym
        expectedFrequencies['l'-'a']['b'-'a'] = 0.0005697548855068488; //Expected frequency of lb
        expectedFrequencies['h'-'a']['s'-'a'] = 0.0005693693742462576; //Expected frequency of hs
        expectedFrequencies['d'-'a']['g'-'a'] = 0.0005647702965981599; //Expected frequency of dg
        expectedFrequencies['g'-'a']['n'-'a'] = 0.0005611371940763308; //Expected frequency of gn
        expectedFrequencies['e'-'a']['k'-'a'] = 0.0005577168512184154; //Expected frequency of ek
        expectedFrequencies['n'-'a']['r'-'a'] = 0.0005535405177720939; //Expected frequency of nr
        expectedFrequencies['p'-'a']['s'-'a'] = 0.0005497145439897171; //Expected frequency of ps
        expectedFrequencies['t'-'a']['d'-'a'] = 0.0005426564733998874; //Expected frequency of td
        expectedFrequencies['l'-'a']['c'-'a'] = 0.0005383890233148899; //Expected frequency of lc
        expectedFrequencies['s'-'a']['k'-'a'] = 0.0005369609897011219; //Expected frequency of sk
        expectedFrequencies['y'-'a']['f'-'a'] = 0.0005331118898683197; //Expected frequency of yf
        expectedFrequencies['y'-'a']['h'-'a'] = 0.0005298809493633882; //Expected frequency of yh
        expectedFrequencies['v'-'a']['o'-'a'] = 0.0005210974441513202; //Expected frequency of vo
        expectedFrequencies['a'-'a']['h'-'a'] = 0.0005146170623011168; //Expected frequency of ah
        expectedFrequencies['d'-'a']['y'-'a'] = 0.0005129450488553611; //Expected frequency of dy
        expectedFrequencies['l'-'a']['m'-'a'] = 0.0005125921453258696; //Expected frequency of lm
        expectedFrequencies['s'-'a']['y'-'a'] = 0.0005120731967543238; //Expected frequency of sy
        expectedFrequencies['n'-'a']['v'-'a'] = 0.0005075090394423684; //Expected frequency of nv
        expectedFrequencies['y'-'a']['d'-'a'] = 0.0004908127248167483; //Expected frequency of yd
        expectedFrequencies['f'-'a']['s'-'a'] = 0.0004734864565775405; //Expected frequency of fs
        expectedFrequencies['s'-'a']['g'-'a'] = 0.0004726432807790307; //Expected frequency of sg
        expectedFrequencies['y'-'a']['r'-'a'] = 0.00046759463271066337; //Expected frequency of yr
        expectedFrequencies['y'-'a']['l'-'a'] = 0.00046574454867663205; //Expected frequency of yl
        expectedFrequencies['w'-'a']['s'-'a'] = 0.00045991400884338226; //Expected frequency of ws
        expectedFrequencies['m'-'a']['y'-'a'] = 0.0004507565553959356; //Expected frequency of my
        expectedFrequencies['o'-'a']['y'-'a'] = 0.0004470015785883647; //Expected frequency of oy
        expectedFrequencies['k'-'a']['n'-'a'] = 0.0004402820733767629; //Expected frequency of kn
        expectedFrequencies['i'-'a']['z'-'a'] = 0.0004314863113579693; //Expected frequency of iz
        expectedFrequencies['x'-'a']['p'-'a'] = 0.00042568028513817046; //Expected frequency of xp
        expectedFrequencies['l'-'a']['w'-'a'] = 0.000424781838079144; //Expected frequency of lw
        expectedFrequencies['t'-'a']['n'-'a'] = 0.00041213373858048867; //Expected frequency of tn
        expectedFrequencies['k'-'a']['o'-'a'] = 0.00040655619773889267; //Expected frequency of ko
        expectedFrequencies['a'-'a']['a'-'a'] = 0.00039803239807310176; //Expected frequency of aa
        expectedFrequencies['j'-'a']['a'-'a'] = 0.00039609443504745395; //Expected frequency of ja
        expectedFrequencies['z'-'a']['e'-'a'] = 0.0003954256296691516; //Expected frequency of ze
        expectedFrequencies['f'-'a']['c'-'a'] = 0.00036326191873751665; //Expected frequency of fc
        expectedFrequencies['g'-'a']['w'-'a'] = 0.00036261438932560564; //Expected frequency of gw
        expectedFrequencies['t'-'a']['g'-'a'] = 0.00035383897823118647; //Expected frequency of tg
        expectedFrequencies['x'-'a']['t'-'a'] = 0.0003491961923477848; //Expected frequency of xt
        expectedFrequencies['f'-'a']['h'-'a'] = 0.0003486492612552243; //Expected frequency of fh
        expectedFrequencies['l'-'a']['r'-'a'] = 0.0003480683348685385; //Expected frequency of lr
        expectedFrequencies['j'-'a']['e'-'a'] = 0.00034396484848105694; //Expected frequency of je
        expectedFrequencies['y'-'a']['n'-'a'] = 0.00034357332444735506; //Expected frequency of yn
        expectedFrequencies['g'-'a']['g'-'a'] = 0.0003395565607489687; //Expected frequency of gg
        expectedFrequencies['g'-'a']['f'-'a'] = 0.000338863704278224; //Expected frequency of gf
        expectedFrequencies['e'-'a']['q'-'a'] = 0.00033797242629482943; //Expected frequency of eq
        expectedFrequencies['h'-'a']['y'-'a'] = 0.00033450698763858445; //Expected frequency of hy
        expectedFrequencies['k'-'a']['t'-'a'] = 0.00033393669923509426; //Expected frequency of kt
        expectedFrequencies['h'-'a']['c'-'a'] = 0.0003332595684786388; //Expected frequency of hc
        expectedFrequencies['b'-'a']['s'-'a'] = 0.0003260014575526296; //Expected frequency of bs
        expectedFrequencies['h'-'a']['w'-'a'] = 0.00032451005856069603; //Expected frequency of hw
        expectedFrequencies['h'-'a']['n'-'a'] = 0.0003200548249462443; //Expected frequency of hn
        expectedFrequencies['c'-'a']['s'-'a'] = 0.0003195113627612476; //Expected frequency of cs
        expectedFrequencies['h'-'a']['m'-'a'] = 0.00031289569351605577; //Expected frequency of hm
        expectedFrequencies['n'-'a']['j'-'a'] = 0.00031052157317938505; //Expected frequency of nj
        expectedFrequencies['h'-'a']['h'-'a'] = 0.0003075760081367029; //Expected frequency of hh
        expectedFrequencies['w'-'a']['t'-'a'] = 0.00030093767536209414; //Expected frequency of wt
        expectedFrequencies['g'-'a']['c'-'a'] = 0.0003005325069586413; //Expected frequency of gc
        expectedFrequencies['l'-'a']['h'-'a'] = 0.00029463698292369613; //Expected frequency of lh
        expectedFrequencies['e'-'a']['j'-'a'] = 0.00029069283502364555; //Expected frequency of ej
        expectedFrequencies['f'-'a']['m'-'a'] = 0.00028937904409897906; //Expected frequency of fm
        expectedFrequencies['d'-'a']['v'-'a'] = 0.0002864311664512544; //Expected frequency of dv
        expectedFrequencies['l'-'a']['v'-'a'] = 0.00028636687603107177; //Expected frequency of lv
        expectedFrequencies['w'-'a']['r'-'a'] = 0.0002836999798960156; //Expected frequency of wr
        expectedFrequencies['g'-'a']['p'-'a'] = 0.0002810286898113786; //Expected frequency of gp
        expectedFrequencies['f'-'a']['p'-'a'] = 0.00027747675972654266; //Expected frequency of fp
        expectedFrequencies['g'-'a']['b'-'a'] = 0.00027389962224674304; //Expected frequency of gb
        expectedFrequencies['g'-'a']['m'-'a'] = 0.00027254304812878956; //Expected frequency of gm
        expectedFrequencies['h'-'a']['l'-'a'] = 0.00027045175938882136; //Expected frequency of hl
        expectedFrequencies['l'-'a']['k'-'a'] = 0.00026923024140535217; //Expected frequency of lk
        expectedFrequencies['c'-'a']['y'-'a'] = 0.00026486635569008074; //Expected frequency of cy
        expectedFrequencies['m'-'a']['c'-'a'] = 0.0002547859415701567; //Expected frequency of mc
        expectedFrequencies['y'-'a']['g'-'a'] = 0.00024261123232370915; //Expected frequency of yg
        expectedFrequencies['x'-'a']['i'-'a'] = 0.00023698096408714326; //Expected frequency of xi
        expectedFrequencies['h'-'a']['b'-'a'] = 0.0002344990763554902; //Expected frequency of hb
        expectedFrequencies['f'-'a']['w'-'a'] = 0.00023262563501052922; //Expected frequency of fw
        expectedFrequencies['g'-'a']['y'-'a'] = 0.0002265899671100062; //Expected frequency of gy
        expectedFrequencies['h'-'a']['p'-'a'] = 0.00022632286122759292; //Expected frequency of hp
        expectedFrequencies['m'-'a']['w'-'a'] = 0.00021683470525906318; //Expected frequency of mw
        expectedFrequencies['p'-'a']['m'-'a'] = 0.0002153555630738551; //Expected frequency of pm
        expectedFrequencies['z'-'a']['a'-'a'] = 0.00021486852845189634; //Expected frequency of za
        expectedFrequencies['l'-'a']['g'-'a'] = 0.00021425638189713622; //Expected frequency of lg
        expectedFrequencies['i'-'a']['w'-'a'] = 0.00021323582929186368; //Expected frequency of iw
        expectedFrequencies['x'-'a']['a'-'a'] = 0.00020909372240017176; //Expected frequency of xa
        expectedFrequencies['f'-'a']['b'-'a'] = 0.00020539517315563884; //Expected frequency of fb
        expectedFrequencies['s'-'a']['v'-'a'] = 0.00020399095937380905; //Expected frequency of sv
        expectedFrequencies['g'-'a']['d'-'a'] = 0.00020346114155856332; //Expected frequency of gd
        expectedFrequencies['i'-'a']['x'-'a'] = 0.00020336123702072563; //Expected frequency of ix
        expectedFrequencies['a'-'a']['j'-'a'] = 0.0002012572289530235; //Expected frequency of aj
        expectedFrequencies['k'-'a']['l'-'a'] = 0.00019571784609462937; //Expected frequency of kl
        expectedFrequencies['h'-'a']['f'-'a'] = 0.000192936938530976; //Expected frequency of hf
        expectedFrequencies['h'-'a']['d'-'a'] = 0.0001916582992029561; //Expected frequency of hd
        expectedFrequencies['a'-'a']['e'-'a'] = 0.00018870001483253996; //Expected frequency of ae
        expectedFrequencies['s'-'a']['q'-'a'] = 0.00018508841953760653; //Expected frequency of sq
        expectedFrequencies['d'-'a']['j'-'a'] = 0.0001848617842434377; //Expected frequency of dj
        expectedFrequencies['f'-'a']['y'-'a'] = 0.00018268677920092957; //Expected frequency of fy
        expectedFrequencies['a'-'a']['z'-'a'] = 0.00017769108978803644; //Expected frequency of az
        expectedFrequencies['l'-'a']['n'-'a'] = 0.00017398097751829082; //Expected frequency of ln
        expectedFrequencies['a'-'a']['o'-'a'] = 0.00017334501113159254; //Expected frequency of ao
        expectedFrequencies['f'-'a']['d'-'a'] = 0.00017298910121554578; //Expected frequency of fd
        expectedFrequencies['k'-'a']['w'-'a'] = 0.00016642269045776; //Expected frequency of kw
        expectedFrequencies['m'-'a']['f'-'a'] = 0.0001653713802054217; //Expected frequency of mf
        expectedFrequencies['m'-'a']['h'-'a'] = 0.00016439476709595742; //Expected frequency of mh
        expectedFrequencies['s'-'a']['j'-'a'] = 0.00016290961213763874; //Expected frequency of sj
        expectedFrequencies['u'-'a']['f'-'a'] = 0.00016231989785179125; //Expected frequency of uf
        expectedFrequencies['t'-'a']['v'-'a'] = 0.00016145452104487308; //Expected frequency of tv
        expectedFrequencies['x'-'a']['c'-'a'] = 0.00016141867566671372; //Expected frequency of xc
        expectedFrequencies['y'-'a']['u'-'a'] = 0.00016084445583465124; //Expected frequency of yu
        expectedFrequencies['b'-'a']['b'-'a'] = 0.00015937502659062186; //Expected frequency of bb
        expectedFrequencies['w'-'a']['w'-'a'] = 0.00015601064877473586; //Expected frequency of ww
        expectedFrequencies['o'-'a']['j'-'a'] = 0.00015288215667318884; //Expected frequency of oj
        expectedFrequencies['a'-'a']['x'-'a'] = 0.00015282295398409983; //Expected frequency of ax
        expectedFrequencies['m'-'a']['r'-'a'] = 0.00015277508305971927; //Expected frequency of mr
        expectedFrequencies['w'-'a']['l'-'a'] = 0.0001521189970091509; //Expected frequency of wl
        expectedFrequencies['x'-'a']['e'-'a'] = 0.00015123211297533713; //Expected frequency of xe
        expectedFrequencies['k'-'a']['h'-'a'] = 0.00015034129751295103; //Expected frequency of kh
        expectedFrequencies['o'-'a']['x'-'a'] = 0.00015033736608437872; //Expected frequency of ox
        expectedFrequencies['u'-'a']['o'-'a'] = 0.00015029758927764705; //Expected frequency of uo
        expectedFrequencies['z'-'a']['i'-'a'] = 0.0001489398588571723; //Expected frequency of zi
        expectedFrequencies['f'-'a']['g'-'a'] = 0.00014748823667197044; //Expected frequency of fg
        expectedFrequencies['i'-'a']['h'-'a'] = 0.0001412268585192956; //Expected frequency of ih
        expectedFrequencies['t'-'a']['k'-'a'] = 0.00014114591734280674; //Expected frequency of tk
        expectedFrequencies['i'-'a']['i'-'a'] = 0.00014040380238465591; //Expected frequency of ii
        expectedFrequencies['i'-'a']['u'-'a'] = 0.0001333640013746624; //Expected frequency of iu
        expectedFrequencies['t'-'a']['j'-'a'] = 0.00012938400809645246; //Expected frequency of tj
        expectedFrequencies['m'-'a']['n'-'a'] = 0.00012913517179387524; //Expected frequency of mn
        expectedFrequencies['w'-'a']['y'-'a'] = 0.00012803668439866913; //Expected frequency of wy
        expectedFrequencies['k'-'a']['y'-'a'] = 0.000127955511961676; //Expected frequency of ky
        expectedFrequencies['k'-'a']['f'-'a'] = 0.00012426598187680897; //Expected frequency of kf
        expectedFrequencies['f'-'a']['n'-'a'] = 0.0001235768255741323; //Expected frequency of fn
        expectedFrequencies['u'-'a']['y'-'a'] = 0.0001230213378429144; //Expected frequency of uy
        expectedFrequencies['p'-'a']['w'-'a'] = 0.00012266311532182507; //Expected frequency of pw
        expectedFrequencies['d'-'a']['k'-'a'] = 0.00012158382254847205; //Expected frequency of dk
        expectedFrequencies['r'-'a']['j'-'a'] = 0.00011982924910269757; //Expected frequency of rj
        expectedFrequencies['u'-'a']['k'-'a'] = 0.0001190697896067277; //Expected frequency of uk
        expectedFrequencies['k'-'a']['r'-'a'] = 0.00011725370086682168; //Expected frequency of kr
        expectedFrequencies['k'-'a']['u'-'a'] = 0.0001171607341441116; //Expected frequency of ku
        expectedFrequencies['w'-'a']['m'-'a'] = 0.00011694543061465121; //Expected frequency of wm
        expectedFrequencies['k'-'a']['m'-'a'] = 0.00011230403229427506; //Expected frequency of km
        expectedFrequencies['m'-'a']['d'-'a'] = 0.00011126544136967071; //Expected frequency of md
        expectedFrequencies['m'-'a']['l'-'a'] = 0.00011066462657961904; //Expected frequency of ml
        expectedFrequencies['e'-'a']['z'-'a'] = 0.00010764390187305436; //Expected frequency of ez
        expectedFrequencies['k'-'a']['b'-'a'] = 0.00010588493447769906; //Expected frequency of kb
        expectedFrequencies['w'-'a']['c'-'a'] = 0.00010369582254443145; //Expected frequency of wc
        expectedFrequencies['w'-'a']['d'-'a'] = 0.00010005393212344076; //Expected frequency of wd
        expectedFrequencies['h'-'a']['g'-'a'] = 9.93511314510131e-05; //Expected frequency of hg
        expectedFrequencies['b'-'a']['t'-'a'] = 9.904332371985113e-05; //Expected frequency of bt
        expectedFrequencies['z'-'a']['o'-'a'] = 9.805815397172944e-05; //Expected frequency of zo
        expectedFrequencies['k'-'a']['c'-'a'] = 9.713334321521803e-05; //Expected frequency of kc
        expectedFrequencies['p'-'a']['f'-'a'] = 9.670574254285253e-05; //Expected frequency of pf
        expectedFrequencies['y'-'a']['v'-'a'] = 9.516069111393209e-05; //Expected frequency of yv
        expectedFrequencies['p'-'a']['c'-'a'] = 9.257542993687754e-05; //Expected frequency of pc
        expectedFrequencies['p'-'a']['y'-'a'] = 9.161315497867699e-05; //Expected frequency of py
        expectedFrequencies['w'-'a']['b'-'a'] = 9.130627228953204e-05; //Expected frequency of wb
        expectedFrequencies['y'-'a']['k'-'a'] = 9.064324842383606e-05; //Expected frequency of yk
        expectedFrequencies['c'-'a']['p'-'a'] = 8.855496607042317e-05; //Expected frequency of cp
        expectedFrequencies['y'-'a']['j'-'a'] = 8.757349649036954e-05; //Expected frequency of yj
        expectedFrequencies['k'-'a']['p'-'a'] = 8.687370220449718e-05; //Expected frequency of kp
        expectedFrequencies['p'-'a']['b'-'a'] = 8.541282959912518e-05; //Expected frequency of pb
        expectedFrequencies['c'-'a']['d'-'a'] = 8.289185884225322e-05; //Expected frequency of cd
        expectedFrequencies['j'-'a']['i'-'a'] = 8.269343732960336e-05; //Expected frequency of ji
        expectedFrequencies['u'-'a']['w'-'a'] = 8.157298018649313e-05; //Expected frequency of uw
        expectedFrequencies['u'-'a']['h'-'a'] = 7.847617077402891e-05; //Expected frequency of uh
        expectedFrequencies['w'-'a']['f'-'a'] = 7.775278791672264e-05; //Expected frequency of wf
        expectedFrequencies['y'-'a']['y'-'a'] = 7.700350388293995e-05; //Expected frequency of yy
        expectedFrequencies['w'-'a']['p'-'a'] = 7.440714220168121e-05; //Expected frequency of wp
        expectedFrequencies['b'-'a']['c'-'a'] = 7.409124035287036e-05; //Expected frequency of bc
        expectedFrequencies['a'-'a']['q'-'a'] = 7.286278455427354e-05; //Expected frequency of aq
        expectedFrequencies['c'-'a']['b'-'a'] = 6.892788707439312e-05; //Expected frequency of cb
        expectedFrequencies['i'-'a']['q'-'a'] = 6.744365715809148e-05; //Expected frequency of iq
        expectedFrequencies['c'-'a']['m'-'a'] = 6.612709110737392e-05; //Expected frequency of cm
        expectedFrequencies['m'-'a']['g'-'a'] = 6.59400013594325e-05; //Expected frequency of mg
        expectedFrequencies['d'-'a']['q'-'a'] = 6.551933850219463e-05; //Expected frequency of dq
        expectedFrequencies['b'-'a']['j'-'a'] = 6.535606858619135e-05; //Expected frequency of bj
        expectedFrequencies['t'-'a']['z'-'a'] = 6.475456001462691e-05; //Expected frequency of tz
        expectedFrequencies['k'-'a']['d'-'a'] = 6.428625749351273e-05; //Expected frequency of kd
        expectedFrequencies['p'-'a']['d'-'a'] = 6.317158186300885e-05; //Expected frequency of pd
        expectedFrequencies['f'-'a']['j'-'a'] = 6.240911598048367e-05; //Expected frequency of fj
        expectedFrequencies['c'-'a']['f'-'a'] = 6.189224875347616e-05; //Expected frequency of cf
        expectedFrequencies['n'-'a']['z'-'a'] = 6.162190522400334e-05; //Expected frequency of nz
        expectedFrequencies['c'-'a']['w'-'a'] = 5.949245850083325e-05; //Expected frequency of cw
        expectedFrequencies['f'-'a']['v'-'a'] = 5.658597648337001e-05; //Expected frequency of fv
        expectedFrequencies['v'-'a']['y'-'a'] = 5.390266085251179e-05; //Expected frequency of vy
        expectedFrequencies['f'-'a']['k'-'a'] = 5.293668572624318e-05; //Expected frequency of fk
        expectedFrequencies['o'-'a']['z'-'a'] = 5.285597581025856e-05; //Expected frequency of oz
        expectedFrequencies['z'-'a']['z'-'a'] = 5.1172168078785784e-05; //Expected frequency of zz
        expectedFrequencies['i'-'a']['j'-'a'] = 5.067565177615262e-05; //Expected frequency of ij
        expectedFrequencies['l'-'a']['j'-'a'] = 5.0498506229894114e-05; //Expected frequency of lj
        expectedFrequencies['n'-'a']['q'-'a'] = 5.0281121355895434e-05; //Expected frequency of nq
        expectedFrequencies['u'-'a']['v'-'a'] = 4.9039021187547636e-05; //Expected frequency of uv
        expectedFrequencies['x'-'a']['o'-'a'] = 4.88359744648127e-05; //Expected frequency of xo
        expectedFrequencies['p'-'a']['g'-'a'] = 4.882672404464254e-05; //Expected frequency of pg
        expectedFrequencies['h'-'a']['k'-'a'] = 4.865374118746061e-05; //Expected frequency of hk
        expectedFrequencies['k'-'a']['g'-'a'] = 4.8394960683200476e-05; //Expected frequency of kg
        expectedFrequencies['v'-'a']['s'-'a'] = 4.719865009469496e-05; //Expected frequency of vs
        expectedFrequencies['h'-'a']['v'-'a'] = 4.568296874981478e-05; //Expected frequency of hv
        expectedFrequencies['b'-'a']['m'-'a'] = 4.4357383539431316e-05; //Expected frequency of bm
        expectedFrequencies['h'-'a']['j'-'a'] = 4.391775732084462e-05; //Expected frequency of hj
        expectedFrequencies['c'-'a']['n'-'a'] = 4.3487612782932326e-05; //Expected frequency of cn
        expectedFrequencies['g'-'a']['v'-'a'] = 4.319414320303411e-05; //Expected frequency of gv
        expectedFrequencies['c'-'a']['g'-'a'] = 4.199459496746903e-05; //Expected frequency of cg
        expectedFrequencies['w'-'a']['u'-'a'] = 4.1831325051465765e-05; //Expected frequency of wu
        expectedFrequencies['g'-'a']['j'-'a'] = 4.092085244621809e-05; //Expected frequency of gj
        expectedFrequencies['x'-'a']['h'-'a'] = 3.852776874819854e-05; //Expected frequency of xh
        expectedFrequencies['g'-'a']['k'-'a'] = 3.788740841191944e-05; //Expected frequency of gk
        expectedFrequencies['t'-'a']['q'-'a'] = 3.67960900923452e-05; //Expected frequency of tq
        expectedFrequencies['c'-'a']['q'-'a'] = 3.643416740318782e-05; //Expected frequency of cq
        expectedFrequencies['r'-'a']['q'-'a'] = 3.6292404714080165e-05; //Expected frequency of rq
        expectedFrequencies['b'-'a']['h'-'a'] = 3.572720404168359e-05; //Expected frequency of bh
        expectedFrequencies['x'-'a']['s'-'a'] = 3.5694365050079535e-05; //Expected frequency of xs
        expectedFrequencies['u'-'a']['z'-'a'] = 3.5553064881980387e-05; //Expected frequency of uz
        expectedFrequencies['w'-'a']['k'-'a'] = 3.4449489755680694e-05; //Expected frequency of wk
        expectedFrequencies['x'-'a']['u'-'a'] = 3.4118555974093337e-05; //Expected frequency of xu
        expectedFrequencies['u'-'a']['x'-'a'] = 3.348975866302693e-05; //Expected frequency of ux
        expectedFrequencies['b'-'a']['d'-'a'] = 3.2781638999001434e-05; //Expected frequency of bd
        expectedFrequencies['b'-'a']['w'-'a'] = 3.2420178830852556e-05; //Expected frequency of bw
        expectedFrequencies['w'-'a']['g'-'a'] = 3.2351031940080635e-05; //Expected frequency of wg
        expectedFrequencies['m'-'a']['v'-'a'] = 3.1524044376868626e-05; //Expected frequency of mv
        expectedFrequencies['m'-'a']['j'-'a'] = 3.104972908264384e-05; //Expected frequency of mj
        expectedFrequencies['p'-'a']['n'-'a'] = 3.044428908250708e-05; //Expected frequency of pn
        expectedFrequencies['x'-'a']['m'-'a'] = 2.9483864208340556e-05; //Expected frequency of xm
        expectedFrequencies['o'-'a']['q'-'a'] = 2.837034488035794e-05; //Expected frequency of oq
        expectedFrequencies['b'-'a']['v'-'a'] = 2.776999261131477e-05; //Expected frequency of bv
        expectedFrequencies['x'-'a']['w'-'a'] = 2.7594465888586045e-05; //Expected frequency of xw
        expectedFrequencies['k'-'a']['k'-'a'] = 2.747629177091229e-05; //Expected frequency of kk
        expectedFrequencies['b'-'a']['p'-'a'] = 2.663219093038549e-05; //Expected frequency of bp
        expectedFrequencies['z'-'a']['u'-'a'] = 2.625685513198138e-05; //Expected frequency of zu
        expectedFrequencies['r'-'a']['z'-'a'] = 2.6232341518530465e-05; //Expected frequency of rz
        expectedFrequencies['x'-'a']['f'-'a'] = 2.6139606056324644e-05; //Expected frequency of xf
        expectedFrequencies['m'-'a']['k'-'a'] = 2.5679397652859347e-05; //Expected frequency of mk
        expectedFrequencies['z'-'a']['h'-'a'] = 2.4892649417387516e-05; //Expected frequency of zh
        expectedFrequencies['b'-'a']['n'-'a'] = 2.4542521013947084e-05; //Expected frequency of bn
        expectedFrequencies['z'-'a']['y'-'a'] = 2.448378084586659e-05; //Expected frequency of zy
        expectedFrequencies['h'-'a']['q'-'a'] = 2.3413044711170948e-05; //Expected frequency of hq
        expectedFrequencies['w'-'a']['j'-'a'] = 2.299538824048837e-05; //Expected frequency of wj
        expectedFrequencies['i'-'a']['y'-'a'] = 2.274701445891966e-05; //Expected frequency of iy
        expectedFrequencies['d'-'a']['z'-'a'] = 2.2672317316045648e-05; //Expected frequency of dz
        expectedFrequencies['v'-'a']['r'-'a'] = 2.229721277814579e-05; //Expected frequency of vr
        expectedFrequencies['z'-'a']['s'-'a'] = 2.1968129080592464e-05; //Expected frequency of zs
        expectedFrequencies['x'-'a']['y'-'a'] = 2.1814572105767862e-05; //Expected frequency of xy
        expectedFrequencies['c'-'a']['v'-'a'] = 2.1790289752821203e-05; //Expected frequency of cv
        expectedFrequencies['x'-'a']['b'-'a'] = 2.1747969080542734e-05; //Expected frequency of xb
        expectedFrequencies['x'-'a']['r'-'a'] = 2.082408336604833e-05; //Expected frequency of xr
        expectedFrequencies['u'-'a']['j'-'a'] = 2.0389776139059473e-05; //Expected frequency of uj
        expectedFrequencies['y'-'a']['q'-'a'] = 2.034005513064488e-05; //Expected frequency of yq
        expectedFrequencies['v'-'a']['d'-'a'] = 1.9798443029682204e-05; //Expected frequency of vd
        expectedFrequencies['p'-'a']['k'-'a'] = 1.919855328164754e-05; //Expected frequency of pk
        expectedFrequencies['v'-'a']['u'-'a'] = 1.9155307567352055e-05; //Expected frequency of vu
        expectedFrequencies['j'-'a']['r'-'a'] = 1.8609764037817064e-05; //Expected frequency of jr
        expectedFrequencies['z'-'a']['l'-'a'] = 1.850985949997937e-05; //Expected frequency of zl
        expectedFrequencies['s'-'a']['z'-'a'] = 1.846383865963284e-05; //Expected frequency of sz
        expectedFrequencies['y'-'a']['z'-'a'] = 1.8103303533500983e-05; //Expected frequency of yz
        expectedFrequencies['l'-'a']['q'-'a'] = 1.7841285382181293e-05; //Expected frequency of lq
        expectedFrequencies['k'-'a']['j'-'a'] = 1.7764506894768992e-05; //Expected frequency of kj
        expectedFrequencies['b'-'a']['f'-'a'] = 1.7425941516541255e-05; //Expected frequency of bf
        expectedFrequencies['n'-'a']['x'-'a'] = 1.7308461180380262e-05; //Expected frequency of nx
        expectedFrequencies['q'-'a']['a'-'a'] = 1.7003891096277855e-05; //Expected frequency of qa
        expectedFrequencies['q'-'a']['i'-'a'] = 1.6971514625682306e-05; //Expected frequency of qi
        expectedFrequencies['k'-'a']['v'-'a'] = 1.692456874331876e-05; //Expected frequency of kv
        expectedFrequencies['z'-'a']['w'-'a'] = 1.5925754625446086e-05; //Expected frequency of zw
        expectedFrequencies['w'-'a']['v'-'a'] = 1.4784484036953e-05; //Expected frequency of wv
        expectedFrequencies['u'-'a']['u'-'a'] = 1.4579355969679774e-05; //Expected frequency of uu
        expectedFrequencies['v'-'a']['t'-'a'] = 1.4549060843622511e-05; //Expected frequency of vt
        expectedFrequencies['v'-'a']['p'-'a'] = 1.4471588574697448e-05; //Expected frequency of vp
        expectedFrequencies['x'-'a']['d'-'a'] = 1.3898987566164746e-05; //Expected frequency of xd
        expectedFrequencies['g'-'a']['q'-'a'] = 1.3817815129171621e-05; //Expected frequency of gq
        expectedFrequencies['x'-'a']['l'-'a'] = 1.3779657145969725e-05; //Expected frequency of xl
        expectedFrequencies['v'-'a']['c'-'a'] = 1.3649920003083275e-05; //Expected frequency of vc
        expectedFrequencies['c'-'a']['z'-'a'] = 1.3393220843361428e-05; //Expected frequency of cz
        expectedFrequencies['l'-'a']['z'-'a'] = 1.3254464540809076e-05; //Expected frequency of lz
        expectedFrequencies['z'-'a']['t'-'a'] = 1.317144201978192e-05; //Expected frequency of zt
        expectedFrequencies['w'-'a']['z'-'a'] = 1.221888000276003e-05; //Expected frequency of wz
        expectedFrequencies['s'-'a']['x'-'a'] = 1.1788504204343487e-05; //Expected frequency of sx
        expectedFrequencies['z'-'a']['b'-'a'] = 1.1713807061469471e-05; //Expected frequency of zb
        expectedFrequencies['v'-'a']['l'-'a'] = 1.1339165044578123e-05; //Expected frequency of vl
        expectedFrequencies['p'-'a']['v'-'a'] = 1.1124786557134741e-05; //Expected frequency of pv
        expectedFrequencies['f'-'a']['q'-'a'] = 1.0985798994078137e-05; //Expected frequency of fq
        expectedFrequencies['p'-'a']['j'-'a'] = 1.087918790161708e-05; //Expected frequency of pj
        expectedFrequencies['z'-'a']['m'-'a'] = 1.0645846052824878e-05; //Expected frequency of zm
        expectedFrequencies['v'-'a']['w'-'a'] = 1.0547329078012708e-05; //Expected frequency of vw
        expectedFrequencies['c'-'a']['j'-'a'] = 9.603323699648213e-06; //Expected frequency of cj
        expectedFrequencies['z'-'a']['c'-'a'] = 9.490237313068047e-06; //Expected frequency of zc
        expectedFrequencies['b'-'a']['g'-'a'] = 9.369750590351755e-06; //Expected frequency of bg
        expectedFrequencies['j'-'a']['s'-'a'] = 9.094550590289593e-06; //Expected frequency of js
        expectedFrequencies['x'-'a']['g'-'a'] = 9.085993951632197e-06; //Expected frequency of xg
        expectedFrequencies['r'-'a']['x'-'a'] = 8.93914353143096e-06; //Expected frequency of rx
        expectedFrequencies['h'-'a']['z'-'a'] = 8.571901850675737e-06; //Expected frequency of hz
        expectedFrequencies['x'-'a']['x'-'a'] = 8.106143195108345e-06; //Expected frequency of xx
        expectedFrequencies['v'-'a']['m'-'a'] = 8.099667900989235e-06; //Expected frequency of vm
        expectedFrequencies['x'-'a']['n'-'a'] = 8.0326023547556e-06; //Expected frequency of xn
        expectedFrequencies['q'-'a']['w'-'a'] = 8.017570421979094e-06; //Expected frequency of qw
        expectedFrequencies['j'-'a']['p'-'a'] = 7.983112606845262e-06; //Expected frequency of jp
        expectedFrequencies['v'-'a']['n'-'a'] = 7.650560001728127e-06; //Expected frequency of vn
        expectedFrequencies['z'-'a']['d'-'a'] = 7.6098581529794365e-06; //Expected frequency of zd
        expectedFrequencies['z'-'a']['r'-'a'] = 7.558749581539321e-06; //Expected frequency of zr
        expectedFrequencies['f'-'a']['z'-'a'] = 7.212090085662698e-06; //Expected frequency of fz
        expectedFrequencies['x'-'a']['v'-'a'] = 7.1961331108691766e-06; //Expected frequency of xv
        expectedFrequencies['z'-'a']['p'-'a'] = 7.027775463772324e-06; //Expected frequency of zp
        expectedFrequencies['v'-'a']['h'-'a'] = 6.984761009981095e-06; //Expected frequency of vh
        expectedFrequencies['v'-'a']['b'-'a'] = 6.750956640180384e-06; //Expected frequency of vb
        expectedFrequencies['z'-'a']['f'-'a'] = 6.627463530908792e-06; //Expected frequency of zf
        expectedFrequencies['g'-'a']['z'-'a'] = 6.594162018296227e-06; //Expected frequency of gz
        expectedFrequencies['t'-'a']['x'-'a'] = 6.511370757773325e-06; //Expected frequency of tx
        expectedFrequencies['v'-'a']['f'-'a'] = 6.496107564492566e-06; //Expected frequency of vf
        expectedFrequencies['d'-'a']['x'-'a'] = 6.339544203112663e-06; //Expected frequency of dx
        expectedFrequencies['q'-'a']['b'-'a'] = 6.315030589661748e-06; //Expected frequency of qb
        expectedFrequencies['b'-'a']['k'-'a'] = 6.242414791326018e-06; //Expected frequency of bk
        expectedFrequencies['z'-'a']['g'-'a'] = 6.0981082366715725e-06; //Expected frequency of zg
        expectedFrequencies['v'-'a']['g'-'a'] = 5.916800001336501e-06; //Expected frequency of vg
        expectedFrequencies['j'-'a']['c'-'a'] = 5.728322690369557e-06; //Expected frequency of jc
        expectedFrequencies['z'-'a']['k'-'a'] = 5.6108423542085664e-06; //Expected frequency of zk
        expectedFrequencies['z'-'a']['n'-'a'] = 5.605985883619234e-06; //Expected frequency of zn
        expectedFrequencies['u'-'a']['q'-'a'] = 5.408258152482134e-06; //Expected frequency of uq
        expectedFrequencies['j'-'a']['m'-'a'] = 5.1658971440240275e-06; //Expected frequency of jm
        expectedFrequencies['v'-'a']['v'-'a'] = 5.163815799485743e-06; //Expected frequency of vv
        expectedFrequencies['j'-'a']['d'-'a'] = 5.065298824673574e-06; //Expected frequency of jd
        expectedFrequencies['m'-'a']['q'-'a'] = 4.939261849855188e-06; //Expected frequency of mq
        expectedFrequencies['j'-'a']['h'-'a'] = 4.847220169162129e-06; //Expected frequency of jh
        expectedFrequencies['q'-'a']['s'-'a'] = 4.821087732181436e-06; //Expected frequency of qs
        expectedFrequencies['j'-'a']['t'-'a'] = 4.7195643708139654e-06; //Expected frequency of jt
        expectedFrequencies['j'-'a']['b'-'a'] = 4.481828572440937e-06; //Expected frequency of jb
        expectedFrequencies['f'-'a']['x'-'a'] = 4.466334118655925e-06; //Expected frequency of fx
        expectedFrequencies['p'-'a']['q'-'a'] = 4.303064202652658e-06; //Expected frequency of pq
        expectedFrequencies['m'-'a']['z'-'a'] = 4.225360673223342e-06; //Expected frequency of mz
        expectedFrequencies['y'-'a']['x'-'a'] = 3.918709244582646e-06; //Expected frequency of yx
        expectedFrequencies['q'-'a']['t'-'a'] = 3.911540168950775e-06; //Expected frequency of qt
        expectedFrequencies['w'-'a']['q'-'a'] = 3.7568268916049035e-06; //Expected frequency of wq
        expectedFrequencies['j'-'a']['j'-'a'] = 3.7198252109242765e-06; //Expected frequency of jj
        expectedFrequencies['j'-'a']['w'-'a'] = 3.719362689915769e-06; //Expected frequency of jw
        expectedFrequencies['l'-'a']['x'-'a'] = 3.576906219295355e-06; //Expected frequency of lx
        expectedFrequencies['g'-'a']['x'-'a'] = 3.4175677318644052e-06; //Expected frequency of gx
        expectedFrequencies['j'-'a']['n'-'a'] = 3.3421768074776277e-06; //Expected frequency of jn
        expectedFrequencies['z'-'a']['v'-'a'] = 3.316044370496935e-06; //Expected frequency of zv
        expectedFrequencies['m'-'a']['x'-'a'] = 3.295462185618336e-06; //Expected frequency of mx
        expectedFrequencies['j'-'a']['k'-'a'] = 3.2300154629144775e-06; //Expected frequency of jk
        expectedFrequencies['k'-'a']['q'-'a'] = 3.2156773116507347e-06; //Expected frequency of kq
        expectedFrequencies['x'-'a']['k'-'a'] = 3.156937143570239e-06; //Expected frequency of xk
        expectedFrequencies['j'-'a']['f'-'a'] = 2.923132773769528e-06; //Expected frequency of jf
        expectedFrequencies['q'-'a']['m'-'a'] = 2.8479731098870043e-06; //Expected frequency of qm
        expectedFrequencies['q'-'a']['h'-'a'] = 2.8382601687083397e-06; //Expected frequency of qh
        expectedFrequencies['j'-'a']['l'-'a'] = 2.8095838661808537e-06; //Expected frequency of jl
        expectedFrequencies['j'-'a']['g'-'a'] = 2.7804450426448604e-06; //Expected frequency of jg
        expectedFrequencies['v'-'a']['k'-'a'] = 2.6523267232881895e-06; //Expected frequency of vk
        expectedFrequencies['v'-'a']['j'-'a'] = 2.6437700846307946e-06; //Expected frequency of vj
        expectedFrequencies['k'-'a']['z'-'a'] = 2.588267563609854e-06; //Expected frequency of kz
        expectedFrequencies['q'-'a']['c'-'a'] = 2.466855798876547e-06; //Expected frequency of qc
        expectedFrequencies['x'-'a']['j'-'a'] = 2.458067899714898e-06; //Expected frequency of xj
        expectedFrequencies['p'-'a']['z'-'a'] = 2.242533109750246e-06; //Expected frequency of pz
        expectedFrequencies['q'-'a']['l'-'a'] = 2.220794622350378e-06; //Expected frequency of ql
        expectedFrequencies['q'-'a']['o'-'a'] = 2.172461176961309e-06; //Expected frequency of qo
        expectedFrequencies['j'-'a']['v'-'a'] = 2.064000000466221e-06; //Expected frequency of jv
        expectedFrequencies['q'-'a']['f'-'a'] = 2.030004706340895e-06; //Expected frequency of qf
        expectedFrequencies['q'-'a']['d'-'a'] = 2.0068786559155033e-06; //Expected frequency of qd
        expectedFrequencies['b'-'a']['z'-'a'] = 1.880610420592864e-06; //Expected frequency of bz
        expectedFrequencies['h'-'a']['x'-'a'] = 1.7404665550149893e-06; //Expected frequency of hx
        expectedFrequencies['z'-'a']['j'-'a'] = 1.6574440339878327e-06; //Expected frequency of zj
        expectedFrequencies['p'-'a']['x'-'a'] = 1.5758090759861996e-06; //Expected frequency of px
        expectedFrequencies['q'-'a']['p'-'a'] = 1.401901176787253e-06; //Expected frequency of qp
        expectedFrequencies['q'-'a']['e'-'a'] = 1.3921882356085885e-06; //Expected frequency of qe
        expectedFrequencies['q'-'a']['r'-'a'] = 1.381781512917162e-06; //Expected frequency of qr
        expectedFrequencies['z'-'a']['q'-'a'] = 1.3350668910578706e-06; //Expected frequency of zq
        expectedFrequencies['j'-'a']['y'-'a'] = 1.3235038658451746e-06; //Expected frequency of jy
        expectedFrequencies['b'-'a']['q'-'a'] = 1.2749391599518517e-06; //Expected frequency of bq
        expectedFrequencies['x'-'a']['q'-'a'] = 1.2525068910392216e-06; //Expected frequency of xq
        expectedFrequencies['c'-'a']['x'-'a'] = 1.2256806725457672e-06; //Expected frequency of cx
        expectedFrequencies['k'-'a']['x'-'a'] = 1.175497143122667e-06; //Expected frequency of kx
        expectedFrequencies['w'-'a']['x'-'a'] = 1.08183663889983e-06; //Expected frequency of wx
        expectedFrequencies['q'-'a']['y'-'a'] = 1.053854117885106e-06; //Expected frequency of qy
        expectedFrequencies['q'-'a']['v'-'a'] = 9.74069243917504e-07; //Expected frequency of qv
        expectedFrequencies['q'-'a']['n'-'a'] = 8.806400001989211e-07; //Expected frequency of qn
        expectedFrequencies['v'-'a']['x'-'a'] = 7.381835295785074e-07; //Expected frequency of vx
        expectedFrequencies['b'-'a']['x'-'a'] = 6.986379833510873e-07; //Expected frequency of bx
        expectedFrequencies['j'-'a']['z'-'a'] = 6.611737816619525e-07; //Expected frequency of jz
        expectedFrequencies['v'-'a']['z'-'a'] = 6.08908907700567e-07; //Expected frequency of vz
        expectedFrequencies['q'-'a']['g'-'a'] = 5.936457144198084e-07; //Expected frequency of qg
        expectedFrequencies['q'-'a']['q'-'a'] = 5.779200001305419e-07; //Expected frequency of qq
        expectedFrequencies['z'-'a']['x'-'a'] = 5.695946219774008e-07; //Expected frequency of zx
        expectedFrequencies['x'-'a']['z'-'a'] = 4.81484369856658e-07; //Expected frequency of xz
        expectedFrequencies['q'-'a']['k'-'a'] = 4.678400001056768e-07; //Expected frequency of qk
        expectedFrequencies['v'-'a']['q'-'a'] = 3.4411563032983046e-07; //Expected frequency of vq
        expectedFrequencies['q'-'a']['j'-'a'] = 3.103515967087584e-07; //Expected frequency of qj
        expectedFrequencies['q'-'a']['x'-'a'] = 1.7691428575424752e-07; //Expected frequency of qx
        expectedFrequencies['j'-'a']['x'-'a'] = 1.72751596677677e-07; //Expected frequency of jx
        expectedFrequencies['j'-'a']['q'-'a'] = 1.6697008407132905e-07; //Expected frequency of jq
        expectedFrequencies['q'-'a']['z'-'a'] = 6.475294119109714e-08; //Expected frequency of qz

        //Yes the above is gross, but it works. No I didn't type it all out, I wrote a script to parse a file containing expected frequencies


        //these two variables count the occurences of character combinations in each line of the de-shredded file
        int[][][] charCounts = new int[shreds[0].length][26][26];
        int[] totalChars = new int[shreds[0].length];


        //For each line in the de-shredded file
        for(int i = 0; i < shreds[0].length; i++) {
            for(int j = 1; j < shreds.length; j++) {

                //we iterate through its characters

                //grab two adjacent characters
                int backOne = processChar(shreds[perm[j]][i]);
                int cur = processChar(shreds[perm[j-1]][i]);

                //When the current combination consists of letters, increment the count for that letter combination
                if(cur != -1 && backOne != -1) {
                    totalChars[i]++;
                    charCounts[i][cur][backOne]++;
                }
            }
        }

        //Calculate the total difference between the expected frequencies and the actual frequencies
        double[] score = new double[shreds[0].length];
        for(int line = 0; line < shreds[0].length; line++) {
            for(int i = 0; i < 26; i++) {
                for(int j = 0; j < 26; j++) {
                    //If we know the expected frequency of the combination take the absolute difference between the observed frequency
                    //and the expect frequency
                    //In general score will be smaller when the de-shredded text looks more like English
                    double expected = expectedFrequencies[i][j];

                    double calculatedScore = abs((((float)charCounts[line][i][j])/totalChars[line])-expected)*(1.0-expected);
                    //If you're wondering about the (1.0-expected) above. expected is a frequency between 0.0 and 1.0, so (1.0-expected) will be larger when expected is smaller. Basically this means that our fitness function will place extra emphasis on less common letter combinations.
                    //For an example of why this might be helpful: the letter combination qz almost never occurs in english, so 1.0-expected is relatively large for qz. As a result our fitness function will essentially treat it as extra bad when qz appears too often
                    if(totalChars[line] != 0)
                        score[line] += calculatedScore;

                }
            }
        }

        //Here we just sum up the score for each line of the de-shredded document
        double total = 0;
        for(int i = 0; i < score.length; i++)
            total += pow(score[i],2);
        //Using pow(x,2) here essentially makes the fitness function focus more on lines with a bad score, since squaring will exaggerate the differences between scores
        //This should help encourage the GA to choose solutions which do well for all lines, instead of solutions that do REALLY well for only a few lines

        return total;
    }

    /**
     * This method will unshred a document according to the provided permutation.
     * Note: shredded.length MUST EQUAL perm.length
     * */
    public static char[][] unshred(char[][] shredded, int[] perm) {
        char[][] unshredded = new char[shredded.length][];
        for(int i = 0; i < shredded.length; i++)
            unshredded[i] = shredded[perm[i]].clone();
        return unshredded;
    }

    /**
     * This method will print out a document in an easy to ready format.
     * Note: doc MUST BE A SQUARE ARRAY. ALL ROWS MUST BE THE SAME LENGTH
     * */
    public static void prettyPrint(char[][] doc) {
        for(int i = 0; i < doc[0].length; i++) {
            for(int j = 0; j < doc.length; j++) {
                System.out.print(doc[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     *  A simple function to read a shredded document from disk.
     * */
    public static char[][] getShreddedDocument(String path) {
        char[][] shreddedDocument = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            LinkedList<char[]> lines = new LinkedList<>();
            String line;
            while( (line = br.readLine()) != null) {
                lines.add(line.toCharArray());
            }

            shreddedDocument = new char[lines.size()][];
            br.close();

            for(int i = 0; i < lines.size(); i++) {
                shreddedDocument[i] = lines.get(i);
            }

        }catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return shreddedDocument;
    }

    /**
     * The main method here provides examples of using the methods provided in this class.
     * */
    public static void main(String[] args) {
        System.out.println("NOTE: There is a lot more explanation in the comments of this file. Take a look at the actual file, don't just look at its output.");
        System.out.println();




        /**
         *             TLDR STARTS HERE
         * */

        //The following code is the bare minimum you'll need for your assignment

        //Your GA will be trying to generate a perm array which correctly unshreds the document. The fitness function is used to determine how
        //well the solution performed.
        //In your GA, each individual in the population will be a perm array. You can evaluate that individual by using fitness(shreddedDocument, permArray)

        int[] perm3 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14}; //A solution like this represents making no change to the shredded document
        //perm3 is used to show you the fitness of the shredded document, and print out what it looks like below

        char[][]shreddedDocument = getShreddedDocument("document1-shredded.txt"); //This reads a -shredded.txt file from disk
        System.out.println("The fitness of document1-shredded.txt is " + fitness(shreddedDocument, perm3));
        System.out.println("Here is what the shredded document looks like");
        prettyPrint(shreddedDocument);


        //Remember, a lower fitness value is better in this  case, so you'll see that the unshredded document has a lower fitness when you run this code
        int[] perm4 = {2,0,7,12,4,1,11,5,14,3,8,13,10,6,9};
        System.out.println("After unshredding using the correct perm array, the fitness is: " + fitness(shreddedDocument, perm4));
        char[][] unshredded = unshred(shreddedDocument, perm4);
        System.out.println("And here is what the unshredded document looks like.");
        prettyPrint(unshredded);

        System.out.println();

        /**
         *             TLDR ENDS HERE
         * */


        //Below here I'll be explaining some of the provided code, and the data file formats we used. If anything is unclear, or you have questions
        // please ask in tutorial or feel free to send me an email at cdennis2@brocku.ca.


        //A -shredded.txt file contains a number of lines, each line represents a shred of paper like you would see coming out of a paper shredder
        //A perm array/solution defines the order in which the shreds should be recombined
        //if perm[0] == 2, then shred 2 should be used in the first position
        //if perm[1] == 7, then shred 7 should be used in the second position
        //and so on
        //As such, a solution/perm array must have the same number of elements as the shredded document has lines. If the shredded document
        //has 15 lines, then the perm array MUST contain the numbers 0,1,2,...,14 in some order. All 15 numbers 0,1,..,14 must be present in
        //the solution exactly once.



        //Here's an example where the fitness function has some trouble
        //Sometimes it's possible to find solutions which actually do better than the correct solution according to the fitness function, especially when your dealing with problems that are difficult to solve programmatically
        //In our case, our fitness function is trying to estimate how much some piece of text resembles english. The provided function is examining the frequencies of different letter groups in the text. Can you think of any issues with this approach, or any other approaches to try?
        //The point is, fitness functions may not be perfect. It's often possible to construct a special case that will confuse a fitness
        //function. The goal is to find a function that works most of the time, ie often enough to be useful.
        shreddedDocument = getShreddedDocument("SillyExample-shredded.txt");
        int[] perm1 = {0,1,2,3,4,5,6,7};
        System.out.println("The fitness of SillyExample-shredded.txt is: " +fitness(shreddedDocument, perm1));
        System.out.println("Here is what the shredded document looks like");
        prettyPrint(shreddedDocument);

        int[] perm2 = {2,7,6,4,3,1,0,5};
        System.out.println("After unshredding using the correct perm array, the fitness is: " + fitness(shreddedDocument, perm2));
        System.out.println("Here is what the unshredded document looks like.");
        unshredded = unshred(shreddedDocument, perm2);
        prettyPrint(unshredded);

        int[] perm9 = {0,4,5,3,1,2,7,6};
        System.out.println("Here is the same document, incorrectly unshredded, but with a better fitness value.");
        System.out.println("After unshredding using the incorrect perm array, the fitness is: " + fitness(shreddedDocument, perm9));
        System.out.println("Here is the what unshredded document looks like.");
        unshredded = unshred(shreddedDocument, perm9);
        prettyPrint(unshredded);
        System.out.println("The point is, fitness functions often are not perfect. It's OK if your GA doesn't unshred the documents perfectly. But, you'll most likely be able to get a feel for what the document says, and be able to read at least bits and pieces.");
        System.out.println();
        System.out.println("Just to illustrate what unshredding means: When you print document1-shredded.txt using the provided prettyPrint method, the first column contains 'skTt t', which you can see above.");
        System.out.println("Take a look at column 7 in the unshredded version above, it contains 'skTt t'. All we're doing is swaping around these columns to put them into the correct order.");
        System.out.println("Each column of the prettyPrint output corresponds to a row in the -shredded.txt file. The provided prettyPrint method just lets you display a -shredded.txt file in an easy to read format.");
    }

}
