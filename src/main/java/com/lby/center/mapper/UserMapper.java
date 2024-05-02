package com.lby.center.mapper;

import com.lby.center.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liu
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-04-25 19:17:47
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




