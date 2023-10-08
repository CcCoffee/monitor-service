package com.study.monitor.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PgArrayStringTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgo = new PGobject();
        pgo.setType("text[]");
        pgo.setValue(parameter.toString().replace("[", "{").replace("]", "}"));
        ps.setObject(i, pgo);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : Arrays.stream(value.replace("{", "").replace("}", "").split(",")).map(v->{
                    if(v.indexOf("\"") == 0){
                        v = v.substring(1);
                    }
                    if(v.indexOf("\"") == v.length() - 1){
                        v = v.substring(0, v.length()-1);
                    }
                    return v;
                }).collect(Collectors.toList());
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : Arrays.stream(value.replace("{", "").replace("}", "").split(",")).map(v->{
                    if(v.indexOf("\"") == 0){
                        v = v.substring(1);
                    }
                    if(v.indexOf("\"") == v.length() - 1){
                        v = v.substring(0, v.length()-1);
                    }
                    return v;
                }).collect(Collectors.toList());
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : Arrays.stream(value.replace("{", "").replace("}", "").split(",")).map(v->{
                    if(v.indexOf("\"") == 0){
                        v = v.substring(1);
                    }
                    if(v.indexOf("\"") == v.length() - 1){
                        v = v.substring(0, v.length()-1);
                    }
                    return v;
                }).collect(Collectors.toList());
    }
}