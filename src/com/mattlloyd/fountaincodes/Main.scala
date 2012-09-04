package com.mattlloyd.fountaincodes

object Main extends App {

    // TODO: Replace with a Soliton distribution
    val seed = new java.util.Random().nextInt()
    println("seed> 0x"+ ("%02X" format seed))
    val rng = new RNG(seed)
    val inputFile = """Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit.Lorem ipsum dolor sit amet, consectetur adipiscing elit."""

    val (meta, file) = FileCreator(IntBlockToString) { add =>
        inputFile foreach { c => add(c.toInt) }
    }

    println("inputFile> " + inputFile)
    println("Created file of size " + meta.filesize)

    var stream = FountainSource.fileToFountainIterator(file, rng, IntBlockToString)

    // serialize meta and send to client.


    var gotRestoredFile = false
    var packetCount = 0

    def onComplete(s:String) {
        gotRestoredFile = true
        println("re-constructed file > "+s)
        println("packetCount> "+packetCount)
        println("overhead  > "+(packetCount - meta.filesize))
        println("overhead %> "+(((packetCount.toDouble/ meta.filesize.toDouble) - 1) * 100))
    }

    val drain = new FountainDrain(meta, onComplete)

    while(! gotRestoredFile) {
        packetCount += 1
        drain.recievePacket(stream.next())
    }
}
