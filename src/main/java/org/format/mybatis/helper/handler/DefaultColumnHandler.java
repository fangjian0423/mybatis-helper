package org.format.mybatis.helper.handler;

import org.format.mybatis.helper.annotation.Column;

import java.lang.reflect.Field;

public class DefaultColumnHandler implements ColumnHandler {

    @Override
    public String handle(Field field) {
        Column columnAnno = field.getAnnotation(Column.class);
        if(columnAnno != null) {
            return columnAnno.value();
        }
        String fieldName = field.getName();
        StringBuilder column = new StringBuilder();
        char[] charArr = fieldName.toCharArray();
        for(char c : charArr) {
            if (((int) c) >= 65 && ((int) c) <= 90) {
                column.append('_').append(c);
            } else {
                column.append((char) ((int) c - 32));
            }
        }
        return column.toString();
    }

}
