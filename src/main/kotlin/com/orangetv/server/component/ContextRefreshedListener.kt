package com.orangetv.server.component

import com.orangetv.server.mapper.RouteMapper
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ContextRefreshedListener(val zuulProperties: ZuulProperties,
                               val zuulHandlerMapping: ZuulHandlerMapping,
                               val routeMapper: RouteMapper) {

    @EventListener(ContextRefreshedEvent::class)
    fun contextRefreshed() {
        routeMapper.findAll().forEach { route ->
            val url = "http://${route.host}:${route.port}${route.subPath}"
            ZuulProperties.ZuulRoute("/resource${route.id}/**", url).let {
                zuulProperties.routes.apply {
                    putIfAbsent("${route.id}", it)
                }
            }
        }
        zuulHandlerMapping.setDirty(true)
    }
}