package com.orangetv.server.service

import com.orangetv.server.entity.Route
import com.orangetv.server.mapper.RouteMapper
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable


@Service
class RouteService(val zuulProperties: ZuulProperties,
                   val zuulHandlerMapping: ZuulHandlerMapping,
                   val routeMapper: RouteMapper) {

    fun addRoute(route: Route)
            : Map<String, ZuulProperties.ZuulRoute>? {
        routeMapper.insert(route)
        val url = "http://${route.host}:${route.port}${route.subPath}"
        return ZuulProperties.ZuulRoute("/resource${route.id}/**", url).let {
            zuulProperties.routes.apply {
                putIfAbsent("resource${route.id}", it)
                zuulHandlerMapping.setDirty(true)
            }
        }
    }

    @Transactional
    fun removeRoute(@PathVariable("id") id: Int)
            : Map<String, ZuulProperties.ZuulRoute>? {
        return routeMapper.findById(id)?.let {
            zuulProperties.routes.apply {
                remove("resource$id")
                zuulHandlerMapping.setDirty(true)
                routeMapper.delete(id)
            }
        }
    }

    fun listRoutes(): Map<String, ZuulProperties.ZuulRoute>? {
        return zuulProperties.routes
    }
}