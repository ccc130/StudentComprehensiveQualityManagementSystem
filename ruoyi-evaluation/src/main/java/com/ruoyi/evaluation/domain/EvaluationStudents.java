package com.ruoyi.evaluation.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生评价对象 evaluation_students
 * 
 * @author 刘桐
 * @date 2025-07-01
 */
public class EvaluationStudents extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 评分 */
    @Excel(name = "评分")
    private Long scores;

    /** 评价人 */
    @Excel(name = "评价人")
    private String name;

    /** 评价标记 */
    @Excel(name = "评价标记")
    private Integer isevaluation;

    /** 评价人id */
    @Excel(name = "评价人id")
    private String nameId;

    /** 班级 */
    @Excel(name = "班级")
    private String teachingClass;

    /** 评价时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评价时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date timeEvaluation;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setScores(Long scores) 
    {
        this.scores = scores;
    }

    public Long getScores() 
    {
        return scores;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setIsevaluation(Integer isevaluation) 
    {
        this.isevaluation = isevaluation;
    }

    public Integer getIsevaluation() 
    {
        return isevaluation;
    }

    public void setNameId(String nameId) 
    {
        this.nameId = nameId;
    }

    public String getNameId() 
    {
        return nameId;
    }

    public void setTeachingClass(String teachingClass) 
    {
        this.teachingClass = teachingClass;
    }

    public String getTeachingClass() 
    {
        return teachingClass;
    }

    public void setTimeEvaluation(Date timeEvaluation) 
    {
        this.timeEvaluation = timeEvaluation;
    }

    public Date getTimeEvaluation() 
    {
        return timeEvaluation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("content", getContent())
            .append("scores", getScores())
            .append("name", getName())
            .append("isevaluation", getIsevaluation())
            .append("nameId", getNameId())
            .append("teachingClass", getTeachingClass())
            .append("timeEvaluation", getTimeEvaluation())
            .toString();
    }
}
