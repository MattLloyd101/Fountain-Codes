package com.mattlloyd.fountaincodes

abstract class Block[DATA] {

    val id: Int

    val data: DATA
}

trait BlockStrategy[DATA] {
    // symmetric
    def combine(b1: Block[DATA], b2: Block[DATA]): Block[DATA]

    def combine(blocks: Seq[Block[DATA]]): Block[DATA] = blocks.reduce(combine)

    def uncombine(b1: Block[DATA], b2: Block[DATA]): Block[DATA]
}

object IntBlock extends BlockStrategy[Int] {
    def combine(b1: Block[Int], b2: Block[Int]): Block[Int] = new IntBlock(b1.data ^ b2.data)

    def uncombine(b1: Block[Int], b2: Block[Int]): Block[Int] = new IntBlock(b1.data ^ b2.data)
}

class IntBlock(val data: Int) extends Block[Int] {
    val id = data

    override def toString = "[IntBlock(" + data.toString + ")]"
}