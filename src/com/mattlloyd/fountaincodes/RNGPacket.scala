package com.mattlloyd.fountaincodes

case class RNGPacket[Data](seed: Int, block: Block[Data]) extends Packet[Data] {
    lazy val rng = new RNG(seed)

    def encodedBlocks(fileLen:Long) = rng.selectIndexes(1 + rng.nextInt(fileLen.toInt - 1), fileLen.toInt) map { _.toLong }

}
