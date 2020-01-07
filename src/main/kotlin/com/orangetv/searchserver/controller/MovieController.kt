package com.orangetv.searchserver.controller

import com.orangetv.searchserver.entity.Movie
import com.orangetv.searchserver.repository.MovieRepo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movie")
class MovieController(val movieRepo: MovieRepo) {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Int): Movie {
        return movieRepo.findById(id)
    }

    @GetMapping("/after/{id}/limit/{limit}")
    fun findByAfter(@PathVariable("id") id: Int,
                    @PathVariable("limit") limit: Int
    ): List<Movie> {
        return movieRepo.findAfter(id, limit)
    }
}
