/*
 * Algopad.
 *
 * Copyright (c) 2016 Nicolas Estrada.
 * Licensed under the MIT License, the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package algopad.geeks.strings

import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static java.lang.System.nanoTime
import static java.math.BigInteger.probablePrime
import static java.util.concurrent.TimeUnit.NANOSECONDS

@See('http://www.geeksforgeeks.org/searching-for-patterns-set-3-rabin-karp-algorithm')
class FindSubstringRK extends Specification {

    /*@Subject // Basic naive Rabin-Karp
    def strStr = { String text, String pattern ->
        int n = text.length(), m = pattern.length()
        if (n < m || m == 0) {
            return -1
        }

        def hash = Character.&hashCode
        long pHash = pattern.inject(0L) { h, c -> h + hash(c as char) }
        long wHash = text[0..<m].inject(0L) { h, c -> h + hash(c as char) }
        def isMatch = { int index ->
            if (pHash != wHash) { return false }
            for (int i = 0; i < m; i++) {
                if (pattern.charAt(i) != text.charAt(index + i)) {
                    return false
                }
            }
            true
        }
        for (int i = 0; i <= n - m; i++) {
            if (isMatch(i)) { return i }
            if (i < n - m) {
                wHash -= hash(text.charAt(i)) // First 1st char in window
                wHash += hash(text.charAt(i + m)) // Add next char to window
            }
        }
        -1
    }*/

    @Subject
    def strStr = { String text, String pattern ->
        int n = text.length(), m = pattern.length()
        if (n < m || m == 0) {
            return -1
        }
        // radix
        def radix = Character.MAX_VALUE
        // a large prime, small enough to avoid long overflow
        long q = probablePrime(31, new Random()).longValue()
        // R^(M-1) % Q
        long rM = 1
        for (int i = 1; i <= m - 1; i++) {
            rM = (radix * rM) % q
        }

        // Compute hash for s[0..len-1]
        def hash = { String s, int len ->
            long h = 0
            for (int j = 0; j < len; j++) {
                h = (radix * h + s.charAt(j)) % q
            }
            h
        }

        // Does pattern match text[i..i-m+1]
        def check = { int i ->
            for (int j = 0; j < m; j++) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    return false
                }
            }
            true
        }

        long pHash = hash(pattern, m)
        long wHash = hash(text, m)

        // check for match at offset 0
        if (pHash == wHash && check(0)) {
            return 0
        }

        for (int i = m; i < n; i++) {
            // Remove leading digit
            //noinspection GroovyOverlyComplexArithmeticExpression
            wHash = (wHash + q - rM * text.charAt(i - m) % q) % q
            // Add trailing digit
            wHash = (radix * wHash + text.charAt(i)) % q

            int offset = i - m + 1
            if (pHash == wHash && check(offset)) {
                return offset
            }
        }
        -1
    }

    @Unroll
    def '''given a text #text of length n and a pattern #pattern of length m with n > m,
           it should return the index of the first occurrence of pattern in text.'''() {

        expect:
        strStr(text, pattern) == index

        where:
        text                 | pattern       | index
        'text'               | ''            | -1
        'b'                  | 'b'           | 0
        'This is some text'  | 'not here'    | -1
        'same string'        | 'same string' | 0
        'thisisatesttext'    | 'test'        | 7
        'AABAACAADAABAAABAA' | 'AABA'        | 0
        'geeks for geeks'    | 'or '         | 7

    }

    def 'it should find the first occurrence for a very large string'() {

        given:
        def start = nanoTime()
        def str = '''
zhilosebulkuwtvhlhfppxocjxieorcaciuzxraqspvfmwbbdtoyevcgmrwbixuixrlkegmkzdqaefwbgurtxizvebkfbagifbkbpzybswhlornuj
wvhotseuxsqdtkeeeokogamghpekeqbcrkheagmxmzcwkealukbqvdhjmbdvypesyomrnzynjplynieushrfildfiymavcysomrrffpjewwzrkblc
obkijafsiolbpgizigpskqoclpxmabrgrtrhnrgwytcfjlkmimfcwzmwfgbkknpcpecgfnagqbgezctqetvgtttubnpldykddculdmittxgorrgxc
peydrcjsvhbehosutykfxdjjvikwrxsdctqexgqkcwsfhfkkqevujxudautvxbmhlstcdnfglfqlzclgluuodcluscumcokaqtbtksribcsyhkgaa
juiubnlrlvehlftndipomlrhhxlesapjrgywrctzaivebhiqvsfodroovaedwduiomkikvbtdzrumymdrinntgugmcgmeyodbscmqaofqvngrdxdr
kupvntgilfiaiqcvkhueqlsanpbuvueosezkuwognlhplqoawnulgdmxkswflrairqkkkainyygrcqjkblcuwnfvkwodilxxlknposilacrqcrtut
urpzeoaolmhwppwogjchnzoxnukkrhibupmkyhgblqyuuampihwpyttblmymgnmvzzmkamcpalotxurawexmkeybipgofunpxymdujbuysdxfmfzy
eckuiklsineelaccvtgjswwohssxqtdweycxlnnxhlpckxkakbexzqlsnejmqjgulfuqqxmkyhxpbwpjciflwzwtbcifzdeuewqyzdnvaoeznibhn
xqnezjtusbzmmymzbzythnxfqdfoxkctdiffrxqlwclbxeupgsrdoakkotxdabpairqxrmndxppzaycofezofpdpkrrdnfbnaobhxuydaauwhjiik
glfavqlqzstsqismctwwuebrrgbzjlnjexuxomtdudguyjlxpvwtsswaqwksqhcxrueyhirybdkmlcdjmmimoajkrosbyhzbiyzyxkwkerakrevtq
pwnwwbomtqujavgpgvwbidtsnyktzlsyqkizkxulnqnwykewbtexkdpqwkllyjswhrynyhrhiwvwuqtomoujmvqloyxzjlzxtposwkysxlvprqzxr
khggwwtssbuswoukdyhaxpedjdnvspzumvwaxznjfbybjijnmyomjqglflcgzsuuoidmoexdpliasxdevvpihshqhccuvpsuslteazazwialfubir
gsjpixndsctpujchnshszhxadekzqrroziokcjztbjlghjalyicsripqkfvsrkybemtbtyimycacyotkozhrrtamtpanltkszhvrsfchyxtinmwim
ezjeepxdevlnygfnhuhxrkgbafpqzyfwayqqdzwggtmokcsfwureguplbwwaxxhngiihivukjwxqyaibeysyqrqdhtyfyiycldvrjmyoyrhdmytbc
mryanzebyyeepdutoyialwhqkvwhwaqpfhejnvrqamsdpueklrwrptjqqyigdiacnzjwvcrehtlskzxwfjesbniezrurgkpzwolgiafliqwjbpjtw
zxloqxunzahmhyaqnnbvvtrhesctqazsipgxikemsgribwblacqthnvwengjbsijjocsyrpnjradfzqpiocxfvtjcpuofehuawdqqhejpsbjhliwc
cjazrrcndrfqdkzsfzvwhccmiohxdurcufpdbanewprymrholjyudfanhvcgxqyfclxfnekcetnoidynzwdqbtfezxhuwsekjzfrmpnylgiokhsmc
btzqqbvbvgivsxyelyokpuhgkwarcjtswvmgxrtmkaxgufyfpzgbrzfoclqoiytjnozysoxibbfrfnlrpkzaxcrndgcqqtgvuxlzhyjrkqywbxsdf
glitmisidcebriaoxuebkeulyfaaivxlvkxilbujkysfftgtlheqkdmbekflnbsbuynsgdlchhhrfduoiiuoflnbjtfsshwrluoghcdathpgplglj
mxvxriutusuzqlvxeditrpimegvdxnrfycmloahgvyzyxrblkhmajspgpbczkyplhuybqhkbshrbmozgoymqdqegrkbjnnhrurxlcdekisaaqcyqg
ispeqzvsszlshuctavnypqtpvwxqkrbdsruqhpefyozyddcjhedusyfvyuubtcgttawqnzuiewdqzfsgcyvkgvoogptcxaukcthnkfewolgjjgeqi
ztmkapwdvnzwphceajcvcxhiplaojznccdgtbdkrpffeytrodrcnzsmlzayjlbdqgbvlzjoguffxwpfiuazowcaasoaksufqzlqcfngsxmqrukzrw
aytcpzhggkdphjxrfdxfloraczbtthznkjriypunwvbjqhxibmnwwwghvgopaneodqqevzlphitavrtcqwkzdubqmmiuyoixpfyrfyjqpcjdzxlac
dmgblumhivlzlcmjgccuyrnijsxlertqyoifxlkbbgdykjonctcnulzqivqdmwrmkhidanlkcobtclmqprfscbajrefinswppcthirhyuucrrllgm
udrvwyzmhvgzsebmlbcrzvjokmrjxuckvofbpxmxfascslahcgdyqajmmsutlcenfxklyrstdprkyceuomfbyssflqnniowsnmoifjzeshecmxhce
gmindyouxjywtoxpdqiipthadjyeixmpiljstmtxmnrqouahjfhyxeklfxqdmeatyaqfuavudkxklugwrtkisowwnzryfohsbmgbfjbucrhowyylq
yhmagszlecathynknlnqnsyhxnifmqyztuqzagbuebcrhavsomaughdolkkewrbkahxmbnpkhnovsqwqmypzrhhgvsblryqnrifhnspfwkortxigm
ivdstugejkkjghrhhqcfgqunahclrkqrbcdkhtlirsqtkcjciggdklseypcsddjylyvicpmglpnlvwejgrlvcswtgwvdirrkppxqtbnyvwhkrkbut
vfjrrjcrtoabjgixbzszlilkwjqhdubhfbaerrmeorzjaihoovehdjyxkzgqbevhdxfshzxprwsekghuokqnrekjjhjpwkwkhhxgvphqqoinvvjsm
fccsbnpcwbfgeemrlhviopondhbiydniqupjshbcioistimoytkmfnbqpwfelcxwbqhhkriilerjupizpnbtgkzlypildywnqoxpxyapmxqahoval
xpztqmdbucgxcqdnhxfledxjvxxhevqmwbayyslcnggxmdujaqjdzjdgswevhdzfjtmvyrsbhmpaosdbjrzsyzloedvbwwdzysbflqfzislprdrgf
vqbmtdhtlwibsfacknnglrknkhiryncczdwwjsvishbvblkxxoqyeqqwqeqyekvtxlwszpsstxkjmqdjnlyctxuflgmlfqzyyuslzkhtjdigeidhs
wefcyentxananrulmwzkzecezuanxdjfepaclyqfcnhunctnbdfszpvphyymqokayuohzhrmamehsdrxtidjgpwjwtzmkkcymfadcnnugoafbiooc
wybcuqhnggzdkpaisgurmgkarspidulbjvcithezescnpjrddoaxqvxuokebknkevhgxccpqnqvtwieaitnawbkvopuxpnognpfrnhfqjolfninni
pyinmsgprulfecizqmqykemeeeoeyllwmrtqemoqtvlhuswrnhybvtpnkimfallgsicbrdhzxdpvwndznqyvglxeaglxxwfburtbdvbfqxypitdst
mbirumjlcskjndjcwujgnlcawqnfozzmqprjlbzywemivsdcktxaaeesuvtnkktxsyeiebrmeruxuxqvxgparqjhjayhfqskxprvnywfitswlaxmr
zrvfjiejoivqtzubpxgdxnowfcspclokzbpoqeuodrujazpfasvphajvfhmgymgkyqbhsggthapnqqodgyjhgudzpdsmmshqqxjzlgdypngztlvmy
pgleaylgcxkuuwiemtjgcazpdzaipvtkinfiergzbkyxdgkaaiwslsymsajzlvkuxnqzknvmkbhumimgukixbjtlenmxjipzggjntxsjvkkbusscr
impkddxxwojvnnnwlydtxqvgqnepjxajwjxojwbnjcznnsbprxmifhgqkvoezbseuaoyhcmrqbvathofwfwucnasglkgjafgmeapyvvhnkmasmsri
yffnozxcmdxrwtgglsenlxwfnvdlbrdbugxkvphutlgghcjaohxynnwhnggfofdvjmotinvxjmqnhouqudjyvbzlzmyrcfotedqzeuqvfllpastie
yzldmkkmgwcoywmhujtrrczrpbwvntfbznyvetwbokzmkfwbozevfzbmzbgitnhereqttcsciaqsjlglhgwvoxylndczdxlfwxqdkbiaxommmvhkj
coreaynxirpnahxupdikdtvkdlevhhurfayojbhyyogbxsvuyicfgzxxruvclhsgdjaurhtcboagdyylwuzlyckolxxkmlygpthwooiikrjfyafuj
fnvcemwtnuwidsjvbbtlgxvudjuofezsjynmlksilmmvtcqnexhxhzljdmbqvomphhbzfunxuvwmoomkveiptzzuoabypgiivmankphejchburyhh
ugnotnwlewaabmirdtfysusnhcgwnanbjbtqearykgbmopagmyaxrdezgpyefaqghxxjrkyeigfbetjgpftorjxyjolpsvlfgnwvnqwkozkcrqfgx
bcxmvdtcutlfsdjbrmndrghelsaw
'''

        expect:
        strStr(str, 'funxuvwmoom') == 4857

        cleanup:
        def delta = nanoTime() - start
        println "Time spent = ${NANOSECONDS.toMillis(delta)}ms"

    }
}
