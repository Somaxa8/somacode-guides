package com.somacode.template.service.tool

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DateTimeTool {

    fun isBeforeNowWithMargin(date: LocalDate, time: LocalTime, marginHours: Int): Boolean {
        val datetime = ZonedDateTime.of(date, time, ZoneId.systemDefault())
        return datetime.isBefore(ZonedDateTime.now().plusHours(marginHours.toLong()))
    }

}