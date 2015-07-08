package com.evojam.nlp.model.entity

import com.evojam.nlp.model.Tag

case class Date(override val value: String) extends Entity(value, Tag.Date)