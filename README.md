## mybatis-helper ##
a simple helper framework for mybatis.

BaseDao provider some methods:

1. query
2. count
3. getAll
4. getById
5. insert
6. delete
7. update

Your dao can extend BaseDao, it requires a ResultMap named resultMap in each entity's mapper.xml.

mybatis-helper using interceptor to handle the page and sort sql.

The search pojo can extends DefaultPageAndSortEntity, so interceptor can add pageable and sortable function to the query.
