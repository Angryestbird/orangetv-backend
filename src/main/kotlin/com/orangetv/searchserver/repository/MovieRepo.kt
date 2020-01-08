package com.orangetv.searchserver.repository

import com.orangetv.searchserver.entity.Movie
import com.orangetv.searchserver.mapper.MovieMapper
import org.apache.ibatis.session.ExecutorType
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.stereotype.Repository

@Repository
class MovieRepo(val movieMapper: MovieMapper, val sqlSessionFactory: SqlSessionFactory) {

    fun search(query: String) = movieMapper.search("%${query}%")
    fun findAfter(id: Int, limit: Int) = movieMapper.findAfter(id, limit)
    fun findById(id: Int) = movieMapper.findById(id)
    fun upload(movies: List<Movie>) {
        sqlSessionFactory.openSession(ExecutorType.BATCH).use {
            val mapper: MovieMapper = it.getMapper(MovieMapper::class.java)
            for (movie in movies) {
                mapper.insert(movie)
            }
            it.commit()
        }
    }
}