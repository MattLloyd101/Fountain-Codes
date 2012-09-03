package com.mattlloyd.fountaincodes

abstract class Packet[DATA] {
    val block: Block[DATA]

    def encodedBlocks(fileLen:Long):Seq[Long]
}
