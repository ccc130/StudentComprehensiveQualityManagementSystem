package com.ruoyi.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.student.domain.StudentCourse;
import com.ruoyi.student.service.IStudentCourseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课程信息Controller
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@RestController
@RequestMapping("/student/course")
public class StudentCourseController extends BaseController
{
    @Autowired
    private IStudentCourseService studentCourseService;

    /**
     * 查询课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentCourse studentCourse)
    {
        startPage();
        List<StudentCourse> list = studentCourseService.selectStudentCourseList(studentCourse);
        return getDataTable(list);
    }

    /**
     * 查询课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:course:list')")
    @GetMapping("/search")
    public TableDataInfo search(StudentCourse studentCourse)
    {
        startPage();
        List<StudentCourse> list = studentCourseService.searchStudentCourseList(studentCourse);
        return getDataTable(list);
    }

    /**
     * 导出课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:course:export')")
    @Log(title = "课程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentCourse studentCourse)
    {
        List<StudentCourse> list = studentCourseService.selectStudentCourseList(studentCourse);
        ExcelUtil<StudentCourse> util = new ExcelUtil<StudentCourse>(StudentCourse.class);
        util.exportExcel(response, list, "课程信息数据");
    }

    /**
     * 获取课程信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:course:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentCourseService.selectStudentCourseById(id));
    }

    /**
     * 新增课程信息
     */
    @PreAuthorize("@ss.hasPermi('student:course:add')")
    @Log(title = "课程信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentCourse studentCourse)
    {
        return toAjax(studentCourseService.insertStudentCourse(studentCourse));
    }

    /**
     * 修改课程信息
     */
    @PreAuthorize("@ss.hasPermi('student:course:edit')")
    @Log(title = "课程信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentCourse studentCourse)
    {
        return toAjax(studentCourseService.updateStudentCourse(studentCourse));
    }

    /**
     * 删除课程信息
     */
    @PreAuthorize("@ss.hasPermi('student:course:remove')")
    @Log(title = "课程信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studentCourseService.deleteStudentCourseByIds(ids));
    }
}
