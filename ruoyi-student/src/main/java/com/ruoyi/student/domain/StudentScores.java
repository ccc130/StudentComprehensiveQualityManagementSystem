package com.ruoyi.student.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生成绩对象 student_scores
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
public class StudentScores extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long courseId;

    /** 学年 */
    @Excel(name = "学年", dictType = "school_year")
    private Long schoolYear;

    /** 学期 */
    @Excel(name = "学期")
    private Long semester;

    /** 课程代码 */
    @Excel(name = "课程代码")
    private String courseCode;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String courseName;

    /** 课程性质 */
    @Excel(name = "课程性质", dictType = "course_nature")
    private Long curriculum;

    /** 学分 */
    @Excel(name = "学分")
    private Double credit;

    /** 成绩备注 */
    @Excel(name = "成绩备注")
    private String scoreRemarks;

    /** 绩点 */
    @Excel(name = "绩点")
    private Double gpa;

    /** 成绩性质 */
    @Excel(name = "成绩性质", dictType = "nature_of_grades")
    private Long gradesNature;

    /** 是否学位课程 */
    @Excel(name = "是否学位课程", dictType = "yes_or_no")
    private Long degreeCourse;

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

    /** 教学班 */
    @Excel(name = "教学班")
    private String teachingClass;

    /** 任课教师 */
    @Excel(name = "任课教师")
    private String courseTeacher;

    /** 考核方式 */
    @Excel(name = "考核方式", dictType = "assessment_method")
    private Long teachingMethod;

    /** 学号 */
    @Excel(name = "学号")
    private String studentId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String studentName;

    /** 学生标记 */
    @Excel(name = "学生标记")
    private String studentMarking;

    /** 成绩 */
    @Excel(name = "成绩")
    private Double scores;

    /** 是否成绩作废 */
    @Excel(name = "是否成绩作废", dictType = "yes_or_no")
    private Long scoresMarking;

    /** 学分绩点 */
    @Excel(name = "学分绩点")
    private Double gradePoint;

    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
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

    public void setCourseCode(String courseCode) 
    {
        this.courseCode = courseCode;
    }

    public String getCourseCode() 
    {
        return courseCode;
    }

    public void setCourseName(String courseName) 
    {
        this.courseName = courseName;
    }

    public String getCourseName() 
    {
        return courseName;
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

    public void setScoreRemarks(String scoreRemarks) 
    {
        this.scoreRemarks = scoreRemarks;
    }

    public String getScoreRemarks() 
    {
        return scoreRemarks;
    }

    public void setGpa(Double gpa)
    {
        this.gpa = gpa;
    }

    public Double getGpa()
    {
        return gpa;
    }

    public void setGradesNature(Long gradesNature) 
    {
        this.gradesNature = gradesNature;
    }

    public Long getGradesNature() 
    {
        return gradesNature;
    }

    public void setDegreeCourse(Long degreeCourse) 
    {
        this.degreeCourse = degreeCourse;
    }

    public Long getDegreeCourse() 
    {
        return degreeCourse;
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

    public void setTeachingClass(String teachingClass) 
    {
        this.teachingClass = teachingClass;
    }

    public String getTeachingClass() 
    {
        return teachingClass;
    }

    public void setCourseTeacher(String courseTeacher) 
    {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseTeacher() 
    {
        return courseTeacher;
    }

    public void setTeachingMethod(Long teachingMethod) 
    {
        this.teachingMethod = teachingMethod;
    }

    public Long getTeachingMethod() 
    {
        return teachingMethod;
    }

    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    public String getStudentId()
    {
        return studentId;
    }

    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }

    public void setStudentMarking(String studentMarking) 
    {
        this.studentMarking = studentMarking;
    }

    public String getStudentMarking() 
    {
        return studentMarking;
    }

    public void setScores(Double scores)
    {
        this.scores = scores;
    }

    public Double getScores()
    {
        return scores;
    }

    public void setScoresMarking(Long scoresMarking) 
    {
        this.scoresMarking = scoresMarking;
    }

    public Long getScoresMarking() 
    {
        return scoresMarking;
    }

    public void setGradePoint(Double gradePoint)
    {
        this.gradePoint = gradePoint;
    }

    public Double getGradePoint()
    {
        return gradePoint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("courseId", getCourseId())
            .append("schoolYear", getSchoolYear())
            .append("semester", getSemester())
            .append("courseCode", getCourseCode())
            .append("courseName", getCourseName())
            .append("curriculum", getCurriculum())
            .append("credit", getCredit())
            .append("scoreRemarks", getScoreRemarks())
            .append("gpa", getGpa())
            .append("gradesNature", getGradesNature())
            .append("degreeCourse", getDegreeCourse())
            .append("collegeOffering", getCollegeOffering())
            .append("courseMarking", getCourseMarking())
            .append("courseCategory", getCourseCategory())
            .append("courseOwnership", getCourseOwnership())
            .append("teachingClass", getTeachingClass())
            .append("courseTeacher", getCourseTeacher())
            .append("teachingMethod", getTeachingMethod())
            .append("studentId", getStudentId())
            .append("studentName", getStudentName())
            .append("studentMarking", getStudentMarking())
            .append("scores", getScores())
            .append("scoresMarking", getScoresMarking())
            .append("gradePoint", getGradePoint())
            .toString();
    }
}
