package com.orangetv.server.controller

import com.orangetv.server.entity.Route
import com.orangetv.server.mapper.RouteMapper
import com.orangetv.server.repository.MovieRepo
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/route")
class RouteController(val movieRepo: MovieRepo,
                      val zuulProperties: ZuulProperties,
                      val zuulHandlerMapping: ZuulHandlerMapping,
                      val routeMapper: RouteMapper) {

    @PostMapping("/")
    fun addRoute(@RequestBody route: Route)
            : Map<String, ZuulProperties.ZuulRoute>? {
        routeMapper.insert(route)
        val url = "http://${route.host}:${route.port}${route.subPath}"
        return ZuulProperties.ZuulRoute("/resource${route.id}/**", url).let {
            zuulProperties.routes.apply {
                putIfAbsent("${route.id}", it)
                zuulHandlerMapping.setDirty(true)
            }
        }
    }

    @DeleteMapping("/{id}")
    fun removeRoute(@PathVariable("id") id: Int)
            : Map<String, ZuulProperties.ZuulRoute>? {
        return routeMapper.findById(id)?.let {
            zuulProperties.routes.apply {
                remove("$id")
                zuulHandlerMapping.setDirty(true)
            }
        }
    }

    @GetMapping("/")
    fun listRoutes(): Map<String, ZuulProperties.ZuulRoute>? {
        return zuulProperties.routes
    }
}