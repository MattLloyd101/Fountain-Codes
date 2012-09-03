
/**
 * Created with IntelliJ IDEA.
 * User: matthewl
 * Date: 31/08/12
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
object Main extends App {

    val rng = new RNG(0xBEEFCAFE)

    val file: Seq[IntBlock] = 0 until 10 map { _ => new IntBlock(rng.nextInt) }

    val stream = FountainSource.fileToFountainStream(file, rng, IntBlock)



}
