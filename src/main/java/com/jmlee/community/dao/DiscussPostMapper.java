package com.jmlee.community.dao;

import com.jmlee.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 在首页时userId是不存在的，只有进入个人主页才需要userId，考虑用动态SQL
    List<DiscussPost> selectDiscussPost(@Param("userId") Integer userId, @Param("offset") int offset, @Param("limit") int limit);

    // 查询帖子的总条数
    // @Param注解用于给参数取别名
    // 如果只有一个参数，并且会在<if>标签中使用，则必须要加别名
    Integer selectDiscussPostRows(@Param("userId") Integer userId);

}
