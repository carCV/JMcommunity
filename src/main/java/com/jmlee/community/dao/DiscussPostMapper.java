package com.jmlee.community.dao;

import com.jmlee.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 在首页未登录时userId是不存在的，只有进入个人主页才需要userId，考虑用动态SQL
    List<DiscussPost> selectDiscussPost(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    // 查询帖子的总条数
    // @Param注解用于给参数取别名
    // 如果只有一个参数，并且会在<if>标签中使用，则必须要加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    // 根据id查询帖子
    DiscussPost selectDiscussPostById(int id);

    // 新增一条帖子
    int insertDiscussPost(DiscussPost discussPost);

    // 更新评论数量
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);


    int updateType(@Param("id") int id, @Param("type") int type);

    int updateStatus(@Param("id") int id, @Param("status") int status);

}
