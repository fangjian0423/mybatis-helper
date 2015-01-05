package org.format.mybatis.helper.entity;

import org.format.mybatis.helper.annotation.Column;
import org.format.mybatis.helper.query.DefaultPageAndSortEntity;

public class StudentDto extends DefaultPageAndSortEntity {

    private Long id;
    private String name;
    @Column("classroom_id")
    private Integer classroomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

}
