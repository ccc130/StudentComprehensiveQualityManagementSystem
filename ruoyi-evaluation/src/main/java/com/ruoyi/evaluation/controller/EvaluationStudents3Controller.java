package com.ruoyi.evaluation.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.evaluation.domain.EvaluationStudents;
import com.ruoyi.evaluation.service.IEvaluationStudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 学生评价Controller
 *
 * @author 刘桐
 * @date 2025-07-01
 */
@RestController
@RequestMapping("/evaluation/students3")
public class EvaluationStudents3Controller extends BaseController
{
    @Autowired
    private IEvaluationStudentsService evaluationStudentsService;

    /**
     * 查询学生评价列表
     */
    @PreAuthorize("@ss.hasPermi('evaluation:students:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvaluationStudents evaluationStudents)
    {
        startPage();
        List<EvaluationStudents> list = evaluationStudentsService.selectEvaluationStudentsList(evaluationStudents);
        return getDataTable(list);
    }

    /**
     * 导出学生评价列表
     */
    @PreAuthorize("@ss.hasPermi('evaluation:students:export')")
    @Log(title = "学生评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EvaluationStudents evaluationStudents)
    {
        List<EvaluationStudents> list = evaluationStudentsService.selectEvaluationStudentsList(evaluationStudents);
        ExcelUtil<EvaluationStudents> util = new ExcelUtil<EvaluationStudents>(EvaluationStudents.class);
        util.exportExcel(response, list, "学生评价数据");
    }

    /**
     * 获取学生评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluation:students:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(evaluationStudentsService.selectEvaluationStudentsById(id));
    }

    /**
     * 新增学生评价
     */
    @PreAuthorize("@ss.hasPermi('evaluation:students:add')")
    @Log(title = "学生评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvaluationStudents evaluationStudents)
    {
        return toAjax(evaluationStudentsService.insertEvaluationStudents(evaluationStudents));
    }

    /**
     * 修改学生评价
     */
    @PreAuthorize("@ss.hasPermi('evaluation:students:edit')")
    @Log(title = "学生评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvaluationStudents evaluationStudents)
    {
        return toAjax(evaluationStudentsService.updateEvaluationStudents(evaluationStudents));
    }

    /**
     * 删除学生评价
     */
    @PreAuthorize("@ss.hasPermi('evaluation:students:remove')")
    @Log(title = "学生评价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(evaluationStudentsService.deleteEvaluationStudentsByIds(ids));
    }
}
