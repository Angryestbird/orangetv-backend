package com.orangetv.searchserver.controller

import com.orangetv.searchserver.domin.UploadDto
import com.orangetv.searchserver.entity.Movie
import com.orangetv.searchserver.repository.MovieRepo
import com.orangetv.searchserver.util.encode
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/movie")
class MovieController(val movieRepo: MovieRepo) {

    @GetMapping("/{id}.json")
    fun findInfoById(@PathVariable("id") id: Int): Movie {
        return movieRepo.findById(id)
    }

    @GetMapping("/{id}")
    fun findVideoById(@PathVariable("id") id: Int): RedirectView {
        val movie = movieRepo.findById(id)
        return RedirectView("http://${movie.host}/${encode(movie.videoUrl)}")
    }

    @GetMapping("/{id}/poster")
    fun findPosterById(@PathVariable("id") id: Int): RedirectView {
        val movie = movieRepo.findById(id)
        return RedirectView("http://${movie.host}/${encode(movie.posterUrl)}")
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