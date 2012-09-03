package com.mattlloyd.fountaincodes

case class FileMeta[Data, Result](filesize: Long, strat: BlockStrategy[Data, Result])


