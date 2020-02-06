package com.orangetv.server.controller

import com.orangetv.server.entity.Route
import com.orangetv.server.service.RouteService
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/route")
class RouteController(val routeService: RouteService) {

    @PostMapping("/")
    fun addRoute(@RequestBody route: Route)
            : Map<String, ZuulProperties.ZuulRoute>? {
        return routeService.addRoute(route)
    }

    @DeleteMapping("/{id}")
    fun removeRoute(@PathVariable("id") id: Int)
            : Map<String, ZuulProperties.ZuulRoute>? {
        return routeService.removeRoute(id)
    }

    @GetMapping("/")
    fun listRoutes(): Map<String, ZuulProperties.ZuulRoute>? {
        return routeService.listRoutes()
    }
}