/*
 *  algopad.
 */

package algdsgn2.w5

import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap

import static groovy.transform.TypeCheckingMode.SKIP
import static groovy.util.GroovyCollections.subsequences
import static groovyx.gpars.GParsPool.withPool
import static groovyx.gpars.util.PoolUtils.retrieveDefaultPoolSize
import static java.lang.Float.MAX_VALUE as Infinity
import static java.util.concurrent.TimeUnit.NANOSECONDS

/**
 * TODO
 *
 * @author Nicolas Estrada.
 */
@CompileStatic
class TSP {

    final int n
    final float[][] distances
    final Map<Integer, Float> cache
    final List<Integer> allPts

    TSP(float[][] distances) {
        this.n = distances.length
        this.distances = distances
        this.cache = new ConcurrentHashMap<>(n*n, 0.75f, retrieveDefaultPoolSize())
        this.allPts = (1..<n) // All points except the start point 0
    }

    /**
     * TODO
     *
     * @return
     */
    @SuppressWarnings('GroovyLocalVariableNamingConvention')
    @CompileStatic(SKIP)
    def calculateMinimumTour() {

        withPool {

            def oldKeys = []
            def cleanCache = {
                oldKeys.each cache.&remove
                oldKeys.clear()
                oldKeys.addAll cache.keySet()
            }

            // println "Calculating combinations for ${allPts}"
            def combos = subsequences(allPts)
                           .groupByParallel { it.size() }

            def empty = buildBitSet([])
            for (int pt in allPts) {
                def key = buildKey(pt, empty)
                cache[key] = distances[pt][empty]
            }

            cleanCache()

            for (int k = 1; k < n - 1; k++) {
                // def now = System.nanoTime()
                combos[k].eachParallel(this.&calculateSolution)
                cleanCache()
                /*def delta = System.nanoTime() - now
                println "k = $k, time spent = ${NANOSECONDS.toSeconds(delta)}s"*/
            }

            searchMinDistance 0, allPts, buildBitSet(allPts)

        }
    }

    private void calculateSolution(List<Integer> points) {
        int bitSet = buildBitSet(points)
        for (int pt in allPts) {
            if ((bitSet & maskFor(pt)) == 0) {
                int key = buildKey(pt, bitSet)
                cache[key] = searchMinDistance(pt, points, bitSet)
            }
        }
    }

    private float searchMinDistance(int src, List<Integer> points, int bitSet) {
        float min = Infinity
        for (int dest in points) {
            int key = buildKey(dest, bitSet & ~maskFor(dest))
            float dist = (float) cache[key] + distances[src][dest]
            if (dist < min) {
                min = dist
            }
        }
        min
    }

    private static int buildBitSet(List<Integer> points) {
        int bitSet = 0
        for (int pt in points) {
            bitSet |= maskFor(pt)
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
