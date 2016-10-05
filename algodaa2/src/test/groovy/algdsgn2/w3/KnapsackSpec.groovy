package algdsgn2.w3

import algdsgn2.w3.Knapsack.Item
import org.junit.Rule
import org.junit.rules.Stopwatch
import spock.lang.Specification
import spock.lang.Unroll

import static algdsgn2.w3.Knapsack.calculateMaxValueWithCapacity
import static java.util.concurrent.TimeUnit.MILLISECONDS

class KnapsackSpec extends Specification {

    @Rule
    Stopwatch stopwatch = new Stopwatch() {}

    @Unroll
    def 'it should calculate the maximum value #value for a given knapsack data set'() {

        given:
        def input = KnapsackSpec.class.getResource(file)
        def (capacity, items) = parseKnapsackFile(input)

        expect:
        calculateMaxValueWithCapacity(items, capacity) == value

        cleanup:
        println "Time spent = ${stopwatch.runtime(MILLISECONDS)}ms"

        where:
        file                 | value
        'knapsack_test1.txt' | 51
        'knapsack_test2.txt' | 141
        'knapsack1.txt'      | 2493893
        // SLOW: 'knapsack_big.txt'   | 4243395

    }

    private static parseKnapsackFile(final URL url) {
        url.withReader { reader ->
            def scanner = new Scanner(reader)
            def capacity = scanner.nextInt()
            def size = scanner.nextInt()
            def items = new Item[size]
            size.times { ix ->
                def value = scanner.nextInt()
                def weight = scanner.nextInt()
                items[ix] = new Item(value: value, weight: weight)
            }
            [capacity, items]
        }
    }
}
