package algopad

/*
 *  algopad.
 */

/**
 * Collection of unoptimized sorting algorithms.
 *
 * @author Nicolas Estrada.
 */
@SuppressWarnings(['GroovyParameterNamingConvention', 'GroovyLocalVariableNamingConvention'])
class Sorts {

    static def mergeSort = { x ->

        def n = x.size()
        if (n < 2) { return x }

        int i = n / 2
        def a = call(x[0..<i])
        def b = call(x[i..<n])

        merge(a, b)

    }

    private static merge(a, b) {

        int n = a.size(), m = b.size()
        int i = 0, j = 0

        def result = []
        result.ensureCapacity n + m

        while (i < n && j < m) {
            if (a[i] <= b[j]) {
                result << a[i]
                i += 1
            } else {
                result << b[j]
                j += 1
            }
        }

        while (i < n) {
            result << a[i]
            i += 1
        }

        while (j < m) {
            result << b[j]
            j += 1
        }

        result

    }
}
