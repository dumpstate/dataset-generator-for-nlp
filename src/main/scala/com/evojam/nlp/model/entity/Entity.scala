package com.evojam.nlp.model.entity

import java.util.StringTokenizer

import scala.collection.mutable

import com.evojam.nlp.model.Tag

abstract class Entity(val value: String, val tag: Tag) {
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
