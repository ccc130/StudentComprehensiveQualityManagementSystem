package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.StudentScores;

/**
 * 学生成绩Mapper接口
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
public interface StudentScoresMapper 
{
    /**
     * 查询学生成绩
     *
     * @param studentScores 学生成绩副键
     * @return 学生成绩和
     */
    public Double sumGradePointByStudentId(StudentScores studentScores);
    /**
     * 查询学生成绩
     * 
     * @param courseId 学生成绩副键
     * @return 学生成绩
     */
    public StudentScores selectStudentScoresByCourseId(Long courseId);

    /**
     * 查询学生成绩列表
     * 
     * @param studentScores 学生成绩
     * @return 学生成绩集合
     */
    public List<StudentScores> selectStudentScoresList(StudentScores studentScores);

    /**
     * 新增学生成绩
     * 
     * @param studentScores 学生成绩
     * @return 结果
     */
    public int insertStudentScores(StudentScores studentScores);

    /**
     * 修改学生成绩
     * 
     * @param studentScores 学生成绩
     * @return 结果
     */
    public int updateStudentScores(StudentScores studentScores);

    /**
     * 删除学生成绩
     * 
     * @param courseId 学生成绩主键
     * @return 结果
     */
    public int deleteStudentScoresByCourseId(Long courseId);

    /**
     * 批量删除学生成绩
     * 
     * @param courseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentScoresByCourseIds(Long[] courseIds);
}
