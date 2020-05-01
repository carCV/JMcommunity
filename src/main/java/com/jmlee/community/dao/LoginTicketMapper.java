package com.jmlee.community.dao;

import com.jmlee.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {

    // 插入登录凭证
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values (#{userId}, #{ticket}, #{status}, #{expired}) "
    })
    @Options(useGeneratedKeys = true, keyProperty = "id") // 自动生成主键
    int insertLoginTicket(LoginTicket loginTicket);

    // 查询凭证
    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket ",
            "where ticket=#{ticket} "
    })
    LoginTicket selectByTicket(String ticket);

    // 根据凭证更新登录状态，0-有效，1-无效
    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket} "
    })
    int updateStatus(@Param("ticket") String ticket, @Param("status") Integer status);
}
