package com.somacode.template.service.tool

import java.util.*
import java.util.regex.Pattern

object StringTool {

    const val regexpUrl = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    private val regexpUrlPattern = Pattern.compile(regexpUrl, Pattern.CASE_INSENSITIVE)


    fun generatePasswordString(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid.substring(0, 4) + uuid.substring(uuid.length - 4, uuid.length)
    }

    fun validateUrl(url: String): Boolean {
        val matcher = regexpUrlPattern.matcher(url)
        return matcher.find()
    }

}