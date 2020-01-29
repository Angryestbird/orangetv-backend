package com.orangetv.searchserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@SpringBootApplication
@EnableZuulProxy
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
