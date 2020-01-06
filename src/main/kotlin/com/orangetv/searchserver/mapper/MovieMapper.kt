package com.orangetv.searchserver.mapper

import com.orangetv.searchserver.entity.Movie
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface MovieMapper {
    @Select("SELECT ROWID,* FROM movie WHERE movie MATCH #{query}")
    fun search(query: String): List<Movie>

    @Select("SELECT ROWID, * from movie WHERE ROWID = #{rowid}")
    fun findById(rowid: Int): Movie
}
