package org.format.mybatis.helper.dao;

import org.apache.ibatis.annotations.*;
import org.format.mybatis.helper.entity.Entity;
import org.format.mybatis.helper.provider.SqlProvider;
import org.format.mybatis.helper.query.DefaultPageAndSortEntity;

import java.util.List;

public interface BaseDao<T extends Entity, DTO extends DefaultPageAndSortEntity> {

    @SelectProvider(type = SqlProvider.class, method = "query")
    @ResultMap("resultMap")
    List<T> query(DTO search);

    @SelectProvider(type = SqlProvider.class, method = "count")
    int count(DTO search);

    @SelectProvider(type = SqlProvider.class, method = "getAll")
    List<T> getAll();

    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(T entity);

    @DeleteProvider(type = SqlProvider.class, method = "delete")
    int delete(Long id);

    @UpdateProvider(type = SqlProvider.class, method = "update")
    int update(T entity);

}
