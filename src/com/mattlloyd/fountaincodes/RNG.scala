package com.mattlloyd.fountaincodes

class RNG(val seed: Int) {

    val self: java.util.Random = new java.util.Random(seed)

    def nextInt: Int = self.nextInt()

    def nextInt(n: Int): Int = self.nextInt(n)

    def nextPair = (nextRng, nextInt)

    def selectIndexes(n: Int): List[Int] = n match {
        case 0 => Nil
        case n => nextInt(list.length) :: selectIndexes(list, n - 1)
    }

    def select[A](list: Seq[A], n: Int) = selectIndexes(n) map {
        list(_)
    }

    def nextSeed = nextInt

    def nextRng = new RNG(nextSeed)

}
