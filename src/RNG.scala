
/**
 * Created with IntelliJ IDEA.
 * User: matthewl
 * Date: 31/08/12
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */
class RNG(val seed:Int) {

    val self: java.util.Random = new java.util.Random(seed)

    def nextInt: Int = self.nextInt()

    def nextInt(n: Int): Int = self.nextInt(n)

    def nextPair = (nextRng, nextInt)

    def selectIndexes(n:Int):List[Int] = n match {
        case 0 => Nil
        case n => nextInt(list.length) :: selectIndexes(list, n-1)
    }

    def select[A](list:Seq[A], n:Int) = selectIndexes(n) map { list(_) }

    def nextSeed = nextInt

    def nextRng = new RNG(nextSeed)

}
