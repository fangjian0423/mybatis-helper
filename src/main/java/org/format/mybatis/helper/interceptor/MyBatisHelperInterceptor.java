package org.format.mybatis.helper.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.format.mybatis.helper.exception.MybatisHelperException;
import org.format.mybatis.helper.provider.SqlProvider;
import org.format.mybatis.helper.query.PageAndSortEntity;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Properties;

public class MyBatisHelperInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        try {
            Type[] types = target.getClass().getGenericInterfaces();
            for(Type type : types) {
                if(type == StatementHandler.class) {
                    StatementHandler sh = (StatementHandler) target;
                    Field field = ReflectionUtils.findField(sh.getClass(), "delegate");
                    ReflectionUtils.makeAccessible(field);
                    Object delegate = ReflectionUtils.getField(field, target);

                    Field mappedStatementField = ReflectionUtils.findField(delegate.getClass(), "mappedStatement");
                    ReflectionUtils.makeAccessible(mappedStatementField);
                    MappedStatement mappedStatement = (MappedStatement)ReflectionUtils.getField(mappedStatementField, delegate);

                    Collection<ResultMap> resultMapList = mappedStatement.getConfiguration().getResultMaps();

                    Class entityCls = null;


                    String key = mappedStatement.getResource().substring(0, mappedStatement.getResource().lastIndexOf(".")).replaceAll("/", ".") + ".resultMap";

                    for(ResultMap resultMap : resultMapList) {
                        if(resultMap.getId().equals(key)) {
                            entityCls = resultMap.getType();
                        }
                    }

                    if(entityCls == null) {
                        throw new MybatisHelperException("can not found a ResultMap named resultMap");
                    }

                    Field boundSqlField = ReflectionUtils.findField(delegate.getClass(), "boundSql");
                    ReflectionUtils.makeAccessible(boundSqlField);
                    BoundSql boundSql = (BoundSql) ReflectionUtils.getField(boundSqlField, delegate);
                    String sql = boundSql.getSql();

                    Field sqlField = ReflectionUtils.findField(boundSql.getClass(), "sql");
                    ReflectionUtils.makeAccessible(sqlField);

                    StringBuilder newSql = new StringBuilder(sql.replaceAll(SqlProvider.TABLE_NAME, entityCls.getSimpleName().toUpperCase()));

                    if(mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
                        // append page and sort sql
                        if(PageAndSortEntity.class.isAssignableFrom(boundSql.getParameterObject().getClass())) {
                            newSql.append(((PageAndSortEntity) boundSql.getParameterObject()).addPageAndSortSql());
                        }
                    }

                    ReflectionUtils.setField(sqlField, boundSql, newSql.toString());
                }
            }
            return target;
        } catch(Exception e) {
            if(e instanceof MybatisHelperException) {
                throw (MybatisHelperException)e;
            } else {
                throw new MybatisHelperException("mybatishelperinterceptor handle error", e);
            }
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
