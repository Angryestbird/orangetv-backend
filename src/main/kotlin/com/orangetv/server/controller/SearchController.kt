package com.orangetv.server.controller

import com.orangetv.server.entity.Movie
import com.orangetv.server.repository.MovieRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchController(val movieRepo: MovieRepo) {

    @GetMapping
    fun search(@RequestParam("q") query: String): List<Movie> {
        return movieRepo.search(query)
    }
}
