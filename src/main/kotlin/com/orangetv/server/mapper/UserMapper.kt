package com.orangetv.server.mapper

import com.orangetv.server.entity.User
import org.apache.ibatis.annotations.*

@Mapper
interface UserMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("""INSERT INTO user (name, email, password, motto, enabled) 
        VALUES (#{name}, #{email}, #{password}, #{motto}), #{enabled})""")
    fun insert(user: User): Int

    @Delete("""delete from user where id=#{id}""")
    fun delete(id: Int)

    @Select("SELECT * FROM user WHERE id = #{id}")
    fun findById(id: Int): User?

    @Select("SELECT * FROM user WHERE email = #{email}")
    fun findByEmail(email: String): User?

    @Select("SELECT * FROM user")
    fun findAll(): List<User>
}
