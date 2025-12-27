package com.ruoyi.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.student.domain.StudentScores;
import com.ruoyi.student.service.IStudentInfo3Service;
import com.ruoyi.student.service.IStudentScoresService;
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
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.service.IStudentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Controller
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@RestController
@RequestMapping("/student/info")
public class StudentInfoController extends BaseController
{
    @Autowired
    private IStudentInfoService studentInfoService;
    @Autowired
    private IStudentInfo3Service studentInfo3Service;
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
        List<StudentInfo> list = studentInfoService.selectStudentInfoList(studentInfo);
        for (StudentInfo student : list) {
            StudentScores studentScores = new StudentScores();
            studentScores.setStudentId(student.getStudentId());
            studentScores.setSchoolYear(student.getSchoolYear());
            studentScores.setSemester(student.getSemester());
            Double totalGradePoint = studentScoresService.sumGradePointByStudentId(studentScores);
            student.setTotalScores(totalGradePoint != null ? totalGradePoint : 0.0);
            studentInfoService.updateStudentInfo(student);
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
