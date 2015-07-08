package com.evojam.nlp.model

import scala.util.Random

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import com.evojam.nlp.model.entity.Date

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

  private def format(date: DateTime): String = value match {
    case "DAY-OF-WEEK" =>  s"${dayOfWeek(date.getDayOfWeek)}"
    case "MONTH" => s"${month(date.getMonthOfYear)}"
    case "MONTH-SHORT" => s"${monthShort(date.getMonthOfYear)}"
    case "MONTH-SHORT yyyy" => s"${monthShort(date.getMonthOfYear)} ${date.getYear}"
    case "MONTH yyyy" => s"${month(date.getMonthOfYear)} ${date.getYear}"
    case "yyyy MONTH-SHORT" => s"${date.getYear} ${monthShort(date.getMonthOfYear)}"
    case "yyyy MONTH" => s"${date.getYear} ${month(date.getMonthOfYear)}"
    case pattern@_ => DateTimeFormat.forPattern(pattern).print(date)
  }

  private def dayOfWeek(day: Int) = day match {
    case 1 => "monday"
    case 2 => "tuesday"
    case 3 => "wednesday"
    case 4 => "thursday"
    case 5 => "friday"
    case 6 => "saturday"
    case _ => "sunday"
  }

  private def month(m: Int) = m match {
    case 1 => "january"
    case 2 => "february"
    case 3 => "march"
    case 4 => "april"
    case 5 => "may"
    case 6 => "june"
    case 7 => "july"
    case 8 => "august"
    case 9 => "september"
    case 10 => "october"
    case 11 => "november"
    case _ => "december"
  }

  private def monthShort(m: Int) = m match {
    case 1 => "jan"
    case 2 => "feb"
    case 3 => "mar"
    case 4 => "apr"
    case 5 => "may"
    case 6 => "jun"
    case 7 => "jul"
    case 8 => "aug"
    case 9 => "sep"
    case 10 => "oct"
    case 11 => "nov"
    case _ => "dec"
  }

  private def pickDate(greaterThan: DateTime = MinDate): DateTime =
    greaterThan.plusHours(MinHours + Random.nextInt(MaxHours))
}
