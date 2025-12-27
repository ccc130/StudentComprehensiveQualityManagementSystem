package com.ruoyi.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.student.domain.StudentInfo;
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
import com.ruoyi.student.domain.StudentInfo3;
import com.ruoyi.student.service.IStudentInfo3Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Controller
 * 
 * @author 焦阳
 * @date 2025-07-07
 */
@RestController
@RequestMapping("/student/info3")
public class StudentInfo3Controller extends BaseController
{
    @Autowired
    private IStudentInfo3Service studentInfo3Service;

    /**
     * 查询学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:info3:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentInfo3 studentInfo3)
    {
        startPage();
        List<StudentInfo3> list = studentInfo3Service.selectStudentInfo3List(studentInfo3);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('student:info3:export')")
    @GetMapping("/search")
    public TableDataInfo search(StudentInfo3 studentInfo3)
    {
        startPage();
        List<StudentInfo3> list = studentInfo3Service.searchStudentInfoByStudentId(studentInfo3);
        return getDataTable(list);
    }

    /**
     * 导出学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:info3:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentInfo3 studentInfo3)
    {
        List<StudentInfo3> list = studentInfo3Service.selectStudentInfo3List(studentInfo3);
        ExcelUtil<StudentInfo3> util = new ExcelUtil<StudentInfo3>(StudentInfo3.class);
        util.exportExcel(response, list, "学生信息数据");
    }

    /**
     * 获取学生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:info3:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentInfo3Service.selectStudentInfo3ById(id));
    }

    /**
     * 新增学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info3:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentInfo3 studentInfo3)
    {
        return toAjax(studentInfo3Service.insertStudentInfo3(studentInfo3));
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info3:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentInfo3 studentInfo3)
    {
        return toAjax(studentInfo3Service.updateStudentInfo3(studentInfo3));
    }

    /**
     * 删除学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info3:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studentInfo3Service.deleteStudentInfo3ByIds(ids));
    }
}
