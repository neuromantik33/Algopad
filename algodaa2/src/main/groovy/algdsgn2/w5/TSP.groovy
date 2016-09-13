/*
 *  algopad.
 */

package algdsgn2.w5

import groovy.transform.CompileStatic

import static algopad.common.misc.Counting.chooseK
import static groovy.transform.TypeCheckingMode.SKIP
import static groovyx.gpars.GParsPool.withPool
import static java.lang.Float.MAX_VALUE
import static java.util.concurrent.TimeUnit.NANOSECONDS

/**
 * @author Nicolas Estrada.
 */
@CompileStatic
class TSP {

    final int n
    final float[][] distances
    final float[] cache

    TSP(float[][] distances) {
        this.n = distances.length
        this.distances = distances
        this.cache = new float[0x1FFFFFFF]
    }

    /**
     * TODO
     *
     * @return
     */
    @SuppressWarnings('GroovyLocalVariableNamingConvention')
    @CompileStatic(SKIP)
    def calculateMinimumTour() {

        // All points except the start point 0
        def allPts = (1..<n)

        withPool {

            for (end in allPts) {
                def key = buildKey(end, 0)
                cache[key] = distances[end][0]
            }

            for (int k = 1; k < n - 1; k++) {
                def now = System.nanoTime()
                chooseK(k, allPts)
                  .eachParallel(this.&calculateSolution)
                def delta = System.nanoTime() - now
                println "k = $k, time spent = ${NANOSECONDS.toSeconds(delta)}s"
            }

            searchMinDistance 0, allPts

        }
    }

    private void calculateSolution(List<Integer> ids) {
        int bitSet = buildBitSet(ids)
        for (int pt in (1..<n)) {
            if ((bitSet & maskFor(pt)) == 0) {
                int key = buildKey(pt, bitSet)
                cache[key] = searchMinDistance(pt, ids, bitSet)
            }
        }
    }

    private float searchMinDistance(int src, List<Integer> ids, int bitSet = buildBitSet(ids)) {
        float min = MAX_VALUE
        for (int dest in ids) {
            int key = buildKey(dest, bitSet & ~maskFor(dest))
            float dist = (float) cache[key] + distances[src][dest]
            if (dist < min) {
                min = dist
            }
        }
        min
    }

    private static int buildBitSet(List<Integer> ids) {
        int bitSet = 0
        for (int id in ids) {
            bitSet |= maskFor(id)
        }
        bitSet
    }

    private static int buildKey(int end, int bitSet) {
        (end << 24) + bitSet
    }

    private static int maskFor(int ix) { MASKS[ix - 1] }

    private static final int[] MASKS = [
      0b000000000000000000000001,
      0b000000000000000000000010,
      0b000000000000000000000100,
      0b000000000000000000001000,
      0b000000000000000000010000,
      0b000000000000000000100000,
      0b000000000000000001000000,
      0b000000000000000010000000,
      0b000000000000000100000000,
      0b000000000000001000000000,
      0b000000000000010000000000,
      0b000000000000100000000000,
      0b000000000001000000000000,
      0b000000000010000000000000,
      0b000000000100000000000000,
      0b000000001000000000000000,
      0b000000010000000000000000,
      0b000000100000000000000000,
      0b000001000000000000000000,
      0b000010000000000000000000,
      0b000100000000000000000000,
      0b001000000000000000000000,
      0b010000000000000000000000,
      0b100000000000000000000000
    ] as int[]

}
