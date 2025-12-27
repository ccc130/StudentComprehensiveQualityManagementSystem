package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.domain.StudentInfo3;
import com.ruoyi.student.domain.StudentScores;

/**
 * 学生信息Mapper接口
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
public interface StudentInfoMapper 
{

    /**
     * 查询学生信息
     *
     * @param studentId 学生信息副键
     * @return 学生信息
     */
    public List<StudentInfo> selectStudentInfoByStudentId(String studentId);
    /**
     * 查询学生信息
     * 
     * @param id 学生信息主键
     * @return 学生信息
     */
    public StudentInfo selectStudentInfoById(Long id);

    /**
     * 查询学生信息列表
     * 
     * @param studentInfo 学生信息
     * @return 学生信息集合
     */
    public List<StudentInfo> selectStudentInfoList(StudentInfo studentInfo);

    /**
     * 新增学生信息
     * 
     * @param studentInfo 学生信息
     * @return 结果
     */
    public int insertStudentInfo(StudentInfo studentInfo);

    /**
     * 修改学生信息
     * 
     * @param studentInfo 学生信息
     * @return 结果
     */
    public int updateStudentInfo(StudentInfo studentInfo);

    /**
     * 删除学生信息
     * 
     * @param id 学生信息主键
     * @return 结果
     */
    public int deleteStudentInfoById(Long id);

    /**
     * 批量删除学生信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentInfoByIds(Long[] ids);
}
