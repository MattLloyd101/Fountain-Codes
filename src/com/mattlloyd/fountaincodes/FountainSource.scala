package com.mattlloyd.fountaincodes

object FountainSource {

    // Streams are elegant but flawed in that they cannot resize the stream. Once loaded always in memory.
    def fileToFountainStream[Data, Result](file: Seq[Block[Data]], d: Int, rng: RNG, blockStrategy: BlockStrategy[Data, Result]): Stream[Packet[Data]] = {
        new RNGPacket[Data](rng.seed, blockStrategy.combine(rng.select(file, d))) #:: fileToFountainStream(file, rng.nextRng, blockStrategy)
    }


    def fileToFountainStream[Data, Result](file: Seq[Block[Data]], rng: RNG, blockStrategy: BlockStrategy[Data, Result]): Stream[Packet[Data]] =
        fileToFountainStream(file, 1 + rng.nextInt(file.length - 1), rng, blockStrategy)


    def fileToFountainIterator[Data, Result](file: Seq[Block[Data]], initRng: RNG, blockStrategy: BlockStrategy[Data, Result]): Iterator[Packet[Data]] = new Iterator[Packet[Data]] {
        var rng = initRng
        def hasNext = true
        def next() = {
            val encodedBlocks = rng.encodedBlocks(file.length) map { _.toInt }
//            println("In  blockLen> "+encodedBlocks.length)
            val next = new RNGPacket[Data](rng.seed, blockStrategy.combine(encodedBlocks map file))
            rng = rng.nextRng
            next
        }
    }

}
