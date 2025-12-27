package com.ruoyi.evaluation.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.evaluation.mapper.EvaluationTeacherMapper;
import com.ruoyi.evaluation.domain.EvaluationTeacher;
import com.ruoyi.evaluation.service.IEvaluationTeacherService;

/**
 * 教师评价Service业务层处理
 * 
 * @author 刘桐
 * @date 2025-07-01
 */
@Service
public class EvaluationTeacherServiceImpl implements IEvaluationTeacherService 
{
    @Autowired
    private EvaluationTeacherMapper evaluationTeacherMapper;

    @Override
    public List<EvaluationTeacher> selectEvaluationTeacherByIsevaluation(int value){
        return evaluationTeacherMapper.selectEvaluationTeacherByIsevaluation(value);
    }

    /**
     * 查询教师评价
     * 
     * @param id 教师评价主键
     * @return 教师评价
     */
    @Override
    public EvaluationTeacher selectEvaluationTeacherById(Long id)
    {
        return evaluationTeacherMapper.selectEvaluationTeacherById(id);
    }

    /**
     * 查询教师评价列表
     * 
     * @param evaluationTeacher 教师评价
     * @return 教师评价
     */
    @Override
    public List<EvaluationTeacher> selectEvaluationTeacherList(EvaluationTeacher evaluationTeacher)
    {
        return evaluationTeacherMapper.selectEvaluationTeacherList(evaluationTeacher);
    }

    /**
     * 新增教师评价
     * 
     * @param evaluationTeacher 教师评价
     * @return 结果
     */
    @Override
    public int insertEvaluationTeacher(EvaluationTeacher evaluationTeacher)
    {
        return evaluationTeacherMapper.insertEvaluationTeacher(evaluationTeacher);
    }

    /**
     * 修改教师评价
     * 
     * @param evaluationTeacher 教师评价
     * @return 结果
     */
    @Override
    public int updateEvaluationTeacher(EvaluationTeacher evaluationTeacher)
    {
        return evaluationTeacherMapper.updateEvaluationTeacher(evaluationTeacher);
    }

    /**
     * 批量删除教师评价
     * 
     * @param ids 需要删除的教师评价主键
     * @return 结果
     */
    @Override
    public int deleteEvaluationTeacherByIds(Long[] ids)
    {
        return evaluationTeacherMapper.deleteEvaluationTeacherByIds(ids);
    }

    /**
     * 删除教师评价信息
     * 
     * @param id 教师评价主键
     * @return 结果
     */
    @Override
    public int deleteEvaluationTeacherById(Long id)
    {
        return evaluationTeacherMapper.deleteEvaluationTeacherById(id);
    }
}
