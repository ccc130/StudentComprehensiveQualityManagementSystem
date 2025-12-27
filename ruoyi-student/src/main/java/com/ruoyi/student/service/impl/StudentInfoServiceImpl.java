package com.ruoyi.student.service.impl;

import java.util.List;

import com.ruoyi.student.domain.StudentInfo3;
import com.ruoyi.student.domain.StudentScores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.StudentInfoMapper;
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.service.IStudentInfoService;

/**
 * 学生信息Service业务层处理
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@Service
public class StudentInfoServiceImpl implements IStudentInfoService 
{
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    /**
     * 查询学生信息
     *
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    public List<StudentInfo> selectStudentInfoByStudentId(String studentId){
        return studentInfoMapper.selectStudentInfoByStudentId(studentId);
    }
    /**
     * 查询学生信息
     * 
     * @param id 学生信息主键
     * @return 学生信息
     */
    @Override
    public StudentInfo selectStudentInfoById(Long id)
    {
        return studentInfoMapper.selectStudentInfoById(id);
    }

    /**
     * 查询学生信息列表
     * 
     * @param studentInfo 学生信息
     * @return 学生信息
     */
    @Override
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo)
    {
        return studentInfoMapper.selectStudentInfoList(studentInfo);
    }

    /**
     * 新增学生信息
     * 
     * @param studentInfo 学生信息
     * @return 结果
     */
    @Override
    public int insertStudentInfo(StudentInfo studentInfo)
    {
        return studentInfoMapper.insertStudentInfo(studentInfo);
    }

    /**
     * 修改学生信息
     * 
     * @param studentInfo 学生信息
     * @return 结果
     */
    @Override
    public int updateStudentInfo(StudentInfo studentInfo)
    {
        return studentInfoMapper.updateStudentInfo(studentInfo);
    }

    /**
     * 批量删除学生信息
     * 
     * @param ids 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentInfoByIds(Long[] ids)
    {
        return studentInfoMapper.deleteStudentInfoByIds(ids);
    }

    /**
     * 删除学生信息信息
     * 
     * @param id 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentInfoById(Long id)
    {
        return studentInfoMapper.deleteStudentInfoById(id);
    }
}
