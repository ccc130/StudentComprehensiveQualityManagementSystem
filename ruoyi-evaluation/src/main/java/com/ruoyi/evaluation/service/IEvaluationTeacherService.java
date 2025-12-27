package com.ruoyi.evaluation.service;

import java.util.List;
import com.ruoyi.evaluation.domain.EvaluationTeacher;

/**
 * 教师评价Service接口
 * 
 * @author 刘桐
 * @date 2025-07-01
 */
public interface IEvaluationTeacherService 
{
    public List<EvaluationTeacher> selectEvaluationTeacherByIsevaluation(int value);
    /**
     * 查询教师评价
     * 
     * @param id 教师评价主键
     * @return 教师评价
     */
    public EvaluationTeacher selectEvaluationTeacherById(Long id);

    /**
     * 查询教师评价列表
     * 
     * @param evaluationTeacher 教师评价
     * @return 教师评价集合
     */
    public List<EvaluationTeacher> selectEvaluationTeacherList(EvaluationTeacher evaluationTeacher);

    /**
     * 新增教师评价
     * 
     * @param evaluationTeacher 教师评价
     * @return 结果
     */
    public int insertEvaluationTeacher(EvaluationTeacher evaluationTeacher);

    /**
     * 修改教师评价
     * 
     * @param evaluationTeacher 教师评价
     * @return 结果
     */
    public int updateEvaluationTeacher(EvaluationTeacher evaluationTeacher);

    /**
     * 批量删除教师评价
     * 
     * @param ids 需要删除的教师评价主键集合
     * @return 结果
     */
    public int deleteEvaluationTeacherByIds(Long[] ids);

    /**
     * 删除教师评价信息
     * 
     * @param id 教师评价主键
     * @return 结果
     */
    public int deleteEvaluationTeacherById(Long id);
}
