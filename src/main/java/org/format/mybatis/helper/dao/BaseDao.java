package org.format.mybatis.helper.dao;

import org.apache.ibatis.annotations.*;
import org.format.mybatis.helper.entity.Entity;
import org.format.mybatis.helper.provider.SqlProvider;

import java.util.List;

public interface BaseDao<T extends Entity, DTO> {

    @SelectProvider(type = SqlProvider.class, method = "query")
    @ResultMap("resultMap")
    List<T> query(@Param("search")DTO search, @Param("entity")Class entityCls);

    @SelectProvider(type = SqlProvider.class, method = "count")
    int count(@Param("search")DTO search, @Param("entity")Class entityCls);

    @SelectProvider(type = SqlProvider.class, method = "getAll")
    List<T> getAll(@Param("entity")Class entityCls);

    @InsertProvider(type = SqlProvider.class, method = "insert")
    int insert(@Param("model")T entity, @Param("entity")Class entityCls);

    @DeleteProvider(type = SqlProvider.class, method = "delete")
    int delete(@Param("id")Long id, @Param("entity")Class entityCls);

    @UpdateProvider(type = SqlProvider.class, method = "update")
    int update(@Param("model")T entity, @Param("entity")Class entityCls);

}
