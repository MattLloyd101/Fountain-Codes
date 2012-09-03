package com.mattlloyd.fountaincodes

object Main extends App {

    val rng = new RNG(0xBEEFCAFE)

    val file: Seq[IntBlock] = 0 until 10 map {
        _ => new IntBlock(rng.nextInt)
    }

    val stream = FountainSource.fileToFountainStream(file, rng, IntBlock)


}
