package com.lyq.mapper;

import com.lyq.entity.UserModel1;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    // 解决报错，修改实体别名或修改类名
    List<UserModel1> getListUser();

    int insert1(Map<String,Object> map);
}
