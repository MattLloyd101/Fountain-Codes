package com.mattlloyd.fountaincodes

import scala.Some

class FountainDrain[Data, Result, T](fileMeta: FileMeta[Data,Result], val callback: Result => T) {

    var decodedCount:Long = 0L
    var decoded: Map[Long, Block[Data]] = Map[Long, Block[Data]]()
    var holdingPackets: List[Packet[Data]] = Nil

    //http://blog.notdot.net/2012/01/Damn-Cool-Algorithms-Fountain-Codes
    def recievePacket(packet: Packet[Data], allowRecurse:Boolean = true) {
        attemptDecode(packet) match {
            case Some(decodedBlock) if !decoded.contains(decodedBlock.id) => handleDecodedBlock(decodedBlock, allowRecurse)
            case _ =>
                packet.onHold()
                holdingPackets = packet :: holdingPackets
        }
    }

    protected def attemptDecode(packet:Packet[Data]):Option[Block[Data]] = {
        val blocks = packet.encodedBlocks(fileMeta.filesize)
        if(blocks.length == 1)
            Some(packet.block)
        else
            blocks.foldLeft (Some(packet.block):Option[Block[Data]]) { (out, blockNum) =>
                (out, decoded.get(blockNum)) match {
                    case (Some(currentBlock), Some(block)) => Some(fileMeta.strat.uncombine(currentBlock, block))
                    case _ => None
                }
            }
    }

    protected def handleDecodedBlock(decodedBlock:Block[Data], allowRecurse:Boolean) = {
        println("Decoded block! > " + decodedBlock.id)
        decoded = decoded + (decodedBlock.id -> decodedBlock)
        if(allowRecurse) holdingPackets foreach { p => recievePacket(p, false) }
        decodedCount += 1

//        println("decodedCount > "+decodedCount)
//        println("fileMeta.filesize > "+fileMeta.filesize)
        if(decodedCount >= fileMeta.filesize) {
            try {
                val out = reconstruct
                callback(out)
            } catch {
                case _ =>
            }
        }
    }

    protected def reconstruct = {
        fileMeta.strat.reconstruct(0L until fileMeta.filesize map { decoded(_) })
    }
}
