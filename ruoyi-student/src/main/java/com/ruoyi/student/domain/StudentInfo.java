package com.ruoyi.student.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 student_info
 *
 * @author 焦阳
 * @date 2025-07-01
 */
public class StudentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 学年 */
    @Excel(name = "学年", dictType = "school_year")
    private Long schoolYear;

    /** 学期 */
    @Excel(name = "学期")
    private Long semester;

    /** 学号 */
    @Excel(name = "学号")
    private String studentId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 教学班 */
    @Excel(name = "教学班")
    private String teachingClass;

    /** 总成绩 */
    @Excel(name = "总成绩")
    private Double totalScores;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setSchoolYear(Long schoolYear)
    {
        this.schoolYear = schoolYear;
    }

    public Long getSchoolYear()
    {
        return schoolYear;
    }

    public void setSemester(Long semester)
    {
        this.semester = semester;
    }

    public Long getSemester()
    {
        return semester;
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

    public void setTotalScores(Double totalScores)
    {
        this.totalScores = totalScores;
    }

    public Double getTotalScores()
    {
        return totalScores;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("schoolYear", getSchoolYear())
                .append("semester", getSemester())
                .append("studentId", getStudentId())
                .append("name", getName())
                .append("teachingClass", getTeachingClass())
                .append("totalScores", getTotalScores())
                .toString();
    }
}
