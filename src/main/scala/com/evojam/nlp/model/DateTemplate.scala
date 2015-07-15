package com.evojam.nlp.model

import java.util.Locale

import scala.util.Random

import com.evojam.nlp.model.entity.Date
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

case class DateTemplate(value: String) {
  require(value != null, "value cannot be null")
  require(value.nonEmpty, "value cennot be empty")

  private val MinDate = new DateTime(1990, 1, 1, 0, 0)
  private val MinHours = 4000
  private val MaxHours = 260000

  def pickDates(): (Date, Date) = {
    val first = pickDate()
    val second = pickDate(first)

    (Date(format(first)), Date(format(second)))
  }

  private def format(date: DateTime): String =
    DateTimeFormat
      .forPattern(value)
      .withLocale(Locale.US)
      .print(date).toLowerCase()

  private def pickDate(greaterThan: DateTime = MinDate): DateTime =
    greaterThan.plusHours(MinHours + Random.nextInt(MaxHours))
}
