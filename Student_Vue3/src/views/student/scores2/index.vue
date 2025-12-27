<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学年" prop="schoolYear">
        <el-select v-model="queryParams.schoolYear" placeholder="请选择学年" clearable>
          <el-option
            v-for="dict in school_year"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学期" prop="semester">
        <el-select v-model="queryParams.semester" placeholder="请选择学期" clearable>
          <el-option
            v-for="dict in semester"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程代码" prop="courseCode">
          <el-select
            v-model="form.courseCode"
            placeholder="请输入课程代码"
            filterable
            remote
            :remote-method="remoteCourseMethod"
            :loading="loadingCourse"
            clearable
            @focus="getCourseOptions"
          >
            <el-option
              v-for="item in courseOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程性质" prop="curriculum">
        <el-select v-model="queryParams.curriculum" placeholder="请选择课程性质" clearable>
          <el-option
            v-for="dict in course_nature"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="成绩性质" prop="gradesNature">
        <el-select v-model="queryParams.gradesNature" placeholder="请选择成绩性质" clearable>
          <el-option
            v-for="dict in nature_of_grades"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否学位课程" prop="degreeCourse">
        <el-select v-model="queryParams.degreeCourse" placeholder="请选择是否学位课程" clearable>
          <el-option
            v-for="dict in yes_or_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开课学院" prop="collegeOffering">
        <el-input
          v-model="queryParams.collegeOffering"
          placeholder="请输入开课学院"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程标记" prop="courseMarking">
        <el-select v-model="queryParams.courseMarking" placeholder="请选择课程标记" clearable>
          <el-option
            v-for="dict in student_marking_main"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程类别" prop="courseCategory">
        <el-select v-model="queryParams.courseCategory" placeholder="请选择课程类别" clearable>
          <el-option
            v-for="dict in course_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程归属" prop="courseOwnership">
        <el-input
          v-model="queryParams.courseOwnership"
          placeholder="请输入课程归属"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教学班" prop="teachingClass">
        <el-input
          v-model="queryParams.teachingClass"
          placeholder="请输入教学班"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任课教师" prop="courseTeacher">
        <el-input
          v-model="queryParams.courseTeacher"
          placeholder="请输入任课教师"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核方式" prop="teachingMethod">
        <el-select v-model="queryParams.teachingMethod" placeholder="请选择考核方式" clearable>
          <el-option
            v-for="dict in assessment_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否成绩作废" prop="scoresMarking">
        <el-select v-model="queryParams.scoresMarking" placeholder="请选择是否成绩作废" clearable>
          <el-option
            v-for="dict in yes_or_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['student:scores:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['student:scores:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['student:scores:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['student:scores:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scoresList" @selection-change="handleSelectionChange" @sort-change="handleSortChange" :default-sort="{ prop: 'courseId', order: 'ascending' }" :row-style="getRowStyle">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学年" align="center" prop="schoolYear">
        <template #default="scope">
          <dict-tag :options="school_year" :value="scope.row.schoolYear"/>
        </template>
      </el-table-column>
      <el-table-column label="学期" align="center" prop="semester">
        <template #default="scope">
          <dict-tag :options="semester" :value="scope.row.semester"/>
        </template>
      </el-table-column>
      <el-table-column label="课程代码" align="center" prop="courseCode" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="课程性质" align="center" prop="curriculum">
        <template #default="scope">
          <dict-tag :options="course_nature" :value="scope.row.curriculum"/>
        </template>
      </el-table-column>
      <el-table-column label="学分" align="center" prop="credit" />
      <el-table-column label="成绩备注" align="center" prop="scoreRemarks" />
      <el-table-column label="绩点" align="center" prop="gpa" />
      <el-table-column label="成绩性质" align="center" prop="gradesNature">
        <template #default="scope">
          <dict-tag :options="nature_of_grades" :value="scope.row.gradesNature"/>
        </template>
      </el-table-column>
      <el-table-column label="是否学位课程" align="center" prop="degreeCourse">
        <template #default="scope">
          <dict-tag :options="yes_or_no" :value="scope.row.degreeCourse"/>
        </template>
      </el-table-column>
      <el-table-column label="开课学院" align="center" prop="collegeOffering" />
      <el-table-column label="课程标记" align="center" prop="courseMarking">
        <template #default="scope">
          <dict-tag :options="student_marking_main" :value="scope.row.courseMarking"/>
        </template>
      </el-table-column>
      <el-table-column label="课程类别" align="center" prop="courseCategory">
        <template #default="scope">
          <dict-tag :options="course_category" :value="scope.row.courseCategory"/>
        </template>
      </el-table-column>
      <el-table-column label="课程归属" align="center" prop="courseOwnership" />
      <el-table-column label="教学班" align="center" prop="teachingClass" />
      <el-table-column label="任课教师" align="center" prop="courseTeacher" />
      <el-table-column label="考核方式" align="center" prop="teachingMethod">
        <template #default="scope">
          <dict-tag :options="assessment_method" :value="scope.row.teachingMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="学号" align="center" prop="studentId" sortable="custom"/>
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="学生标记" align="center" prop="studentMarking" />
      <el-table-column label="成绩" align="center" prop="scores" sortable="custom"/>
      <el-table-column label="是否成绩作废" align="center" prop="scoresMarking">
        <template #default="scope">
          <dict-tag :options="yes_or_no" :value="scope.row.scoresMarking"/>
        </template>
      </el-table-column>
      <el-table-column label="学分绩点" align="center" prop="gradePoint" sortable="custom"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['student:scores:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['student:scores:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改学生成绩对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="scoresRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学年" prop="schoolYear">
          <el-select v-model="form.schoolYear" placeholder="请选择学年">
            <el-option
              v-for="dict in school_year"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-select v-model="form.semester" placeholder="请选择学期">
            <el-option
              v-for="dict in semester"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程代码" prop="courseCode">
          <el-select
            v-model="form.courseCode"
            placeholder="请输入课程代码"
            filterable
            remote
            :remote-method="remoteCourseMethod"
            :loading="loadingCourse"
            clearable
            @focus="getCourseOptions"
          >
            <el-option
              v-for="item in courseOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="成绩备注" prop="scoreRemarks">
          <el-input v-model="form.scoreRemarks" placeholder="请输入成绩备注" />
        </el-form-item>
        <el-form-item label="成绩性质" prop="gradesNature">
          <el-select v-model="form.gradesNature" placeholder="请选择成绩性质">
            <el-option
              v-for="dict in nature_of_grades"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否学位课程" prop="degreeCourse">
          <el-select v-model="form.degreeCourse" placeholder="请选择是否学位课程">
            <el-option
              v-for="dict in yes_or_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程标记" prop="courseMarking">
          <el-select v-model="form.courseMarking" placeholder="请选择课程标记">
            <el-option
              v-for="dict in student_marking_main"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程归属" prop="courseOwnership">
          <el-input v-model="form.courseOwnership" placeholder="请输入课程归属" />
        </el-form-item>
        <el-form-item label="任课教师" prop="courseTeacher">
          <el-input v-model="form.courseTeacher" placeholder="请输入任课教师" />
        </el-form-item>
        <el-form-item label="学号" prop="studentId">
          <el-select
            v-model="form.studentId"
            placeholder="请输入学号"
            filterable
            remote
            :remote-method="remoteMethod"
            :loading="loadingStudent"
            clearable
            @focus="getStudentOptions"
          >
            <el-option
              v-for="item in studentOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="学生标记" prop="studentMarking">
          <el-input v-model="form.studentMarking" placeholder="请输入学生标记" />
        </el-form-item>
        <el-form-item label="成绩" prop="scores">
          <el-input v-model="form.scores" placeholder="请输入成绩" />
        </el-form-item>
        <el-form-item label="是否成绩作废" prop="scoresMarking">
          <el-select v-model="form.scoresMarking" placeholder="请选择是否成绩作废">
            <el-option
              v-for="dict in yes_or_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Scores">
import { listScores, getScores, delScores, addScores, updateScores } from "@/api/student/scores2"
import { searchInfo } from "@/api/student/info3"
import { searchCourse } from "@/api/student/course"

const { proxy } = getCurrentInstance()
const { assessment_method, course_nature, school_year, semester, yes_or_no, nature_of_grades, student_marking_main, course_category } = proxy.useDict('assessment_method', 'course_nature', 'school_year', 'semester', 'yes_or_no', 'nature_of_grades', 'student_marking_main', 'course_category')

const scoresList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const studentOptions = ref([])
const loadingStudent = ref(false)
const searchTimer = ref(null)
const courseOptions = ref([])
const loadingCourse = ref(false)
const courseSearchTimer = ref(null)
const studentNameOptions = ref([])
const loadingStudentName = ref(false)
const nameSearchTimer = ref(null)
const filteredStudentNameOptions = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    schoolYear: null,
    semester: null,
    courseCode: null,
    courseName: null,
    curriculum: null,
    credit: null,
    scoreRemarks: null,
    gpa: null,
    gradesNature: null,
    degreeCourse: null,
    collegeOffering: null,
    courseMarking: null,
    courseCategory: null,
    courseOwnership: null,
    teachingClass: null,
    courseTeacher: null,
    teachingMethod: null,
    studentId: null,
    studentName: null,
    studentMarking: null,
    scores: null,
    scoresMarking: null,
    gradePoint: null
  },
  rules: {
    schoolYear: [
      { required: true, message: "学年不能为空", trigger: "change" }
    ],
    semester: [
      { required: true, message: "学期不能为空", trigger: "change" }
    ],
    courseCode: [
      { required: true, message: "课程代码不能为空", trigger: "blur" }
    ],
    courseName: [
      { required: true, message: "课程名称不能为空", trigger: "blur" }
    ],
    curriculum: [
      { required: true, message: "课程性质不能为空", trigger: "change" }
    ],
    credit: [
      { required: true, message: "学分不能为空", trigger: "blur" }
    ],
    gpa: [
      { required: true, message: "绩点不能为空", trigger: "blur" }
    ],
    gradesNature: [
      { required: true, message: "成绩性质不能为空", trigger: "change" }
    ],
    degreeCourse: [
      { required: true, message: "是否学位课程不能为空", trigger: "change" }
    ],
    collegeOffering: [
      { required: true, message: "开课学院不能为空", trigger: "blur" }
    ],
    courseMarking: [
      { required: true, message: "课程标记不能为空", trigger: "change" }
    ],
    courseCategory: [
      { required: true, message: "课程类别不能为空", trigger: "change" }
    ],
    teachingClass: [
      { required: true, message: "教学班不能为空", trigger: "blur" }
    ],
    courseTeacher: [
      { required: true, message: "任课教师不能为空", trigger: "blur" }
    ],
    teachingMethod: [
      { required: true, message: "考核方式不能为空", trigger: "change" }
    ],
    studentId: [
      { required: true, message: "学号不能为空", trigger: "change" }
    ],
    studentName: [
      { required: true, message: "姓名不能为空", trigger: "blur" }
    ],
    scores: [
    { required: true, message: "成绩不能为空", trigger: "blur" },
    { 
      validator: (rule, value, callback) => {
        const score = Number(value);
        if (score <= 0 || score > 100) {
          callback(new Error('成绩必须大于0且小于等于100'));
        } else {
          callback();
        }
      }, 
      trigger: 'blur' 
    }
    ],
    scoresMarking: [
      { required: true, message: "是否成绩作废不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询学生成绩列表 */
function getList() {
  loading.value = true
  listScores(queryParams.value).then(response => {
    scoresList.value = response.rows
    total.value = response.total
    loading.value = false
    
  })
  
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    courseId: null,
    schoolYear: 5,
    semester: null,
    courseCode: null,
    courseName: null,
    curriculum: null,
    credit: null,
    scoreRemarks: null,
    gpa: null,
    gradesNature: 0,
    degreeCourse: 0,
    collegeOffering: null,
    courseMarking: 0,
    courseCategory: null,
    courseOwnership: null,
    teachingClass: null,
    courseTeacher: null,
    teachingMethod: null,
    studentId: null,
    studentName: null,
    studentMarking: null,
    scores: null,
    scoresMarking: 1,
    gradePoint: null
  }
  proxy.resetForm("scoresRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.courseId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加学生成绩"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _courseId = row.courseId || ids.value
  getScores(_courseId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改学生成绩"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["scoresRef"].validate(valid => {
    if (valid) {
      if (form.value.courseId != null) {
        updateScores(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addScores(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _courseIds = row.courseId || ids.value
  proxy.$modal.confirm('是否确认删除学生成绩编号为"' + _courseIds + '"的数据项？').then(function() {
    return delScores(_courseIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('student/scores2/export', {
    ...queryParams.value
  }, `scores_${new Date().getTime()}.xlsx`)
}

/** 获取所有学生姓名选项 */
function getStudentNameOptions() {
  if (studentNameOptions.value.length > 0) {
    return; // 已经加载过数据则不再重复加载
  }
  
  loadingStudentName.value = true;
  
  // 只获取必要的姓名数据，限制数量为50
  searchInfo({ 
    pageNum: 1, 
    pageSize: 50
  }).then(response => {
    // 验证响应数据结构
    if (response && Array.isArray(response.rows)) {
      // 直接映射为选项，添加空值防止无限滚动
      const allNames = response.rows.map(student => ({
        value: student.name,
        label: `${student.name} - ${student.studentId}`
      }));
      
      // 存储全部姓名数据
      studentNameOptions.value = allNames;
      // 初始化显示所有数据
      filteredStudentNameOptions.value = allNames;
    } else {
      studentNameOptions.value = [];
      filteredStudentNameOptions.value = [];
      proxy.$modal.msgError("无法获取学生姓名列表：数据格式错误");
    }
    
    loadingStudentName.value = false;
  }).catch(error => {
    // 打印详细错误信息
    console.error('获取学生姓名列表失败:', error);
    loadingStudentName.value = false;
    studentNameOptions.value = [];
    filteredStudentNameOptions.value = [];
    proxy.$modal.msgError("获取学生姓名列表失败");
  });
}

/** 获取所有课程选项 */
function getCourseOptions() {
  if (courseOptions.value.length > 0) {
    return; // 已经加载过数据则不再重复加载
  }
  
  loadingCourse.value = true;
  
  // 使用统一的API调用方式
  searchCourse({ 
    pageNum: 1, 
    pageSize: 50 
  }).then(response => {
    // 验证响应数据结构
    if (response && Array.isArray(response.rows)) {
      // 直接映射为选项
      courseOptions.value = response.rows.map(course => ({
        value: course.courseCode,
        label: `${course.courseCode} - ${course.courseName}`
      }));
    } else {
      courseOptions.value = [];
      proxy.$modal.msgError("无法获取课程列表：数据格式错误");
    }
    
    loadingCourse.value = false;
  }).catch(error => {
    // 打印详细错误信息
    console.error('获取课程列表失败:', error);
    loadingCourse.value = false;
    courseOptions.value = [];
    proxy.$modal.msgError("获取课程列表失败");
  });
}

/** 学生姓名远程搜索方法 */
function remoteNameMethod(query) {
  if (query === '') {
    // 如果查询为空，显示全部姓名
    filteredStudentNameOptions.value = studentNameOptions.value;
    return;
  }
  
  // 添加防抖处理
  clearTimeout(nameSearchTimer.value);
  nameSearchTimer.value = setTimeout(() => {
    // 只搜索长度≥1的关键词（支持单字搜索）
    if (query.length >= 1) {
      // 从本地数据中过滤
      const filtered = studentNameOptions.value.filter(item => 
        item.value.toLowerCase().includes(query.toLowerCase())
      );
      
      // 更新过滤后的选项
      filteredStudentNameOptions.value = filtered;
      
      if (filtered.length === 0) {
        proxy.$modal.msgWarning("未找到匹配的学生姓名");
      }
    }
  }, 300); // 300毫秒防抖
}

/** 获取所有学生选项 */
function getStudentOptions() {
  if (studentOptions.value.length > 0) {
    return; // 已经加载过数据则不再重复加载
  }
  
  loadingStudent.value = true;
  searchInfo({ pageNum: 1, pageSize: 50 }).then(response => {
    if (response && response.rows) {
      // 只显示前50个学生的学号作为搜索建议
      studentOptions.value = response.rows.slice(0, 50).map(student => ({
        value: student.studentId,
        label: `${student.studentId} - ${student.name}`
      }));
      
      // 添加"输入更多以搜索"提示
      if (response.total > 50) {
        studentOptions.value.push({
          value: '',
          label: '输入更多以搜索全部学生',
          disabled: true
        });
      }
    } else {
      studentOptions.value = [];
      proxy.$modal.msgError("无法获取学生列表");
    }
    loadingStudent.value = false;
  }).catch(error => {
    loadingStudent.value = false;
    studentOptions.value = [];
    console.error('获取学生列表失败:', error);
    proxy.$modal.msgError("获取学生列表失败");
  });
}

/** 远程搜索方法 */
function remoteMethod(query) {
  if (query === '') {
    return;
  }
  
  // 添加防抖处理
  clearTimeout(searchTimer.value);
  searchTimer.value = setTimeout(() => {
    loadingStudent.value = true;
    
    // 只搜索长度≥2的关键词
    if (query.length >= 2) {
      searchInfo({ studentId: query }).then(response => {
        if (response.rows && response.rows.length > 0) {
          // 精确匹配：学号完全包含搜索词的学生
          const exactMatches = response.rows.filter(student => 
            student.studentId.includes(query)
          );
          
          // 模糊匹配：姓名包含搜索词的学生（如果查询不是纯数字）
          const isNumericQuery = /^\d+$/.test(query);
          const fuzzyMatches = isNumericQuery ? [] : response.rows.filter(student => 
            student.name.toLowerCase().includes(query.toLowerCase())
          );
          
          // 合并结果，确保去重
          const uniqueResults = [...new Map([
            ...exactMatches.map(item => [item.studentId, item]), 
            ...fuzzyMatches.map(item => [item.studentId, item])
          ]).values()];
          
          studentOptions.value = uniqueResults.map(student => ({
            value: student.studentId,
            label: `${student.studentId} - ${student.name}`
          }));
        } else {
          studentOptions.value = [];
          proxy.$modal.msgWarning("未找到匹配的学生");
        }
        loadingStudent.value = false;
      }).catch(error => {
        console.error('搜索学生失败:', error);
        loadingStudent.value = false;
        studentOptions.value = [];
        proxy.$modal.msgError("搜索学生失败，请重试");
      });
    } else {
      loadingStudent.value = false;
    }
  }, 300); // 300毫秒防抖
}

/** 课程代码远程搜索方法 */
function remoteCourseMethod(query) {
  if (query === '') {
    return;
  }
  
  // 添加防抖处理
  clearTimeout(courseSearchTimer.value);
  courseSearchTimer.value = setTimeout(() => {
    loadingCourse.value = true;
    
    // 只搜索长度≥2的关键词
    if (query.length >= 2) {
      searchCourse({ 
        courseCode: query,
        pageNum: 1, 
        pageSize: 50 
      }).then(response => {
        // 验证响应数据结构
        if (response && Array.isArray(response.rows)) {
          // 处理搜索结果
          const results = response.rows.map(course => ({
            value: course.courseCode,
            label: `${course.courseCode} - ${course.courseName}`
          }));
          
          courseOptions.value = results;
          
          if (results.length === 0) {
            proxy.$modal.msgWarning("未找到匹配的课程");
          }
        } else {
          courseOptions.value = [];
          proxy.$modal.msgError("无效的响应数据格式");
        }
        
        loadingCourse.value = false;
      }).catch(error => {
        // 打印详细错误信息
        console.error('搜索课程失败:', error);
        loadingCourse.value = false;
        courseOptions.value = [];
        proxy.$modal.msgError("搜索课程失败，请重试");
      });
    } else {
      loadingCourse.value = false;
    }
  }, 300); // 300毫秒防抖
}

getList()

function handleSortChange({ column, prop, order }) {
  if (!['studentId', 'scores', 'gradePoint'].includes(prop)) {
    return; // 只允许对指定字段进行排序
  }

  const sortedData = [...scoresList.value];

  sortedData.sort((a, b) => {
    let valueA = a[prop];
    let valueB = b[prop];

    // 如果是字符串，则统一转为小写比较
    if (typeof valueA === 'string' && typeof valueB === 'string') {
      return order === 'ascending' ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
    }

    // 如果是数字，则直接相减比较
    if (typeof valueA === 'number' && typeof valueB === 'number') {
      return order === 'ascending' ? valueA - valueB : valueB - valueA;
    }

    // 默认情况下将值转为字符串再比较
    return order === 'ascending'
      ? String(valueA).localeCompare(String(valueB))
      : String(valueB).localeCompare(String(valueA));
  });

  scoresList.value = sortedData;
}

function getRowStyle({ row }) {
  if (row.scores < 60 || row.scoresMarking === 0) {
    return { color: 'red' }
  }
  return {}
}

</script>
