package com.orangetv.server.mapper

import com.orangetv.server.entity.Movie
import com.orangetv.server.entity.Route
import org.apache.ibatis.annotations.*

@Mapper
interface RouteMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("""INSERT INTO route (host, sub_path, port) 
        VALUES (#{host},#{subPath},#{port})""")
    fun insert(route: Route): Int

    @Delete("""delete from route where id=#{id}""")
    fun delete(id: Int)

    @Select("""select * from route where id in
        (SELECT route_id FROM movie_route WHERE movie_id = #{id})""")
    fun findByMovie(MovieId: Int): Route

    @Insert("""INSERT INTO movie_route (movie_id, route_id) 
        VALUES (#{movie.id},#{route.id})""")
    fun addMovieRoute(
            @Param("movie") movie: Movie,
            @Param("route") route: Route
    ): Int

    @Select("SELECT * FROM route WHERE id = #{id}")
    fun findById(id: Int): Route?

    @Select("SELECT * FROM route")
    fun findAll(): List<Route>
}
