package com.evojam.nlp.model.entity

import com.evojam.nlp.model.Tag

case class Venue(override val value: String) extends Entity(value, Tag.Venue)
