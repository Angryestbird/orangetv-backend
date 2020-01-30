package com.orangetv.server.controller

import com.orangetv.server.entity.Movie
import com.orangetv.server.repository.MovieRepo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class searchController(val movieRepo: MovieRepo) {

    @GetMapping
    fun search(@RequestParam("q") query: String): List<Movie> {
        return movieRepo.search(query)
    }
}
