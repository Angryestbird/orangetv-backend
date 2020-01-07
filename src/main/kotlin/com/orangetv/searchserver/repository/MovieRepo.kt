package com.orangetv.searchserver.repository

import com.orangetv.searchserver.mapper.MovieMapper
import org.springframework.stereotype.Repository

@Repository
class MovieRepo(val movieMapper: MovieMapper) {

    fun search(query: String) = movieMapper.search("%${query}%")
    fun findAfter(id: Int, limit: Int) = movieMapper.findAfter(id, limit)
    fun findById(id: Int) = movieMapper.findById(id)
}