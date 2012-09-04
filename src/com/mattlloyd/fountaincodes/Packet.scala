package com.mattlloyd.fountaincodes

abstract class Packet[DATA] {
    val block: Block[DATA]

    def onHold()

    def encodedBlockIds(fileLen:Long):Seq[Long]
}
