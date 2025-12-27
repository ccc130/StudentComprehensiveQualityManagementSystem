package com.ruoyi.student.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.student.mapper.StudentCourseMapper;
import com.ruoyi.student.domain.StudentCourse;
import com.ruoyi.student.service.IStudentCourseService;

/**
 * 课程信息Service业务层处理
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@Service
public class StudentCourseServiceImpl implements IStudentCourseService 
{
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    /**
     * 查询课程信息列表
     *
     * @param studentCourse 课程信息
     * @return 课程信息集合
     */
    @Override
    public List<StudentCourse> searchStudentCourseList(StudentCourse studentCourse){
        return studentCourseMapper.searchStudentCourseList(studentCourse);
    }
    /**
     * 查询课程信息
     *
     * @param courseCode 课程信息主键
     * @return 课程信息
     */
    @Override
    public StudentCourse selectStudentCourseByCourseCode(String courseCode) {
        return studentCourseMapper.selectStudentCourseByCourseCode(courseCode);
    }

    /**
     * 查询课程信息
     * 
     * @param id 课程信息主键
     * @return 课程信息
     */
    @Override
    public StudentCourse selectStudentCourseById(Long id)
    {
        return studentCourseMapper.selectStudentCourseById(id);
    }

    /**
     * 查询课程信息列表
     * 
     * @param studentCourse 课程信息
     * @return 课程信息
     */
    @Override
    public List<StudentCourse> selectStudentCourseList(StudentCourse studentCourse)
    {
        return studentCourseMapper.selectStudentCourseList(studentCourse);
    }

    /**
     * 新增课程信息
     * 
     * @param studentCourse 课程信息
     * @return 结果
     */
    @Override
    public int insertStudentCourse(StudentCourse studentCourse)
    {
        return studentCourseMapper.insertStudentCourse(studentCourse);
    }

    /**
     * 修改课程信息
     * 
     * @param studentCourse 课程信息
     * @return 结果
     */
    @Override
    public int updateStudentCourse(StudentCourse studentCourse)
    {
        return studentCourseMapper.updateStudentCourse(studentCourse);
    }

    /**
     * 批量删除课程信息
     * 
     * @param ids 需要删除的课程信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentCourseByIds(Long[] ids)
    {
        return studentCourseMapper.deleteStudentCourseByIds(ids);
    }

    /**
     * 删除课程信息信息
     * 
     * @param id 课程信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentCourseById(Long id)
    {
        return studentCourseMapper.deleteStudentCourseById(id);
    }
}
