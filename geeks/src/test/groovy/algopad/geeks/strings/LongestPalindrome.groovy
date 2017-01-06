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
import static java.util.concurrent.TimeUnit.NANOSECONDS

@See('http://www.geeksforgeeks.org/longest-palindrome-substring-set-1')
class LongestPalindrome extends Specification {

    /*@Subject //My brute-force solution :/
    def findLongestPalindrome = { String s ->
        int n = s.length()
        if (n == 0) { return s }
        def isPalindrome = { int i, int j ->
            boolean eq = s.charAt(i) == s.charAt(j)
            if (eq && j - i > 1) {
                eq = eq && call(i + 1, j - 1)
            }
            eq
        }
        def palindrome = "${s[0]}"
        for (int i = 0; i < n - 1; i++) {
            for (int j = i; j < n; j++) {
                if ((j - i + 1) > palindrome.length() &&
                    isPalindrome(i, j)) {
                    palindrome = s[i..j]
                }
            }
        }
        palindrome
    }*/

    @Subject
    def findLongestPalindrome = { String s ->
        int n = s.length()
        if (n == 0) { return s }

        boolean[][] cache = new boolean[n][n]
        for (int i = 0; i < n; i++) {
            cache[i][i] = true
        }
        for (int i = 0; i < n - 1; i++) {
            int j = i + 1
            cache[i][j] = s.charAt(i) == s.charAt(j)
        }

        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1
                cache[i][j] = cache[i + 1][j - 1] && s.charAt(i) == s.charAt(j)
            }
        }

        int start = 0, len = 0
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (cache[i][j] && j - i > len) {
                    start = i
                    len = j - i
                }
            }
        }

        s[start..start + len]

    }

    @Unroll
    def 'given a string "#s", it should find the longest palindromic substring #palindrome'() {

        expect:
        findLongestPalindrome(s) == palindrome

        where:
        s                               | palindrome
        ''                              | ''
        'a'                             | 'a'
        'mcdbm'                         | 'm'
        'abba'                          | 'abba'
        'eabbat'                        | 'abba'
        'forgeeksskeegfor'              | 'geeksskeeg'
        'fkiuamanaplanacanalpanamazzzz' | 'amanaplanacanalpanama'
        'aaaabaaa'                      | 'aaabaaa'

    }

    def 'it should find a palindrome for a very large string'() {

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
        findLongestPalindrome(str) == 'pgizigp'

        cleanup:
        def delta = nanoTime() - start
        println "Time spent = ${NANOSECONDS.toMillis(delta)}ms"

    }
}
