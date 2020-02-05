package com.orangetv.server.mapper

import com.orangetv.server.entity.Authority
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface AuthorityMapper {

    @Select("""SELECT * FROM authority WHERE user_id=#{userId}""")
    fun findByUser(userId: Int): List<Authority>

    @Delete("""DELETE FROM authority WHERE user_id=#{userId}""")
    fun removeByUser(userId: Int): Int

    @Insert("""INSERT INTO authority (user_id, role) VALUES (#{userId}, #{role})""")
    fun insert(authority: Authority): Int
}
