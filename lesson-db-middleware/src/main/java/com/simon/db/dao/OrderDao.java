package com.simon.db.dao;

import com.simon.db.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by sang on 2019/12/3.
 */
@Mapper
public interface OrderDao {
    @Insert("insert into t_order (product,amount,uid,create_time) value (#{product},#{amount},#{uid},#{createTime})")
        //@Insert("insert into t_order (id,product,amount,uid,create_time) value (#{id},#{product},#{amount},#{uid},#{createTime})")
    void addOrder(Order o);

    @Select("select id ,product,amount,create_time,uid from t_order where id = #{id}")
    Order get(@Param("id") Long id);
}


