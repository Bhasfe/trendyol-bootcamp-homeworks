package dev.akif.kodluyoruz.streetfinder

trait StreetFinder {
  def findStreets(streets: List[String], names: Set[String]): List[String] = {
    var matches : List[String] = List()
    for(street <- streets){
      for(name<- names){
        if(street.contains(name) && !matches.contains(street)){
          matches= matches:+street
        }
      }
    }
    matches
  }
}


