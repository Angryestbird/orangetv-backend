package com.orangetv.server.mapper

import com.orangetv.server.entity.Role
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface RoleMapper {

    @Select("SELECT * FROM role WHERE id = #{id}")
    fun findById(id: Int): Role?

    @Select("SELECT * FROM role WHERE role_name = #{name}")
    fun findByName(@Param("name") name: String): Role?

    @Select("SELECT * FROM role")
    fun findAll(): List<Role>
}
