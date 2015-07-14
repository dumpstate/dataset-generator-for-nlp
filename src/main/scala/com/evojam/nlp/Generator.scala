package com.evojam.nlp

import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

import scala.io.Source
import scala.util.Random

import com.evojam.nlp.model.{Expression, DateTemplate}
import com.evojam.nlp.model.entity.{Period, Determiner, Venue, Artist}

object Generator extends App {
  def filterNonAlphaChars(str: String) =
    str
      .replaceAll("[-_]", " ")
      .replaceAll("[^a-z0-9 ]", "")
      .replaceAll("\\s+", " ")

  lazy val artists = Source.fromFile("src/main/resources/artist.txt")
    .getLines().toList
    .map(_.toLowerCase)
    .map(filterNonAlphaChars)
    .filter(_.nonEmpty)
    .map(Artist)

  lazy val venues = Source.fromFile("src/main/resources/venues.txt")
    .getLines().toList
    .map(_.toLowerCase)
    .map(filterNonAlphaChars)
    .filter(_.nonEmpty)
    .map(Venue)

  lazy val determiners = Source.fromFile("src/main/resources/determiners.txt")
    .getLines().toList
    .map(_.toLowerCase)
    .map(filterNonAlphaChars)
    .filter(_.nonEmpty)
    .map(Determiner)

  lazy val periods = Source.fromFile("src/main/resources/periods.txt")
    .getLines().toList
    .map(_.toLowerCase)
    .map(filterNonAlphaChars)
    .filter(_.nonEmpty)
    .map(Period)

  lazy val dateTemplates = Source.fromFile("src/main/resources/date-template.txt")
    .getLines().toList
    .filter(_.nonEmpty)
    .map(DateTemplate)

  lazy val expressions = Source.fromFile("src/main/resources/expression.txt")
    .getLines().toList
    .filter(_.nonEmpty)
    .map(Expression)

  def next[T](list: List[T], i: Int): T =
    list(i % list.length)

  println(s"Artists: ${artists.size}, Venues: ${venues.size}, DateTemplates: ${dateTemplates.size}, Expressions: ${expressions.size}")

  val trainingSetSize = 500

  val out = new FileOutputStream("out.train")

  for {
    i <- 0 to trainingSetSize
  } {
    val (firstDate, secondDate) = next(dateTemplates, i).pickDates()
    val bytes = next(expressions, i).render(
      next(artists, i),
      next(venues, i),
      firstDate,
      secondDate,
      next(determiners, i),
      next(periods, i))
      .getBytes(StandardCharsets.UTF_8)
    out.write(bytes)
    out.write("\n\n".getBytes(StandardCharsets.UTF_8))
  }
  out.close()
}
