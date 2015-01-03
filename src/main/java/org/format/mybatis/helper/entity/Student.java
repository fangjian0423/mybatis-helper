package org.format.mybatis.helper.entity;


import org.format.mybatis.helper.annotation.Column;
import org.format.mybatis.helper.entity.Entity;

public class Student extends Entity {
    private String name;
    @Column("classroom_id")
    private Integer classroomId;

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
