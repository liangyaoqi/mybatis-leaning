package com.lyq.mapper;

import com.lyq.entity.UserModel;

import java.util.List;

public interface UserMapper {
    int insertUser(UserModel user);

    List<UserModel> listAll();
}
