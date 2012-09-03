package com.mattlloyd.fountaincodes

object Main extends App {

    // TODO: Replace with a Soliton distribution
    val rng = new RNG(new java.util.Random().nextInt())

    val (meta, file) = FileCreator(IntBlockToString) { add =>
        "hello world" foreach { c => add(c.toInt) }
    }

    println("Created file of size " + meta.filesize)

    var stream = FountainSource.fileToFountainIterator(file, rng, IntBlockToString)

    // serialize meta and send to client.


    var gotRestoredFile = false
    var packetCount = 0

    def onComplete(s:String) {
        gotRestoredFile = true
        println("re-constructed file > "+s)
        println("packetCount> "+packetCount)
    }

    val drain = new FountainDrain(meta, onComplete)

    while(! gotRestoredFile) {
        packetCount += 1
        drain.recievePacket(stream.next())
    }
}
