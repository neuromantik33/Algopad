/*
 *  algopad.
 */

package algdsgn2.w5

import groovy.transform.Immutable
import groovy.transform.ToString
import groovyx.gpars.GParsPool

import java.util.concurrent.ConcurrentHashMap

import static algopad.common.misc.Counting.chooseK

/**
 * @author Nicolas Estrada.
 */
class TSP {

    /**
     * TODO
     *
     * @param distances
     * @return
     */
    @SuppressWarnings("GroovyLocalVariableNamingConvention")
    static float calculateMinimumTour(float[][] distances) {

        int n = distances.length
        def buildKey = { int end, List<Integer> ids ->
            def bs = new BitSet(n)
            ids.each bs.&set
            new CacheKey((byte) end, bs)
        }
        def cache = new ConcurrentHashMap<CacheKey, Float>()

        GParsPool.withPool {

            (1..<n).eachParallel { end ->
                def key = buildKey(end, [])
                cache[key] = distances[end][0]
            }

            def keys = new HashSet<>(cache.keySet())

            for (int k = 1; k < n - 1; k++) {
                chooseK(k, (1..<n))
                  .eachParallel { List<Integer> ids ->
                    (1..<n).each { i ->
                        if (i in ids) { return }
                        def key = buildKey(i, ids)

                        float min = Float.MAX_VALUE
                        ids.each { j ->
                            def removedJ = ids - j
                            def key2 = buildKey(j, removedJ)
                            def dist = cache[key2] + distances[i][j]
                            if (dist < min) {
                                min = dist
                            }
                        }

                        cache[key] = min

                    }
                }

                keys.each cache.&remove
                keys.clear()
                cache.keySet().each keys.&add

            }

            float min = Float.MAX_VALUE
            def ids = (1..<n)
            ids.each { j ->
                def removedJ = ids - j
                def key2 = buildKey(j, removedJ)
                def dist = cache[key2] + distances[0][j]
                if (dist < min) {
                    min = dist
                }
            }

            min

        }
    }

    @Immutable
    @ToString(includePackage = false)
    private static class CacheKey {
        byte x
        BitSet set
    }
}
