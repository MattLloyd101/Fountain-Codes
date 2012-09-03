package com.mattlloyd.fountaincodes

object FileCreator {
    type File[Data] = List[Block[Data]]

    def apply[Data, Result, T](strat: BlockStrategy[Data, Result])(body: (Data => Unit) => T):(FileMeta[Data, Result], File[Data]) = {
        var n:Long = 0L
        var file: List[Block[Data]] = Nil

        def add(data:Data) { file = file :+ strat.createBlock({ val t = n; n += 1; t }, data)}
        body(add)

        (FileMeta(file.length.toLong, strat), file)
    }
}