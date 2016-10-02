package algdsgn2.w6

import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static algdsgn2.w6.TwoSAT.Clause
import static algdsgn2.w6.TwoSAT.isSatisfiable
import static java.util.concurrent.TimeUnit.MILLISECONDS

class TwoSATSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'it should verify the 2 #sat of #file'() {

        given:
        def url = TwoSATSpec.class.getResource(file)
        def (n, clauses) = parseClauseFile(url)

        expect:
        n == size
        isSatisfiable(n, clauses) == satisfiable

        cleanup:
        println "TwoSAT($file) : Time spent ${stopwatch.runtime(MILLISECONDS)}ms"

        where:
        file | size | satisfiable
        //        '2sat_test1_ok.txt' | 8    | true
        //        '2sat_test2_ko.txt' | 8    | false
        //        '2sat_test3_ok.txt' | 7    | true
        '2sat1.txt' | 100000 | true
        //        '2sat2.txt'         | 200000  | false
        //        '2sat2.txt'         | 200000  | false
        //        '2sat3.txt'         | 400000  | true
        //        '2sat4.txt'         | 600000  | true
        //        '2sat5.txt'         | 800000  | false
        //        '2sat6.txt'         | 1000000 | false

        sat = satisfiable ? 'satisfiability' : 'unsatisfiability'

    }

    private static parseClauseFile(final URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            def n = scanner.nextInt()
            def clauses = new Clause[n]
            n.times {
                int v1 = scanner.nextInt()
                int v2 = scanner.nextInt()
                clauses[it] = new Clause(v1: v1.abs() - 1, not1: v1 < 0,
                                         v2: v2.abs() - 1, not2: v2 < 0)
            }
            [n, clauses]
        }
    }
}