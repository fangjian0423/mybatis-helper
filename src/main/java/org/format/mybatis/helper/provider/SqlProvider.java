package org.format.mybatis.helper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.format.mybatis.helper.annotation.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SqlProvider {

    public String query(Map<String, Object> dataMap) {
        final Class entity = getEntity(dataMap);
        return new SQL() {
            {
                SELECT("*");
                FROM(entity.getSimpleName().toUpperCase());
                //TODO 加上查询条件
            }
        }.toString();
    }

    public String count(Map<String, Object> dataMap) {
        final Class entity = getEntity(dataMap);
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(entity.getSimpleName().toUpperCase());
                //TODO 加上查询条件
            }
        }.toString();
    }

    public String getAll(Map<String, Object> dataMap) {
        final Class entity = getEntity(dataMap);
        return new SQL() {
            {
                SELECT("*");
                FROM(entity.getSimpleName().toUpperCase());
            }
        }.toString();
    }

    public String insert(Map<String, Object> dataMap) {
        try {
            final Class entity = getEntity(dataMap);
            final Field[] fields = entity.getDeclaredFields();
            return new SQL() {
                {
                    INSERT_INTO(entity.getSimpleName().toUpperCase());
                    for(int i = 0; i < fields.length; i ++) {
                        VALUES(fields[i].getAnnotation(Column.class) == null ? fields[i].getName() : fields[i].getAnnotation(Column.class).value(), "#{model." + fields[i].getName() + "}");
                    }
                }
            }.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String update(Map<String, Object> dataMap) {
        try {
            final Class entity = getEntity(dataMap);
            Field[] fields = entity.getDeclaredFields();
            Object model = dataMap.get("model");
            final Map<String, Object> entityData = new HashMap<String, Object>();
            for(int i = 0; i < fields.length; i ++) {
                fields[i].setAccessible(true);
                if(fields[i].get(model) != null) {
                    String column = fields[i].getAnnotation(Column.class) == null ? fields[i].getName() : fields[i].getAnnotation(Column.class).value();
                    entityData.put(column, fields[i].getName());
                }
            }
            return new SQL() {
                {
                    UPDATE(entity.getSimpleName().toUpperCase());
                    for(String column : entityData.keySet()) {
                        SET(column + "=#{model." + entityData.get(column) + "}");
                    }
                    WHERE("id = #{model.id}");
                }
            }.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String delete(Map<String, Object> dataMap) {
        final Class entity = getEntity(dataMap);
        return new SQL() {
            {
                DELETE_FROM(entity.getSimpleName().toUpperCase());
                WHERE("id = #{id}");
            }
        }.toString();
    }

    private Class getEntity(Map<String, Object> dataMap) {
        Object entityObj = dataMap.get("entity");
        if(entityObj == null) {
            throw new IllegalArgumentException("entity param is error");
        }
        return (Class) entityObj;
    }


}
