package com.mattlloyd.fountaincodes

class RNG(val seed: Int) {

    val self: java.util.Random = new java.util.Random(seed)

    def nextInt: Int = self.nextInt()

    def nextInt(n: Int): Int = self.nextInt(n)

    def nextLong: Long = self.nextLong()

    def nextLong(n: Long): Long = nextLong % n

    def nextPair = (nextRng, nextInt)

    def selectIndexes(n: Int, max: Int): List[Int] = n match {
        case 0 => Nil
        case n => nextInt(max) :: selectIndexes(n - 1, max)
    }

    def select[A](list: Seq[A], n: Int) = {
        val indexes = selectIndexes(n, list.length)
        //println("indexes generated> " + indexes)
        indexes map {
            l =>
            // TODO: lists can't handle Long indexes...
                list(l)
        }
    }

    def nextSeed = nextInt

    def nextRng = new RNG(nextSeed)

}
