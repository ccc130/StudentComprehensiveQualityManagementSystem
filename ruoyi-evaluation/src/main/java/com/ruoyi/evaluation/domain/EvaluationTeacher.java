package com.ruoyi.evaluation.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师评价对象 evaluation_teacher
 * 
 * @author 刘桐
 * @date 2025-07-01
 */
public class EvaluationTeacher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 评价对象 */
    @Excel(name = "评价对象")
    private String target;

    /** 评价对象id */
    @Excel(name = "评价对象id")
    private String targetId;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 评分 */
    @Excel(name = "评分")
    private Long scores;

    /** 评价人 */
    @Excel(name = "评价人")
    private String name;

    /** 评价人id */
    @Excel(name = "评价人id")
    private String nameId;

    /** 评价标记 */
    @Excel(name = "评价标记")
    private Long isevaluation;

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

    public void setTarget(String target) 
    {
        this.target = target;
    }

    public String getTarget() 
    {
        return target;
    }

    public void setTargetId(String targetId) 
    {
        this.targetId = targetId;
    }

    public String getTargetId() 
    {
        return targetId;
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

    public void setNameId(String nameId) 
    {
        this.nameId = nameId;
    }

    public String getNameId() 
    {
        return nameId;
    }

    public void setIsevaluation(Long isevaluation) 
    {
        this.isevaluation = isevaluation;
    }

    public Long getIsevaluation() 
    {
        return isevaluation;
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
            .append("target", getTarget())
            .append("targetId", getTargetId())
            .append("content", getContent())
            .append("scores", getScores())
            .append("name", getName())
            .append("nameId", getNameId())
            .append("isevaluation", getIsevaluation())
            .append("timeEvaluation", getTimeEvaluation())
            .toString();
    }
}
