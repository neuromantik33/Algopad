package cc

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@Narrative('''
The function under test should be able to parse a roman numeral string into a long using the following rules :

Numbers are formed by combining symbols and adding the values, so II is two (two ones) and XIII is thirteen (a ten and three ones). Because each
numeral has a fixed value rather than representing multiples of ten, one hundred and so on, according to position, there is no need for "place
keeping" zeros, as in numbers like 207 or 1066; those numbers are written as CCVII (two hundreds, a five and two ones) and MLXVI (a thousand, a
fifty, a ten, a five and a one).

Symbols are placed from left to right in order of value, starting with the largest. However, in a few specific cases, to avoid four characters
being repeated in succession (such as IIII or XXXX), subtractive notation is often used as follows:

- I placed before V or X indicates one less, so four is IV (one less than five) and nine is IX (one less than ten)
- X placed before L or C indicates ten less, so forty is XL (ten less than fifty) and ninety is XC (ten less than a hundred)
- C placed before D or M indicates a hundred less, so four hundred is CD (a hundred less than five hundred) and nine hundred is CM (a hundred less
than a thousand)
For example, MCMIV is one thousand nine hundred and four, 1904 (M is a thousand, CM is nine hundred and IV is four).

Some examples of the modern use of Roman numerals include:

- 1954 as MCMLIV, as in the trailer for the movie The Last Time I Saw Paris[6]
- 1990 as MCMXC, used as the title of musical project Enigma's debut album MCMXC a.D., named after the year of its release.
- 2014 as MMXIV, the year of the games of the XXII (22nd) Olympic Winter Games
''')
class RomanNumeralSpec extends Specification {

    @Subject
    def parseRomanNum = { String roman ->

        def lookup = [
          'I': 1,
          'V': 5,
          'X': 10,
          'L': 50,
          'C': 100,
          'D': 500,
          'M': 1000,
        ]

        long num = 0
        for (int i = 0; i < roman.length(); i++) {
            num += lookup[roman[i]]
        }

        num

    }

    @Unroll
    def 'it should convert a single character #roman string to #num'() {

        expect:
        parseRomanNum(roman) == num as long

        where:
        roman | num
        'I'   | 1
        'V'   | 5
        'X'   | 10
        'L'   | 50
        'C'   | 100
        'D'   | 500
        'M'   | 1000

    }
}
