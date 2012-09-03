package com.mattlloyd.fountaincodes

case class RNGPacket[DATA](seed: Int, block: Block[DATA]) extends Packet[DATA] {
    lazy val rng = new RNG(seed)

    def encodedBlocks = rng.selectIndexes(1 + rng.nextInt(file.length - 1))

}
