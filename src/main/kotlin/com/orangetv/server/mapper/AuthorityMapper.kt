package com.orangetv.server.mapper

import com.orangetv.server.entity.Authority
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface AuthorityMapper {

    @Select("""SELECT * FROM authority WHERE user_id=#{userId}""")
    fun findByUser(userId: Int): List<Authority>
}
