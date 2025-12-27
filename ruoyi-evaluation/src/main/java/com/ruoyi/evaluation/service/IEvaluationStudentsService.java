package com.ruoyi.evaluation.service;

import java.util.List;
import com.ruoyi.evaluation.domain.EvaluationStudents;

/**
 * 学生评价Service接口
 * 
 * @author 刘桐
 * @date 2025-07-01
 */
public interface IEvaluationStudentsService 
{
    /**
     * 查询学生评价
     * 
     * @param id 学生评价主键
     * @return 学生评价
     */
    public EvaluationStudents selectEvaluationStudentsById(Long id);

    /**
     * 查询学生评价列表
     * 
     * @param evaluationStudents 学生评价
     * @return 学生评价集合
     */
    public List<EvaluationStudents> selectEvaluationStudentsList(EvaluationStudents evaluationStudents);

    /**
     * 新增学生评价
     * 
     * @param evaluationStudents 学生评价
     * @return 结果
     */
    public int insertEvaluationStudents(EvaluationStudents evaluationStudents);

    /**
     * 修改学生评价
     * 
     * @param evaluationStudents 学生评价
     * @return 结果
     */
    public int updateEvaluationStudents(EvaluationStudents evaluationStudents);

    /**
     * 批量删除学生评价
     * 
     * @param ids 需要删除的学生评价主键集合
     * @return 结果
     */
    public int deleteEvaluationStudentsByIds(Long[] ids);

    /**
     * 删除学生评价信息
     * 
     * @param id 学生评价主键
     * @return 结果
     */
    public int deleteEvaluationStudentsById(Long id);
}
