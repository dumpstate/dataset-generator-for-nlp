package com.evojam.nlp.model

case class Expression(value: String) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cannot be empty")

  def render(
    artist: Artist,
    venue: Venue,
    firstDate: Date,
    secondDate: Date,
    inDate: Date,
    onDate: Date,
    singularDet: SingularDeterminer,
    pluralDet: PluralDeterminer,
    directAdverb: DirectAdverb,
    singularPeriod: SingularPeriod,
    pluralPeriod: PluralPeriod): String =
    value.split("\\s+").foldLeft(List[String]()){
      case (list, token) => token match {
        case "ARTIST" => list ::: artist.tokenizeAndTag
        case "VENUE" => list ::: venue.tokenizeAndTag
        case "FDATE" => list ::: firstDate.tokenizeAndTag
        case "SDATE" => list ::: secondDate.tokenizeAndTag
        case "INDATE" => list ::: inDate.tokenizeAndTag
        case "ONDATE" => list ::: onDate.tokenizeAndTag
        case "SINGDET" => list ::: singularDet.tokenizeAndTag
        case "PLURDET" => list ::: pluralDet.tokenizeAndTag
        case "SINGPERIOD" => list ::: singularPeriod.tokenizeAndTag
        case "PLURPERIOD" => list ::: pluralPeriod.tokenizeAndTag
        case "DIRADV" => list ::: directAdverb.tokenizeAndTag
        case prep @ _ => list ::: Preposition(prep).tokenizeAndTag
      }
    } mkString("\n")
}
