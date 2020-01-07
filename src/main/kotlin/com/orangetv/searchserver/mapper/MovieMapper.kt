package com.orangetv.searchserver.mapper

import com.orangetv.searchserver.entity.Movie
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MovieMapper {
    @Select("SELECT * FROM movie WHERE title LIKE #{query}")
    fun search(query: String): List<Movie>

    @Select("SELECT * from movie WHERE id > #{id} LIMIT #{limit}")
    fun findAfter(id: Int, limit: Int): List<Movie>

    @Select("SELECT * from movie WHERE id = #{id}")
    fun findById(id: Int): Movie
}
