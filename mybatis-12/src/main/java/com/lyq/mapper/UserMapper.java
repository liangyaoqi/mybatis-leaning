package com.lyq.mapper;

import com.lyq.entity.UserModel2;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<UserModel2> getList1(Map<String, Object> paramMap);

    List<UserModel2> getList2(Map<String, Object> paramMap);
    List<UserModel2> getList3(Map<String, Object> paramMap);

    int insert1(UserModel2 userModel2);
}
