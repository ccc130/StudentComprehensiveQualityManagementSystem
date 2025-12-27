package com.ruoyi.student.mapper;

import java.util.List;

import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.domain.StudentInfo3;

/**
 * 学生信息Mapper接口
 * 
 * @author 焦阳
 * @date 2025-07-07
 */
public interface StudentInfo3Mapper 
{
    /**
     * 查询学生信息
     *
     * @param studentId 学生信息副键
     * @return 学生信息
     */
    public List<StudentInfo3> selectStudentInfoByStudentId(String studentId);
    /**
     * 查询学生信息
     *
     * @param studentInfo3 学生信息副键
     * @return 学生信息
     */
    public List<StudentInfo3> searchStudentInfoByStudentId(StudentInfo3 studentInfo3);
    /**
     * 查询学生信息
     * 
     * @param id 学生信息主键
     * @return 学生信息
     */
    public StudentInfo3 selectStudentInfo3ById(Long id);

    /**
     * 查询学生信息列表
     * 
     * @param studentInfo3 学生信息
     * @return 学生信息集合
     */
    public List<StudentInfo3> selectStudentInfo3List(StudentInfo3 studentInfo3);

    /**
     * 新增学生信息
     * 
     * @param studentInfo3 学生信息
     * @return 结果
     */
    public int insertStudentInfo3(StudentInfo3 studentInfo3);

    /**
     * 修改学生信息
     * 
     * @param studentInfo3 学生信息
     * @return 结果
     */
    public int updateStudentInfo3(StudentInfo3 studentInfo3);

    /**
     * 删除学生信息
     * 
     * @param id 学生信息主键
     * @return 结果
     */
    public int deleteStudentInfo3ById(Long id);

    /**
     * 批量删除学生信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentInfo3ByIds(Long[] ids);
}
