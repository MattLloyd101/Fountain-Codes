package com.mattlloyd.fountaincodes

class RNG(val seed: Int) {

    val self: java.util.Random = new java.util.Random(seed)

    def nextInt: Int = self.nextInt()

    def nextInt(n: Int): Int = self.nextInt(n)

    def nextLong: Long = self.nextLong()

    def nextLong(n: Long): Long = nextLong % n

    def nextPair = (nextRng, nextInt)

    def selectIndexes(n:Int, max:Int):List[Int] = selectIndexes({ () => nextInt(max) })(n)

    def selectIndexes(next: () => Int)(n:Int):List[Int] = n match {
        case 0 => Nil
        case n => next() :: selectIndexes(next)(n - 1)
    }

    def selectSolitonWeightedIndexes(n: Int, max: Int): List[Int] = {
        val soliton = solitonM(max)
        selectWeightedIndexes({ n:Int => soliton(n) })(n, max)
    }

    def selectSolitonWeightedIndex(max:Int):Int = {
        val soliton = solitonM(max)
        selectWeightedIndex({ n:Int => soliton(n) }, max)
    }

    // for reliable distributions 0 to max map weights reduce(_ + _) == 1.0
    def selectWeightedIndexes(weights:{ def apply(n:Int):Double })(n: Int, max: Int): List[Int] = selectIndexes(selectWeightedIndex(weights, max))(n)

    def selectWeightedIndex(weights:{def apply(n:Int):Double}, max:Int)():Int = {

        val weightTotal = (1 to max).foldLeft(0.0) { (out, n) => out + weights(n) }
        val r = self.nextDouble() * weightTotal

        // using find as foldLeft doesn't have an early bail.
        var currentWeight = 0.0
        (0 until max).sortBy { n => weights(n + 1) }.find { n =>
                currentWeight += weights(n + 1)
                currentWeight > r
        }.getOrElse(max-1)
    }

    def select[A](list: Seq[A], n: Int) = selectIndexes(n, list.length) map list

    def selectSolitonWeighted[A](list:Seq[A], n:Int) = selectSolitonWeightedIndexes(n, list.length) map list

    def encodedBlocks(fileLen:Long) = {
        val blockCount = 1 + selectSolitonWeightedIndex(fileLen.toInt)
        selectIndexes(blockCount, fileLen.toInt) map { _.toLong }
    }

    def nextSeed = nextInt

    def nextRng = new RNG(nextSeed)

    def soliton(max:Double)(n:Double):Double = n match {
        case 1 => 1 / max
        case k => 1 / (k * (k - 1))
    }

    // memoize the soliton function
    def solitonM = Memoize(Memoize(soliton))
}
