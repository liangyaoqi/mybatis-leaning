package com.lyq.typehandler;

import com.lyq.entity.SexEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class SexEnumTypeHandle extends BaseTypeHandler<SexEnum> {
    /**
     * 这个方法是规定在结果映射的时候如何设置值
     *
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SexEnum parameter, JdbcType jdbcType) throws SQLException {
        log.debug("获取到到参数值{}", parameter);
        log.debug("序号i{}", i);
        ps.setInt(i, parameter.getValue());
    }

    /**
     * 根据列名获取值的时候自定义返回
     *
     * @param rs
     * @param columnName Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.debug("列名{}", columnName);
        Object object = rs.getObject(columnName);
        Integer sex = object != null && object instanceof Integer ? (Integer) object : null;
        return SexEnum.getByValue(sex);
    }

    /**
     * 根据列索引获取值的时候自定义返回
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object object = rs.getObject(columnIndex);
        Integer sex = object != null && object instanceof Integer ? (Integer) object : null;
        return SexEnum.getByValue(sex);
    }

    /**
     * 根据存储过程获取值的时候自定义返回
     *
     * @param cs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object object = cs.getObject(columnIndex);
        Integer sex = object != null && object instanceof Integer ? (Integer) object : null;
        return SexEnum.getByValue(sex);
    }
}
