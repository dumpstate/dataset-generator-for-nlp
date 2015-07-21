package com.evojam.nlp

import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

import scala.io.Source
import scala.util.Random

import com.evojam.nlp.model._

object Generator extends App {
  def filterNonAlphaChars(str: String) =
    str
      .replaceAll("[-_]", " ")
      .replaceAll("[^a-z0-9 ]", "")
      .replaceAll("\\s+", " ")

  def load[T](
    file: String,
    f: String => T,
    removeNonAlphaChars: Boolean = true,
    toLowerCase: Boolean = true): List[T] =
    Source.fromFile(s"src/main/resources/$file.txt")
      .getLines().toList
      .map(line => if (toLowerCase) line.toLowerCase else line)
      .map(line => if (removeNonAlphaChars) filterNonAlphaChars(line) else line)
      .filter(_.nonEmpty)
      .map(f)

  lazy val artists = load("artist", Artist)
  lazy val venues = load("venues", Venue)
  lazy val singularDet = load("singulardet", SingularDeterminer)
  lazy val pluralDet = load("pluraldet", PluralDeterminer)
  lazy val directAdverb = load("diradv", DirectAdverb)
  lazy val singularPeriod = load("singularperiod", SingularPeriod)
  lazy val pluralPeriod = load("pluralperiod", PluralPeriod)
  lazy val dateTemplates = load("date-template", DateTemplate, false, false)
  lazy val inDateTemplates = load("indates", InDateTemplate, false, false)
  lazy val onDateTemplates = load("ondates", OnDateTemplate, false, false)
  lazy val expressions = load("expression", Expression, false, false)

  def pickSingle[T](list: List[T]): T = {
    require(list != null, "list cannot be null")
    require(list.nonEmpty, "lsit cannot be empty")

    list(Random.nextInt(list.size))
  }

  println(s"Artists: ${artists.size}, Venues: ${venues.size}, DateTemplates: ${dateTemplates.size}, Expressions: ${expressions.size}")

  val trainingSetSize = 100000

  val out = new FileOutputStream("out.train")

  for (i <- 0 to trainingSetSize) {
    val (firstDate, secondDate) = pickSingle(dateTemplates).pickDates
    val (inDate, _) = pickSingle(inDateTemplates).pickDates
    val (onDate, _) = pickSingle(onDateTemplates).pickDates
    val expressionBytes = pickSingle(expressions)
      .render(
        pickSingle(artists),
        pickSingle(venues),
        firstDate,
        secondDate,
        inDate,
        onDate,
        pickSingle(singularDet),
        pickSingle(pluralDet),
        pickSingle(directAdverb),
        pickSingle(singularPeriod),
        pickSingle(pluralPeriod))
      .getBytes(StandardCharsets.UTF_8)

    out.write(expressionBytes)
    out.write("\n\n".getBytes(StandardCharsets.UTF_8))
  }

  out.close()
}
