package org.format.mybatis.helper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.format.mybatis.helper.annotation.Column;
import org.format.mybatis.helper.entity.Entity;
import org.format.mybatis.helper.exception.MybatisHelperException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SqlProvider {
    
    public static String TABLE_NAME = "TABLE";

    public String query(Map<String, Object> dataMap) {
        try {
            Object model = dataMap.get("model");
            final Map<String, Object> entityData = getParam(model);
            return new SQL() {
                {
                    SELECT("*");
                    FROM(TABLE_NAME);
                    for(String column : entityData.keySet()) {
                        WHERE(column + "=#{model." + entityData.get(column) + "}");
                    }
                }
            }.toString();
        } catch(Exception e) {
            throw new MybatisHelperException("query sql error", e);
        }
    }

    public String count(Map<String, Object> dataMap) {
        try {
            Object model = dataMap.get("model");
            final Map<String, Object> entityData = getParam(model);
            return new SQL() {
                {
                    SELECT("count(*)");
                    FROM(TABLE_NAME);
                    for(String column : entityData.keySet()) {
                        WHERE(column + "=#{model." + entityData.get(column) + "}");
                    }
                }
            }.toString();
        } catch(Exception e) {
            throw new MybatisHelperException("count sql error", e);
        }
    }

    public String getAll(Map<String, Object> dataMap) {
        return new SQL() {
            {
                SELECT("*");
                FROM(TABLE_NAME);
            }
        }.toString();
    }

    public String insert(Entity model) {
        try {
            final Field[] fields = model.getClass().getDeclaredFields();
            return new SQL() {
                {
                    INSERT_INTO(TABLE_NAME);
                    for(int i = 0; i < fields.length; i ++) {
                        VALUES(fields[i].getAnnotation(Column.class) == null ? fields[i].getName() : fields[i].getAnnotation(Column.class).value(), "#{" + fields[i].getName() + "}");
                    }
                }
            }.toString();
        } catch(Exception e) {
            throw new MybatisHelperException("construct insert sql error", e);
        }
    }

    public String update(Entity model) {
        try {
            final Map<String, Object> entityData = getParam(model);
            return new SQL() {
                {
                    UPDATE(TABLE_NAME);
                    for(String column : entityData.keySet()) {
                        SET(column + "=#{" + entityData.get(column) + "}");
                    }
                    WHERE("id = #{id}");
                }
            }.toString();
        } catch(Exception e) {
            throw new MybatisHelperException("construct update sql error", e);
        }
    }

    public String delete(Map<String, Object> dataMap) {
        return new SQL() {
            {
                DELETE_FROM(TABLE_NAME);
                WHERE("id = #{id}");
            }
        }.toString();
    }

    private Map<String, Object> getParam(Object model) throws Exception {
        Class entity = model.getClass();
        Map<String, Object> entityData = new HashMap<String, Object>();
        if(model == null) {
            return entityData;
        }
        Field[] fields = entity.getDeclaredFields();
        for(int i = 0; i < fields.length; i ++) {
            fields[i].setAccessible(true);
            if(fields[i].get(model) != null) {
                String column = fields[i].getAnnotation(Column.class) == null ? fields[i].getName() : fields[i].getAnnotation(Column.class).value();
                entityData.put(column, fields[i].getName());
            }
        }
        return entityData;
    }


}
