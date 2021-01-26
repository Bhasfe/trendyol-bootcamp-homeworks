package dev.akif.kodluyoruz.streetfinder

import java.io.File
import scala.io.Source

trait CsvLoader {
  def loadCsv(file: File): List[String] = {
    val csv : List[String] = Source.fromFile(file).getLines.drop(1).map(_.split(",")(1)).toList
    csv
  }
}