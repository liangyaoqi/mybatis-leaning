package com.lyq.entity;

/**
 * 性别
 */
public enum SexEnum {
    UNKNOW(0, "未知"),
    MAN(1, "男"),
    WOMAN(2, "女");
    private Integer value;
    private String name;
    SexEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
    public Integer getValue() {
        return value;
    }
    public String getName() {
        return name;
    }
    public static SexEnum getByValue(Integer value) {
        for (SexEnum item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
