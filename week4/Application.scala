package dev.akif.kodluyoruz.streetfinder

import java.io.File

/**
 * See CSV file at: https://github.com/makiftutuncu/kodluyoruz-scala/blob/main/data/streets.csv
 *
 * Original data: https://github.com/life/il-ilce-mahalle-sokak-cadde-sql
 */
object Application {
  def main(args: Array[String]): Unit = {

    // Traits
    val loader = new CsvLoader{}
    val finder = new StreetFinder{}

    // Read the file
    val file = new File("D:/kodluyoruz-scala/data/streets.csv")
    val data = loader.loadCsv(file)

    // Search the keywords
    val search = scala.io.StdIn.readLine().split(" ").toSet
    val matches = finder.findStreets(data,search)

    // Print the results
    for(element<-matches)
    {
      println(element)
    }
  }
}
