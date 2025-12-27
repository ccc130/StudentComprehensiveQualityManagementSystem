package com.ruoyi.student.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.domain.StudentScores;
import com.ruoyi.student.service.IStudentInfoService;
import com.ruoyi.student.service.IStudentScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 学生信息Controller
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@RestController
@RequestMapping("/student/info2")
public class StudentInfo2Controller extends BaseController
{
    @Autowired
    private IStudentInfoService studentInfoService;
    @Autowired
    private IStudentScoresService studentScoresService;

    /**
     * 查询学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentInfo studentInfo)
    {
        startPage();
        String createAccount = SecurityUtils.getUsername();
        studentInfo.setStudentId(createAccount);
        List<StudentInfo> list = studentInfoService.selectStudentInfoList(studentInfo);
        for (StudentInfo student : list) {
            StudentScores studentScores = new StudentScores();
            studentScores.setStudentId(student.getStudentId());
            studentScores.setSchoolYear(student.getSchoolYear());
            studentScores.setSemester(student.getSemester());
            Double totalGradePoint = studentScoresService.sumGradePointByStudentId(studentScores);
            student.setTotalScores(totalGradePoint != null ? totalGradePoint : 0.0);
        }
        return getDataTable(list);
    }

    /**
     * 导出学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('student:info:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentInfo studentInfo)
    {
        String createAccount = SecurityUtils.getUsername();
        studentInfo.setStudentId(createAccount);
        List<StudentInfo> list = studentInfoService.selectStudentInfoList(studentInfo);
        ExcelUtil<StudentInfo> util = new ExcelUtil<StudentInfo>(StudentInfo.class);
        util.exportExcel(response, list, "学生信息数据");
    }

    /**
     * 获取学生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentInfoService.selectStudentInfoById(id));
    }

    /**
     * 新增学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentInfo studentInfo)
    {
        return toAjax(studentInfoService.insertStudentInfo(studentInfo));
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentInfo studentInfo)
    {
        return toAjax(studentInfoService.updateStudentInfo(studentInfo));
    }

    /**
     * 删除学生信息
     */
    @PreAuthorize("@ss.hasPermi('student:info:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studentInfoService.deleteStudentInfoByIds(ids));
    }
}
