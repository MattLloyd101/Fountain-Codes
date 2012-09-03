package com.mattlloyd.fountaincodes

abstract class Packet[DATA] {
    val block: Block[DATA]

    def onHold()

    def encodedBlocks(fileLen:Long):Seq[Long]
}
