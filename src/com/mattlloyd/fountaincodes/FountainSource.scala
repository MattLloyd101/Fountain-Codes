package com.mattlloyd.fountaincodes

object FountainSource {

    def fileToFountainStream[DATA](file: Seq[Block[DATA]], d: Int, rng: RNG, blockStrategy: BlockStrategy[DATA]): Stream[Packet[DATA]] =
        new RNGPacket(rng.seed, blockStrategy.combine(rng.select(file, d))) #:: fileToFountainStream(file, rng.nextRng, blockStrategy)

    def fileToFountainStream[DATA](file: Seq[Block[DATA]], rng: RNG, blockStrategy: BlockStrategy[DATA]): Stream[Packet[DATA]] =
        fileToFountainStream(file, 1 + rng.nextInt(file.length - 1), rng, blockStrategy)


}
