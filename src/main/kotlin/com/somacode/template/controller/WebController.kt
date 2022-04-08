package com.somacode.template.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@Controller
class WebController {


    @GetMapping("/")
    fun getIndex(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("Hello World!")
    }

    @GetMapping("/login")
    fun getLogin(): ModelAndView {
        return ModelAndView("public/login")
    }

    @GetMapping("/swagger")
    fun getSwagger(): RedirectView {
        return RedirectView("/swagger-ui.html")
    }

}