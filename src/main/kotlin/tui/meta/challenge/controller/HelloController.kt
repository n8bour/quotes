package tui.meta.challenge.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/hello")
class HelloController {
    @Get
    fun hello(): String = "Hello with Micronaut and Kotlin!"

}