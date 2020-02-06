package com.orangetv.server.controller

import com.orangetv.server.domin.UploadDto
import com.orangetv.server.entity.Movie
import com.orangetv.server.entity.Route
import com.orangetv.server.mapper.RouteMapper
import com.orangetv.server.repository.MovieRepo
import com.orangetv.server.util.encodePath
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URL

@RestController
@RequestMapping("/api/v1/movie")
class MovieController(
        val movieRepo: MovieRepo,
        val routeMapper: RouteMapper
) {

    @GetMapping("/{id}.json")
    fun findInfoById(@PathVariable("id") id: Int): Movie? {
        return movieRepo.findById(id)?.let {
            it.mapRoute(routeMapper.findByMovie(it.id))
        }
    }

    @GetMapping("/after/{id}/limit/{limit}.json")
    fun findByAfter(@PathVariable("id") id: Int,
                    @PathVariable("limit") limit: Int
    ): List<Movie> {
        return movieRepo.findAfter(id, limit).map {
            it.mapRoute(routeMapper.findByMovie(it.id))
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun upload(@RequestBody uploadInfo: UploadDto) {
        movieRepo.upload(uploadInfo.movies, URL(uploadInfo.hostUrl))
    }

    companion object {
        val mapRoute: Movie.(Route) -> Movie = { route ->
            this.let {
                val url = "resource${route.id}/"
                it.copy(
                        posterUrl = "${url}${encodePath(it.posterUrl)}",
                        videoUrl = "${url}${encodePath(it.videoUrl)}"
                )
            }
        }
    }
}