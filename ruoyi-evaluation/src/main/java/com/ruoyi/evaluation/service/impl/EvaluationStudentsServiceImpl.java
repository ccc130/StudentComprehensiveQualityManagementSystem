package com.ruoyi.evaluation.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.evaluation.mapper.EvaluationStudentsMapper;
import com.ruoyi.evaluation.domain.EvaluationStudents;
import com.ruoyi.evaluation.service.IEvaluationStudentsService;

/**
 * 学生评价Service业务层处理
 * 
 * @author 刘桐
 * @date 2025-07-01
 */
@Service
public class EvaluationStudentsServiceImpl implements IEvaluationStudentsService 
{
    @Autowired
    private EvaluationStudentsMapper evaluationStudentsMapper;

    /**
     * 查询学生评价
     * 
     * @param id 学生评价主键
     * @return 学生评价
     */
    @Override
    public EvaluationStudents selectEvaluationStudentsById(Long id)
    {
        return evaluationStudentsMapper.selectEvaluationStudentsById(id);
    }

    /**
     * 查询学生评价列表
     * 
     * @param evaluationStudents 学生评价
     * @return 学生评价
     */
    @Override
    public List<EvaluationStudents> selectEvaluationStudentsList(EvaluationStudents evaluationStudents)
    {
        return evaluationStudentsMapper.selectEvaluationStudentsList(evaluationStudents);
    }

    /**
     * 新增学生评价
     * 
     * @param evaluationStudents 学生评价
     * @return 结果
     */
    @Override
    public int insertEvaluationStudents(EvaluationStudents evaluationStudents)
    {
        return evaluationStudentsMapper.insertEvaluationStudents(evaluationStudents);
    }

    /**
     * 修改学生评价
     * 
     * @param evaluationStudents 学生评价
     * @return 结果
     */
    @Override
    public int updateEvaluationStudents(EvaluationStudents evaluationStudents)
    {
        return evaluationStudentsMapper.updateEvaluationStudents(evaluationStudents);
    }

    /**
     * 批量删除学生评价
     * 
     * @param ids 需要删除的学生评价主键
     * @return 结果
     */
    @Override
    public int deleteEvaluationStudentsByIds(Long[] ids)
    {
        return evaluationStudentsMapper.deleteEvaluationStudentsByIds(ids);
    }

    /**
     * 删除学生评价信息
     * 
     * @param id 学生评价主键
     * @return 结果
     */
    @Override
    public int deleteEvaluationStudentsById(Long id)
    {
        return evaluationStudentsMapper.deleteEvaluationStudentsById(id);
    }
}
