package org.format.mybatis.helper.provider;

import org.apache.ibatis.annotations.Param;
import org.format.mybatis.helper.annotation.Column;
import org.format.mybatis.helper.entity.Entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SqlProvider {

    public String query(Map<String, Object> dataMap) {
        Class entity = getEntity(dataMap);
        String sql = select(entity.getSimpleName().toUpperCase(), "*");
        sql += where(null);
        return sql;
    }

    public String count(Map<String, Object> dataMap) {
        Class entity = getEntity(dataMap);
        String sql = select(entity.getSimpleName().toUpperCase(), "count(*)");
        sql += where(null);
        return sql;
    }

    public String getAll(Map<String, Object> dataMap) {
        Class entity = getEntity(dataMap);
        String sql = select(entity.getSimpleName().toUpperCase(), "count(*)");
        return sql;
    }

    public String insert(Map<String, Object> dataMap) {
        try {
            Class entity = getEntity(dataMap);
            Field[] fields = entity.getDeclaredFields();
            StringBuilder sql = new StringBuilder("insert into " + entity.getSimpleName().toUpperCase() + "(");
            for(int i = 0; i < fields.length; i ++) {
                if(fields[i].getAnnotation(Column.class) != null) {
                    sql.append(fields[i].getAnnotation(Column.class).value());
                } else {
                    sql.append(fields[i].getName());
                }
                if(i != fields.length - 1) {
                    sql.append(",");
                }
            }
            sql.append(") values(");
            for(int i = 0; i < fields.length; i ++) {
                sql.append("#{model." + fields[i].getName() + "}");
                if(i != fields.length - 1) {
                    sql.append(",");
                }
            }
            sql.append(")");
            return sql.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String update(Map<String, Object> dataMap) {
        Class entity = getEntity(dataMap);
        try {
            Field[] fields = entity.getDeclaredFields();
            Object model = dataMap.get("model");
            Map<String, Object> entityData = new HashMap<String, Object>();
            for(int i = 0; i < fields.length; i ++) {
                fields[i].setAccessible(true);
                if(fields[i].get(model) != null) {
                    String column = fields[i].getAnnotation(Column.class) == null ? fields[i].getName() : fields[i].getAnnotation(Column.class).value();
                    entityData.put(column, fields[i].getName());
                }
            }
            StringBuilder sql = new StringBuilder("update " + entity.getSimpleName().toUpperCase() + " set ");
            for(String column : entityData.keySet()) {
                sql.append(column + "=#{model." + entityData.get(column) + "},");
            }
            return sql.substring(0, sql.length() - 1) + " where id = #{model.id}";
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String delete(Map<String, Object> dataMap) {
        Class entity = getEntity(dataMap);
        String sql = "delete from " + entity.getSimpleName().toUpperCase() + " where id = #{id}";
        return sql;
    }

    private String select(String tableName, String fields) {
        return "select " + fields + " from " + tableName;
    }

    private String where(Map<String, Object> params) {
        if(params == null || params.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private Class getEntity(Map<String, Object> dataMap) {
        Object entityObj = dataMap.get("entity");
        if(entityObj == null) {
            throw new IllegalArgumentException("entity param is error");
        }
        return (Class) entityObj;
    }


}
