package com.evojam.nlp.model

case class UnknownTagException(tag: String) extends Exception(s"Unknown tag: $tag")

case class Tag(value: String) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cannot be empty")

  override def toString() = value
}

object Tag {
  val Artist = Tag("ARTIST")
  val Venue = Tag("VENUE")
  val Date = Tag("DATE")
  val Time = Tag("TIME")
  val Preposition = Tag("PREP")
  val Determiner = Tag("DATE")
  val Period = Tag("DATE")

  def tagOf(value: String): Tag = value match {
    case Artist.value => Artist
    case Venue.value => Artist
    case Date.value => Date
    case Time.value => Time
    case Preposition.value => Preposition
    case tag@_ => throw UnknownTagException(tag)
  }
}
