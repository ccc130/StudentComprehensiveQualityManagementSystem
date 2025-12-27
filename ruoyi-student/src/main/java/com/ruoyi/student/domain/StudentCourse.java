package com.ruoyi.student.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程信息对象 student_course
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
public class StudentCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String courseName;

    /** 课程代码 */
    @Excel(name = "课程代码")
    private String courseCode;

    /** 课程性质 */
    @Excel(name = "课程性质", dictType = "course_nature")
    private Long curriculum;

    /** 学分 */
    @Excel(name = "学分")
    private Double credit;

    /** 绩点 */
    @Excel(name = "绩点")
    private Double gpa;

    /** 开课学院 */
    @Excel(name = "开课学院")
    private String collegeOffering;

    /** 课程标记 */
    @Excel(name = "课程标记")
    private Long courseMarking;

    /** 课程类别 */
    @Excel(name = "课程类别", dictType = "course_category")
    private Long courseCategory;

    /** 课程归属 */
    @Excel(name = "课程归属")
    private String courseOwnership;

    /** 考核方式 */
    @Excel(name = "考核方式", dictType = "assessment_method")
    private Long teachingMethod;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCourseName(String courseName) 
    {
        this.courseName = courseName;
    }

    public String getCourseName() 
    {
        return courseName;
    }

    public void setCourseCode(String courseCode) 
    {
        this.courseCode = courseCode;
    }

    public String getCourseCode() 
    {
        return courseCode;
    }

    public void setCurriculum(Long curriculum) 
    {
        this.curriculum = curriculum;
    }

    public Long getCurriculum() 
    {
        return curriculum;
    }

    public void setCredit(Double credit)
    {
        this.credit = credit;
    }

    public Double getCredit()
    {
        return credit;
    }

    public void setGpa(Double gpa)
    {
        this.gpa = gpa;
    }

    public Double getGpa()
    {
        return gpa;
    }

    public void setCollegeOffering(String collegeOffering) 
    {
        this.collegeOffering = collegeOffering;
    }

    public String getCollegeOffering() 
    {
        return collegeOffering;
    }

    public void setCourseMarking(Long courseMarking) 
    {
        this.courseMarking = courseMarking;
    }

    public Long getCourseMarking() 
    {
        return courseMarking;
    }

    public void setCourseCategory(Long courseCategory) 
    {
        this.courseCategory = courseCategory;
    }

    public Long getCourseCategory() 
    {
        return courseCategory;
    }

    public void setCourseOwnership(String courseOwnership) 
    {
        this.courseOwnership = courseOwnership;
    }

    public String getCourseOwnership() 
    {
        return courseOwnership;
    }

    public void setTeachingMethod(Long teachingMethod) 
    {
        this.teachingMethod = teachingMethod;
    }

    public Long getTeachingMethod() 
    {
        return teachingMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("courseName", getCourseName())
            .append("courseCode", getCourseCode())
            .append("curriculum", getCurriculum())
            .append("credit", getCredit())
            .append("gpa", getGpa())
            .append("collegeOffering", getCollegeOffering())
            .append("courseMarking", getCourseMarking())
            .append("courseCategory", getCourseCategory())
            .append("courseOwnership", getCourseOwnership())
            .append("teachingMethod", getTeachingMethod())
            .toString();
    }
}
