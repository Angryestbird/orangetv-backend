package com.orangetv.server.repository

import com.orangetv.server.entity.Movie
import com.orangetv.server.entity.Route
import com.orangetv.server.mapper.MovieMapper
import com.orangetv.server.mapper.RouteMapper
import com.orangetv.server.service.RouteService
import org.apache.ibatis.session.ExecutorType
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.net.URL

@Repository
class MovieRepo(
        val movieMapper: MovieMapper,
        val sqlSessionFactory: SqlSessionFactory,
        val routeService: RouteService
) {

    fun search(query: String) = movieMapper.search("%${query}%")
    fun findAfter(id: Int, limit: Int) = movieMapper.findAfter(id, limit)
    fun findById(id: Int) = movieMapper.findById(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun upload(movies: List<Movie>, url: URL) {
        val route = Route(host = url.host, port = url.port, subPath = url.file)
        routeService.addRoute(route)
        sqlSessionFactory.openSession(ExecutorType.BATCH).use {
            val movieMapper: MovieMapper = it.getMapper(MovieMapper::class.java)
            val routeMapper: RouteMapper = it.getMapper(RouteMapper::class.java)
            var maxMovieId = movieMapper.findMaxId() ?: 0
            for (movie in movies) {
                movieMapper.insert(movie)
                routeMapper.addMovieRoute(movie.copy(id = ++maxMovieId), route)
            }
            it.commit()
        }
    }
}