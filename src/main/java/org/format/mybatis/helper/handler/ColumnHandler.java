package org.format.mybatis.helper.handler;

import java.lang.reflect.Field;

public interface ColumnHandler {

    String handle(Field field);

}
