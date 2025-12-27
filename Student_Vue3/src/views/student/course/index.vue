<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['student:course:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['student:course:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['student:course:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['student:course:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="课程代码" align="center" prop="courseCode" />
      <el-table-column label="课程性质" align="center" prop="curriculum">
        <template #default="scope">
          <dict-tag :options="course_nature" :value="scope.row.curriculum"/>
        </template>
      </el-table-column>
      <el-table-column label="学分" align="center" prop="credit" />
      <el-table-column label="绩点" align="center" prop="gpa" />
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
      <el-table-column label="考核方式" align="center" prop="teachingMethod">
        <template #default="scope">
          <dict-tag :options="assessment_method" :value="scope.row.teachingMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['student:course:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['student:course:remove']">删除</el-button>
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

    <!-- 添加或修改课程信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="courseRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程代码" prop="courseCode">
          <el-input v-model="form.courseCode" placeholder="请输入课程代码" />
        </el-form-item>
        <el-form-item label="课程性质" prop="curriculum">
          <el-select v-model="form.curriculum" placeholder="请选择课程性质">
            <el-option
              v-for="dict in course_nature"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input v-model="form.credit" placeholder="请输入学分" />
        </el-form-item>
        <el-form-item label="绩点" prop="gpa">
          <el-input v-model="form.gpa" placeholder="请输入绩点" />
        </el-form-item>
        <el-form-item label="开课学院" prop="collegeOffering">
          <el-input v-model="form.collegeOffering" placeholder="请输入开课学院" />
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
        <el-form-item label="课程类别" prop="courseCategory">
          <el-select v-model="form.courseCategory" placeholder="请选择课程类别">
            <el-option
              v-for="dict in course_category"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程归属" prop="courseOwnership">
          <el-input v-model="form.courseOwnership" placeholder="请输入课程归属" />
        </el-form-item>
        <el-form-item label="考核方式" prop="teachingMethod">
          <el-select v-model="form.teachingMethod" placeholder="请选择考核方式">
            <el-option
              v-for="dict in assessment_method"
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

<script setup name="Course">
import { listCourse, getCourse, delCourse, addCourse, updateCourse } from "@/api/student/course"
import { searchCourse } from "@/api/student/course"

const { proxy } = getCurrentInstance()
const { assessment_method, course_nature, student_marking_main, course_category } = proxy.useDict('assessment_method', 'course_nature', 'student_marking_main', 'course_category')

const courseList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const loadingCourse = ref(false)
const courseSearchTimer = ref(null)
const courseOptions = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    courseName: null,
    courseCode: null,
    curriculum: null,
    collegeOffering: null,
    courseMarking: null,
    courseCategory: null,
    courseOwnership: null,
    teachingMethod: null
  },
  rules: {
    courseName: [
      { required: true, message: "课程名称不能为空", trigger: "blur" }
    ],
    courseCode: [
      { required: true, message: "课程代码不能为空", trigger: "blur" }
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
    collegeOffering: [
      { required: true, message: "开课学院不能为空", trigger: "blur" }
    ],
    courseMarking: [
      { required: true, message: "课程标记不能为空", trigger: "change" }
    ],
    courseCategory: [
      { required: true, message: "课程类别不能为空", trigger: "change" }
    ],
    teachingMethod: [
      { required: true, message: "考核方式不能为空", trigger: "change" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询课程信息列表 */
function getList() {
  loading.value = true
  listCourse(queryParams.value).then(response => {
    courseList.value = response.rows
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
    id: null,
    courseName: null,
    courseCode: null,
    curriculum: null,
    credit: null,
    gpa: null,
    collegeOffering: null,
    courseMarking: null,
    courseCategory: null,
    courseOwnership: null,
    teachingMethod: null
  }
  proxy.resetForm("courseRef")
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
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加课程信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getCourse(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改课程信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCourse(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCourse(form.value).then(response => {
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
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除课程信息编号为"' + _ids + '"的数据项？').then(function() {
    return delCourse(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('student/course/export', {
    ...queryParams.value
  }, `course_${new Date().getTime()}.xlsx`)
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
</script>
