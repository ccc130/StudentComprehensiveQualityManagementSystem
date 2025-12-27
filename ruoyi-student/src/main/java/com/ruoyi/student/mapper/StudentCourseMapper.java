package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.StudentCourse;

/**
 * 课程信息Mapper接口
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
public interface StudentCourseMapper
{
    /**
     * 查询课程信息列表
     *
     * @param studentCourse 课程信息
     * @return 课程信息集合
     */
    public List<StudentCourse> searchStudentCourseList(StudentCourse studentCourse);
    /**
     * 查询课程信息
     *
     * @param courseCode 课程信息主键
     * @return 课程信息
     */
    public StudentCourse selectStudentCourseByCourseCode(String courseCode);
    /**
     * 查询课程信息
     * 
     * @param id 课程信息主键
     * @return 课程信息
     */
    public StudentCourse selectStudentCourseById(Long id);

    /**
     * 查询课程信息列表
     * 
     * @param studentCourse 课程信息
     * @return 课程信息集合
     */
    public List<StudentCourse> selectStudentCourseList(StudentCourse studentCourse);

    /**
     * 新增课程信息
     * 
     * @param studentCourse 课程信息
     * @return 结果
     */
    public int insertStudentCourse(StudentCourse studentCourse);

    /**
     * 修改课程信息
     * 
     * @param studentCourse 课程信息
     * @return 结果
     */
    public int updateStudentCourse(StudentCourse studentCourse);

    /**
     * 删除课程信息
     * 
     * @param id 课程信息主键
     * @return 结果
     */
    public int deleteStudentCourseById(Long id);

    /**
     * 批量删除课程信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentCourseByIds(Long[] ids);

}
