/**
 * Created with IntelliJ IDEA.
 * User: matthewl
 * Date: 31/08/12
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
class FountainDrain[Data](fileMeta: FileMeta, strat: BlockStrategy[Data]) {

    val decoded: Map[Int, Block[Data]] = Map[Int, Block[Data]]()
    val holdingPackets: List[RNGPacket] = Nil

    //http://blog.notdot.net/2012/01/Damn-Cool-Algorithms-Fountain-Codes
    def recievePacket(packet: RNGPacket) = {
        packet.encodedBlocks foldLeft (None) { bNum =>
            decoded.get(bNum) match {
                case Some(block) => Some(strat.uncombine(packet.block, block))
                case _ => None
            }
        } match {
            case Some(decodedBlock) => decoded +
        }
    }
}
