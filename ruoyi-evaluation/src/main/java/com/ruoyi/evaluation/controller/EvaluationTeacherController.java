package com.ruoyi.evaluation.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.evaluation.domain.EvaluationTeacher;
import com.ruoyi.evaluation.service.IEvaluationTeacherService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教师评价Controller
 * 
 * @author 刘桐
 * @date 2025-07-01
 */
@RestController
@RequestMapping("/evaluation/teacher")
public class EvaluationTeacherController extends BaseController
{
    @Autowired
    private IEvaluationTeacherService evaluationTeacherService;

    /**
     * 查询教师评价列表
     */
    @PreAuthorize("@ss.hasPermi('evaluation:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvaluationTeacher evaluationTeacher)
    {
        startPage();
        String createAccount = SecurityUtils.getUsername();
        evaluationTeacher.setNameId(createAccount);
        List<EvaluationTeacher> list = evaluationTeacherService.selectEvaluationTeacherList(evaluationTeacher);
        return getDataTable(list);
    }

    /**
     * 导出教师评价列表
     */
    @PreAuthorize("@ss.hasPermi('evaluation:teacher:export')")
    @Log(title = "教师评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EvaluationTeacher evaluationTeacher)
    {
        String createAccount = getUsername();
        evaluationTeacher.setNameId(createAccount);
        List<EvaluationTeacher> list = evaluationTeacherService.selectEvaluationTeacherList(evaluationTeacher);
        ExcelUtil<EvaluationTeacher> util = new ExcelUtil<EvaluationTeacher>(EvaluationTeacher.class);
        util.exportExcel(response, list, "教师评价数据");
    }

    /**
     * 获取教师评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluation:teacher:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(evaluationTeacherService.selectEvaluationTeacherById(id));
    }

    /**
     * 新增教师评价
     */
    @PreAuthorize("@ss.hasPermi('evaluation:teacher:add')")
    @Log(title = "教师评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvaluationTeacher evaluationTeacher)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        evaluationTeacher.setName(loginUser.getUser().getNickName());
        evaluationTeacher.setNameId(loginUser.getUsername());
        evaluationTeacher.setTimeEvaluation(DateUtils.getNowDate());

        return toAjax(evaluationTeacherService.insertEvaluationTeacher(evaluationTeacher));
    }

    /**
     * 修改教师评价
     */
    @PreAuthorize("@ss.hasPermi('evaluation:teacher:edit')")
    @Log(title = "教师评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvaluationTeacher evaluationTeacher)
    {
        return toAjax(evaluationTeacherService.updateEvaluationTeacher(evaluationTeacher));
    }

    /**
     * 删除教师评价
     */
    @PreAuthorize("@ss.hasPermi('evaluation:teacher:remove')")
    @Log(title = "教师评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(evaluationTeacherService.deleteEvaluationTeacherByIds(ids));
    }
}
