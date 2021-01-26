package dev.akif.kodluyoruz.flist

object FListDemo {
  def main(args: Array[String]): Unit = {
    val list = (1 to 100 by 2).toList

    // Initialize Flists
    val flist1 = FList(1, 2, 3)
    val flist2 = FList(list)
    val flist3 = FList("hello", "world")

    // Print Lists
    println("FLISTS")
    println(flist1)
    println(flist2)
    println(flist3)

    println("\n")

    // Append
    println("APPEND")
    println(flist1.append(4))
    println(flist1 :+ 4)

    println("\n")

    // Prepend
    println("PREPEND")
    println(flist1.prepend(0))
    println(0 +: flist1)

    println("\n")

    // Concat
    println("CONCAT")
    println(flist1.concat(flist3))
    println(flist1 ++ flist3)

    println("\n")

    // Map
    println("MAP")
    println(flist1.map(i => i + 20))

    println("\n")

    // Size
    println("SIZE")
    println(flist1.size)

    println("\n")

    // Get
    println("GET")
    println(flist1.get(2))
    println(flist1.get(3))

    println("\n")

    // Fold
    println("FOLD")
    println(flist1.fold(0)(_ + _))

    println("\n")

    // FlatMap
    println("FLATMAP")
    println(flist1.fold(0)(_ + _))

    println("\n")

  }
}
