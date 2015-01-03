package org.format.mybatis.helper.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.format.mybatis.helper.entity.Student;
import org.format.mybatis.helper.provider.SqlProvider;

import java.util.Properties;

public class ExecutorInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
