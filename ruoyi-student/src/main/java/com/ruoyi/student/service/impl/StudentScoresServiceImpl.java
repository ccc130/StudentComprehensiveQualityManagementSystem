package com.ruoyi.student.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.StudentScoresMapper;
import com.ruoyi.student.domain.StudentScores;
import com.ruoyi.student.service.IStudentScoresService;

import javax.validation.Validator;

/**
 * 学生成绩Service业务层处理
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@Service
public class StudentScoresServiceImpl implements IStudentScoresService 
{
    @Autowired
    private StudentScoresMapper studentScoresMapper;
    @Autowired
    protected Validator validator;

    /**
     * 查询学生成绩
     *
     * @param studentScores 学生成绩副键
     * @return 学生成绩和
     */
    @Override
    public Double sumGradePointByStudentId(StudentScores studentScores){
        return studentScoresMapper.sumGradePointByStudentId(studentScores);
    }
    /**
     * 查询学生成绩
     * 
     * @param courseId 学生成绩主键
     * @return 学生成绩
     */
    @Override
    public StudentScores selectStudentScoresByCourseId(Long courseId)
    {
        return studentScoresMapper.selectStudentScoresByCourseId(courseId);
    }

    /**
     * 查询学生成绩列表
     * 
     * @param studentScores 学生成绩
     * @return 学生成绩
     */
    @Override
    public List<StudentScores> selectStudentScoresList(StudentScores studentScores)
    {
        return studentScoresMapper.selectStudentScoresList(studentScores);
    }

    /**
     * 新增学生成绩
     * 
     * @param studentScores 学生成绩
     * @return 结果
     */
    @Override
    public int insertStudentScores(StudentScores studentScores)
    {
        return studentScoresMapper.insertStudentScores(studentScores);
    }

    /**
     * 修改学生成绩
     * 
     * @param studentScores 学生成绩
     * @return 结果
     */
    @Override
    public int updateStudentScores(StudentScores studentScores)
    {
        return studentScoresMapper.updateStudentScores(studentScores);
    }

    /**
     * 批量删除学生成绩
     * 
     * @param courseIds 需要删除的学生成绩主键
     * @return 结果
     */
    @Override
    public int deleteStudentScoresByCourseIds(Long[] courseIds)
    {
        return studentScoresMapper.deleteStudentScoresByCourseIds(courseIds);
    }

    /**
     * 删除学生成绩信息
     * 
     * @param courseId 学生成绩主键
     * @return 结果
     */
    @Override
    public int deleteStudentScoresByCourseId(Long courseId)
    {
        return studentScoresMapper.deleteStudentScoresByCourseId(courseId);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<StudentScores> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StudentScores user : userList)
        {
            try
            {
                BeanValidators.validateWithException(validator, user);
                user.setCreateBy(operName);
                this.insertStudentScores(user);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + user.getStudentName() + " 导入成功");

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getStudentName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                //log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
