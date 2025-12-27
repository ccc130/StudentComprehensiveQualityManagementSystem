package com.ruoyi.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.student.domain.StudentCourse;
import com.ruoyi.student.domain.StudentInfo;
import com.ruoyi.student.domain.StudentInfo3;
import com.ruoyi.student.service.IStudentCourseService;
import com.ruoyi.student.service.IStudentInfo3Service;
import com.ruoyi.student.service.IStudentInfoService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Configurable;
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
import com.ruoyi.student.domain.StudentScores;
import com.ruoyi.student.service.IStudentScoresService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生成绩Controller
 * 
 * @author 焦阳
 * @date 2025-06-30
 */
@RestController
@RequestMapping("/student/scores")
public class StudentScoresController extends BaseController
{
    @Autowired
    private IStudentScoresService studentScoresService;
    @Autowired
    private IStudentCourseService studentCourseService;
    @Autowired
    private IStudentInfoService studentInfoService;
    @Autowired
    private IStudentInfo3Service studentInfo3Service;


    /**
     * 查询学生成绩列表
     */
    @PreAuthorize("@ss.hasPermi('student:scores:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentScores studentScores)
    {
        startPage();
        List<StudentScores> list = studentScoresService.selectStudentScoresList(studentScores);

        Map<String, StudentInfo> infoMap = new HashMap<>();
        for (StudentScores item : list){
            List<StudentInfo> listStudentInfo = studentInfoService.selectStudentInfoByStudentId(item.getStudentId());
            for (StudentInfo info : listStudentInfo) {
                String key = info.getStudentId() + "_" + info.getSchoolYear() + "_" + info.getSemester();
                System.out.println("1:"+key);
                infoMap.put(key, info);
            }
        }

        Map<String, Double> scoreCache = new HashMap<>();

        for (StudentScores item : list) {
            String studentId = item.getStudentId();
            if (!scoreCache.containsKey(studentId)) {
                Double totalScore = studentScoresService.sumGradePointByStudentId(item);
                scoreCache.put(studentId, totalScore != null ? totalScore : 0.0);
            }

            Double totalScore = scoreCache.get(studentId);
            String key = item.getStudentId() + "_" + item.getSchoolYear() + "_" + item.getSemester();
            System.out.println("2:"+key);
            if (infoMap.containsKey(key)) {
                StudentInfo studentInfo = infoMap.get(key);
                if (!Objects.equals(studentInfo.getTotalScores(), totalScore)) {
                    studentInfo.setTotalScores(totalScore);
                    studentInfoService.updateStudentInfo(studentInfo);
                }
            } else {
                StudentInfo student = new StudentInfo();
                student.setStudentId(studentId);
                student.setName(item.getStudentName());
                student.setTeachingClass(item.getTeachingClass());
                student.setSchoolYear(item.getSchoolYear());
                student.setSemester(item.getSemester());
                student.setTotalScores(totalScore);
                studentInfoService.insertStudentInfo(student);
            }
        }
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

        List<StudentInfo3> studentInfo3 = studentInfo3Service.selectStudentInfoByStudentId(studentScores.getStudentId());
        studentScores.setStudentName(studentInfo3.get(0).getName());
        studentScores.setTeachingClass(studentInfo3.get(0).getTeachingClass());
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

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<StudentScores> util = new ExcelUtil<StudentScores>(StudentScores.class);
        List<StudentScores> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = getLoginUser();
        String operName = loginUser.getUsername();
        String message = studentScoresService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<StudentScores> util = new ExcelUtil<StudentScores>(StudentScores.class);
        util.importTemplateExcel(response, "用户数据");
    }

}
