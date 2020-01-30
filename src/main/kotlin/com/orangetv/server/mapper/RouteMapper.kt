package com.orangetv.server.mapper

import com.orangetv.server.entity.Route
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select

@Mapper
interface RouteMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("""INSERT INTO route (host, sub_path, port) 
        VALUES (#{host},#{subPath},#{port})""")
    fun insert(route: Route): Int

    @Select("SELECT * FROM route WHERE id = #{id}")
    fun findById(id: Int): Route?

    @Select("SELECT * FROM route")
    fun findAll(): List<Route>
}
