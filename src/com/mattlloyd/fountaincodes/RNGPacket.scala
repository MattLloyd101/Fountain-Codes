package com.mattlloyd.fountaincodes

case class RNGPacket[Data](seed: Int, block: Block[Data]) extends Packet[Data] {
    var rng = new RNG(seed)



    def encodedBlocks(fileLen:Long) = rng.encodedBlocks(fileLen)

    def onHold() {
        rng = new RNG(seed)
    }
}
