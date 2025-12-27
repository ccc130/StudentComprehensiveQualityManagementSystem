package com.ruoyi.student.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 student_info3
 * 
 * @author 焦阳
 * @date 2025-07-07
 */
public class StudentInfo3 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 学号 */
    @Excel(name = "学号", dictType = "school_year")
    private String studentId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 教学班 */
    @Excel(name = "教学班")
    private String teachingClass;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
    }

    public String getStudentId() 
    {
        return studentId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setTeachingClass(String teachingClass) 
    {
        this.teachingClass = teachingClass;
    }

    public String getTeachingClass() 
    {
        return teachingClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentId", getStudentId())
            .append("name", getName())
            .append("teachingClass", getTeachingClass())
            .toString();
    }
}
