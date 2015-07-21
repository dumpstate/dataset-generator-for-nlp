package com.evojam.nlp.model

import java.util.StringTokenizer

import scala.collection.mutable

sealed abstract class Entity(val value: String, val tag: Tag) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cannot be empty")

  def tokenize(): List[String] = {
    val tokenizer = new StringTokenizer(value)
    var list = mutable.ArrayBuffer[String]()
    while(tokenizer.hasMoreTokens) {
      list += tokenizer.nextToken()
    }
    list.toList
  }

  def tokenizeAndTag(): List[String] =
    tokenize.map(token => {
      val sb = new StringBuilder()
      sb.append(token)
      sb.append(" ")
      sb.append(tag.value)
      sb.toString
    })

  override def toString() = value
}

case class Artist(override val value: String) extends Entity(value, Tag.Artist)
case class Date(override val value: String) extends Entity(value, Tag.Date)
case class SingularDeterminer(override val value: String) extends Entity(value, Tag.Date)
case class PluralDeterminer(override val value: String) extends Entity(value, Tag.Date)
case class DirectAdverb(override val value: String) extends Entity(value, Tag.Date)
case class SingularPeriod(override val value: String) extends Entity(value, Tag.Date)
case class PluralPeriod(override val value: String) extends Entity(value, Tag.Date)
case class Preposition(override val value: String) extends Entity(value, Tag.Preposition)
case class Venue(override val value: String) extends Entity(value, Tag.Venue)