package org.format.mybatis.helper.query;


import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class DefaultPageAndSortEntity implements PageAndSortEntity {

    private Integer pageNo = 1;
    private Integer pageSize = 20;

    private List<String> sort;
    private List<String> sortDirection;

    @Override
    public String addPageAndSortSql() {
        StringBuilder sql = new StringBuilder();
        if(CollectionUtils.isNotEmpty(sort)) {
            for(int i = 0, j = sort.size(); i < j; i ++) {
                sql.append(" order by ").append(sort.get(i));
                if(sortDirection.get(i) != null) {
                    sql.append(" ").append(sortDirection.get(i));
                }
                if(i != sort.size() - 1) {
                    sql.append(",");
                }
            }
        }
        int start = (pageNo - 1) * pageSize;
        sql.append(" limit ").append(start).append(", ").append(pageSize);
        return sql.toString();
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public List<String> getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(List<String> sortDirection) {
        this.sortDirection = sortDirection;
    }

}
