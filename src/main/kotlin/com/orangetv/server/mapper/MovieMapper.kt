package com.orangetv.server.mapper

import com.orangetv.server.entity.Movie
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select

@Mapper
interface MovieMapper {

    @Select("""SELECT * FROM movie WHERE title LIKE #{query}""")
    fun search(query: String): List<Movie>

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("""INSERT INTO movie (title, poster_url, video_url, description) VALUES 
        (#{title}, #{posterUrl}, #{videoUrl}, #{description})""")
    fun insert(movie: Movie): Int

    @Select("""SELECT * from movie WHERE id > #{id} LIMIT #{limit}""")
    fun findAfter(id: Int, limit: Int): List<Movie>

    @Select("""SELECT * from movie WHERE id = #{id}""")
    fun findById(id: Int): Movie?

    @Select("""SELECT MAX(id) from movie""")
    fun findMaxId(): Int?
}
