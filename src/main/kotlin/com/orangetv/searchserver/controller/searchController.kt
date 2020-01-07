package com.orangetv.searchserver.controller

import com.orangetv.searchserver.entity.Movie
import com.orangetv.searchserver.repository.MovieRepo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class searchController(val movieRepo: MovieRepo) {

    @GetMapping
    fun search(@RequestParam("q") query: String): List<Movie> {
        return movieRepo.search(query)
    }
}
