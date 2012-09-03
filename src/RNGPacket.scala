/**
 * Created with IntelliJ IDEA.
 * User: matthewl
 * Date: 31/08/12
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
case class RNGPacket[DATA](seed:Int, block:Block[DATA]) extends Packet[DATA] {
    lazy val rng = new RNG(seed)

    def encodedBlocks = rng.selectIndexes(1 + rng.nextInt(file.length - 1))

}
