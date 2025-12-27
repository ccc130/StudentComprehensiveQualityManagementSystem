package com.ruoyi.student.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.student.domain.StudentCourse;
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.domain.StudentScores;
import com.ruoyi.student.service.IStudentCourseService;
import com.ruoyi.student.service.IStudentInfoService;
import com.ruoyi.student.service.IStudentScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 学生成绩Controller
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@RestController
@RequestMapping("/student/scores2")
public class StudentScores2Controller extends BaseController
{
    @Autowired
    private IStudentScoresService studentScoresService;
    @Autowired
    private IStudentCourseService studentCourseService;
    @Autowired
    private IStudentInfoService studentInfoService;

    /**
     * 查询学生成绩列表
     */
    @PreAuthorize("@ss.hasPermi('student:scores:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentScores studentScores)
    {
        startPage();
        String createAccount = SecurityUtils.getUsername();
        studentScores.setStudentId(createAccount);
        List<StudentScores> list = studentScoresService.selectStudentScoresList(studentScores);
        return getDataTable(list);
    }

    /**
     * 导出学生成绩列表
     */
    @PreAuthorize("@ss.hasPermi('student:scores:export')")
    @Log(title = "学生成绩", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentScores studentScores)
    {
        String createAccount = SecurityUtils.getUsername();
        studentScores.setStudentId(createAccount);
        List<StudentScores> list = studentScoresService.selectStudentScoresList(studentScores);
        ExcelUtil<StudentScores> util = new ExcelUtil<StudentScores>(StudentScores.class);
        util.exportExcel(response, list, "学生成绩数据");
    }

    /**
     * 获取学生成绩详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:scores:query')")
    @GetMapping(value = "/{courseId}")
    public AjaxResult getInfo(@PathVariable("courseId") Long courseId)
    {
        return success(studentScoresService.selectStudentScoresByCourseId(courseId));
    }

    /**
     * 新增学生成绩
     */
    @PreAuthorize("@ss.hasPermi('student:scores:add')")
    @Log(title = "学生成绩", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentScores studentScores)
    {
        StudentCourse studentCourse = studentCourseService.selectStudentCourseByCourseCode(studentScores.getCourseCode());
        studentScores.setCourseName(studentCourse.getCourseName());
        studentScores.setCurriculum(studentCourse.getCurriculum());
        studentScores.setCredit(studentCourse.getCredit());
        studentScores.setGpa(studentCourse.getGpa());
        studentScores.setCollegeOffering(studentCourse.getCollegeOffering());
        studentScores.setCourseMarking(studentCourse.getCourseMarking());
        studentScores.setCourseCategory(studentCourse.getCourseCategory());
        studentScores.setCourseOwnership(studentCourse.getCourseOwnership());
        studentScores.setTeachingMethod(studentCourse.getTeachingMethod());

        List<StudentInfo> studentInfo = studentInfoService.selectStudentInfoByStudentId(studentScores.getStudentId());
        studentScores.setStudentName(studentInfo.get(0).getName());
        studentScores.setTeachingClass(studentInfo.get(0).getTeachingClass());
        studentScores.setGradePoint(studentScores.getScores()/100*studentCourse.getCredit()*studentCourse.getGpa());

        return toAjax(studentScoresService.insertStudentScores(studentScores));
    }

    /**
     * 修改学生成绩
     */
    @PreAuthorize("@ss.hasPermi('student:scores:edit')")
    @Log(title = "学生成绩", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentScores studentScores)
    {
        return toAjax(studentScoresService.updateStudentScores(studentScores));
    }

    /**
     * 删除学生成绩
     */
    @PreAuthorize("@ss.hasPermi('student:scores:remove')")
    @Log(title = "学生成绩", businessType = BusinessType.DELETE)
	@DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds)
    {
        return toAjax(studentScoresService.deleteStudentScoresByCourseIds(courseIds));
    }
}
