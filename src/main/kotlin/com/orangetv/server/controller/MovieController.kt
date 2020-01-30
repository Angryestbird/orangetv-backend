package com.orangetv.server.controller

import com.orangetv.server.domin.UploadDto
import com.orangetv.server.entity.Movie
import com.orangetv.server.repository.MovieRepo
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/movie")
class MovieController(val movieRepo: MovieRepo) {

    @GetMapping("/{id}.json")
    fun findInfoById(@PathVariable("id") id: Int): Movie {
        return movieRepo.findById(id)
    }

    @GetMapping("/after/{id}/limit/{limit}.json")
    fun findByAfter(@PathVariable("id") id: Int,
                    @PathVariable("limit") limit: Int
    ): List<Movie> {
        return movieRepo.findAfter(id, limit)
    }

    @PostMapping("/")
    fun upload(uploadInfo: UploadDto) {
        val host = uploadInfo.host
        val movies = uploadInfo.movies.map {
            it.copy(host = host)
        }
        movieRepo.upload(movies)
    }
}