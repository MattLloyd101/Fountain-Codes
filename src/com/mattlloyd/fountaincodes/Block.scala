package com.mattlloyd.fountaincodes

abstract class Block[Data] {

    val id: Long

    val data: Data
}

trait BlockStrategy[Data, Result] {

    def createBlock(id:Long, data:Data):Block[Data]
    // symmetric
    def combine(b1: Block[Data], b2: Block[Data]): Block[Data]

    def combine(blocks: Seq[Block[Data]]): Block[Data] = blocks.reduce(combine)

    def uncombine(b1: Block[Data], b2: Block[Data]): Block[Data]

    def reconstruct(blocks: Seq[Block[Data]]):Result
}

object IntBlock extends BlockStrategy[Int, Seq[Int]] {
    case class IntBlock(id:Long, data: Int) extends Block[Int]

    def createBlock(id:Long, data:Int):Block[Int] = IntBlock(id, data)

    def combine(b1: Block[Int], b2: Block[Int]): Block[Int] = new IntBlock(b1.id ^ b2.id, b1.data ^ b2.data)

    def uncombine(b1: Block[Int], b2: Block[Int]): Block[Int] = new IntBlock(b1.id ^ b2.id, b1.data ^ b2.data)

    def reconstruct(blocks:Seq[Block[Int]]):Seq[Int] = blocks.map { _.data }
}

object CharBlock extends BlockStrategy[Char, String] {
    case class CharBlock(id:Long, data: Char) extends Block[Char]

    def createBlock(id:Long, data:Char):Block[Char] = CharBlock(id, data)

    def combine(b1: Block[Char], b2: Block[Char]): Block[Char] = new CharBlock(b1.id ^ b2.id, (b1.data ^ b2.data).toChar)

    def uncombine(b1: Block[Char], b2: Block[Char]): Block[Char] = new CharBlock(b1.id ^ b2.id, (b1.data ^ b2.data).toChar)

    def reconstruct(blocks: Seq[Block[Char]]):String = blocks.foldLeft("") { (out, chr) => out + chr.data }
}
