package com.ruoyi.student.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.StudentInfo3Mapper;
import com.ruoyi.student.domain.StudentInfo3;
import com.ruoyi.student.service.IStudentInfo3Service;

/**
 * 学生信息Service业务层处理
 * 
 * @author 焦阳
 * @date 2025-07-07
 */
@Service
public class StudentInfo3ServiceImpl implements IStudentInfo3Service 
{
    @Autowired
    private StudentInfo3Mapper studentInfo3Mapper;

    /**
     * 查询学生成绩
     *
     * @param studentInfo3 学号
     * @return 模糊学生成绩
     */
    @Override
    public List<StudentInfo3> searchStudentInfoByStudentId(StudentInfo3 studentInfo3){
        return studentInfo3Mapper.searchStudentInfoByStudentId(studentInfo3);
    }
    /**
     * 查询学生信息
     *
     * @param studentId 学生信息主键
     * @return 学生信息
     */
    public List<StudentInfo3> selectStudentInfoByStudentId(String studentId){
        return studentInfo3Mapper.selectStudentInfoByStudentId(studentId);
    }
    /**
     * 查询学生信息
     * 
     * @param id 学生信息主键
     * @return 学生信息
     */
    @Override
    public StudentInfo3 selectStudentInfo3ById(Long id)
    {
        return studentInfo3Mapper.selectStudentInfo3ById(id);
    }

    /**
     * 查询学生信息列表
     * 
     * @param studentInfo3 学生信息
     * @return 学生信息
     */
    @Override
    public List<StudentInfo3> selectStudentInfo3List(StudentInfo3 studentInfo3)
    {
        return studentInfo3Mapper.selectStudentInfo3List(studentInfo3);
    }

    /**
     * 新增学生信息
     * 
     * @param studentInfo3 学生信息
     * @return 结果
     */
    @Override
    public int insertStudentInfo3(StudentInfo3 studentInfo3)
    {
        return studentInfo3Mapper.insertStudentInfo3(studentInfo3);
    }

    /**
     * 修改学生信息
     * 
     * @param studentInfo3 学生信息
     * @return 结果
     */
    @Override
    public int updateStudentInfo3(StudentInfo3 studentInfo3)
    {
        return studentInfo3Mapper.updateStudentInfo3(studentInfo3);
    }

    /**
     * 批量删除学生信息
     * 
     * @param ids 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentInfo3ByIds(Long[] ids)
    {
        return studentInfo3Mapper.deleteStudentInfo3ByIds(ids);
    }

    /**
     * 删除学生信息信息
     * 
     * @param id 学生信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentInfo3ById(Long id)
    {
        return studentInfo3Mapper.deleteStudentInfo3ById(id);
    }
}
