package com.lyq.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel1 implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private SexEnum sex;
}