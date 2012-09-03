package com.mattlloyd.fountaincodes

import scala.Some

class FountainDrain[Data](fileMeta: FileMeta, strat: BlockStrategy[Data]) {

    val decoded: Map[Int, Block[Data]] = Map[Int, Block[Data]]()
    val holdingPackets: List[RNGPacket] = Nil

    //http://blog.notdot.net/2012/01/Damn-Cool-Algorithms-Fountain-Codes
    def recievePacket(packet: RNGPacket) = {
        (packet.encodedBlocks foldLeft (None) {
            (out, bNum) =>
                decoded.get(bNum) match {
                    case Some(block) => Some(strat.uncombine(packet.block, block))
                    case _ => None
                }
        }) match {
            case Some(decodedBlock) => decoded
        }
    }
}
