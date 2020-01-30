package com.orangetv.server.mapper

import com.orangetv.server.entity.Movie
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MovieMapper {

    @Select("SELECT * FROM movie WHERE title LIKE #{query}")
    fun search(query: String): List<Movie>

    @Insert("""INSERT INTO movie (title, poster_url, video_url, host, description) VALUES 
        (#{movie.title},#{movie.posterUrl},#{movie.videoUrl},#{movie.host},#{movie.description})""")
    fun insert(movie: Movie)

    @Select("SELECT * from movie WHERE id > #{id} LIMIT #{limit}")
    fun findAfter(id: Int, limit: Int): List<Movie>

    @Select("SELECT * from movie WHERE id = #{id}")
    fun findById(id: Int): Movie
}
