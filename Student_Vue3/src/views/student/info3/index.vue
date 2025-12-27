<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentId">
        <el-select
            v-model="queryParams.studentId"
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
      <el-form-item label="姓名" prop="Name">
        <el-select
          v-model="queryParams.Name"
          placeholder="请输入姓名"
          filterable
          remote
          :remote-method="remoteNameMethod" 
          :loading="loadingStudentName"
          clearable
          @focus="getStudentNameOptions"
        >
          <el-option
            v-for="item in studentNameOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="教学班" prop="teachingClass">
        <el-input
          v-model="queryParams.teachingClass"
          placeholder="请输入教学班"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['student:info3:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['student:info3:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['student:info3:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['student:info3:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="info3List" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学号" align="center" prop="studentId" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="教学班" align="center" prop="teachingClass" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['student:info3:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['student:info3:remove']">删除</el-button>
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

    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="info3Ref" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="教学班" prop="teachingClass">
          <el-input v-model="form.teachingClass" placeholder="请输入教学班" />
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

<script setup name="Info3">
import { listInfo3, getInfo3, delInfo3, addInfo3, updateInfo3 } from "@/api/student/info3"
import { searchInfo } from "@/api/student/info3"

const { proxy } = getCurrentInstance()

const info3List = ref([])
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

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    studentId: null,
    name: null,
    teachingClass: null
  },
  rules: {
    studentId: [
      { required: true, message: "学号不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "姓名不能为空", trigger: "blur" }
    ],
    teachingClass: [
      { required: true, message: "教学班不能为空", trigger: "blur" }
    ]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询学生信息列表 */
function getList() {
  loading.value = true
  listInfo3(queryParams.value).then(response => {
    info3List.value = response.rows
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
    studentId: null,
    name: null,
    teachingClass: null
  }
  proxy.resetForm("info3Ref")
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
  title.value = "添加学生信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getInfo3(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改学生信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["info3Ref"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateInfo3(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addInfo3(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除学生信息编号为"' + _ids + '"的数据项？').then(function() {
    return delInfo3(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('student/info3/export', {
    ...queryParams.value
  }, `info3_${new Date().getTime()}.xlsx`)
}

/** 学生姓名远程搜索方法 */
function remoteNameMethod(query) {
  if (query === '') {
    return;
  }

  clearTimeout(nameSearchTimer.value);
  nameSearchTimer.value = setTimeout(() => {
    loadingStudentName.value = true;

    if (query.length < 1) {
      loadingStudentName.value = false;
      return;
    }

    searchInfo({ name: query }).then(response => {
      if (response.rows && Array.isArray(response.rows)) {
        const isNumericQuery = /^\d+$/.test(query);
        let matches = response.rows.filter(student =>
          student.name.toLowerCase().includes(query.toLowerCase())
        );

        if (!isNumericQuery) {
          const idMatches = response.rows.filter(student =>
            student.studentId.includes(query)
          );
          matches = [...idMatches, ...matches];
        }

        const results = matches.map(student => ({
          value: student.name,
          label: `${student.name} - ${student.studentId}`
        }));

        studentNameOptions.value = results;

        if (results.length === 0) {
          proxy.$modal.msgWarning("未找到匹配的学生姓名");
        }
      } else {
        studentNameOptions.value = [];
        proxy.$modal.msgWarning("未找到匹配的学生姓名");
      }

      loadingStudentName.value = false;
    }).catch(error => {
      console.error('搜索学生姓名失败:', error);
      loadingStudentName.value = false;
      studentNameOptions.value = [];
      proxy.$modal.msgError("搜索学生姓名失败，请重试");
    });
  }, 300); 
}

/** 课程代码远程搜索方法 */
function remoteCourseMethod(query) {
  if (query === '') {
    return;
  }

  clearTimeout(courseSearchTimer.value);
  courseSearchTimer.value = setTimeout(() => {
    loadingCourse.value = true;

    if (query.length < 2) {
      loadingCourse.value = false;
      return;
    }

    searchCourse({
      courseCode: query,
      pageNum: 1,
      pageSize: 50
    }).then(response => {
      if (response && Array.isArray(response.rows)) {
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
      console.error('搜索课程失败:', error);
      loadingCourse.value = false;
      courseOptions.value = [];
      proxy.$modal.msgError("搜索课程失败，请重试");
    });
  }, 300);
}

/** 远程搜索方法（学号） */
function remoteMethod(query) {
  if (query === '') {
    return;
  }

  clearTimeout(searchTimer.value);
  searchTimer.value = setTimeout(() => {
    loadingStudent.value = true;

    if (query.length < 2) {
      loadingStudent.value = false;
      return;
    }

    searchInfo({
      studentId: query,
      pageNum: 1,
      pageSize: 50
    }).then(response => {
      if (response.rows && response.rows.length > 0) {
        const isNumericQuery = /^\d+$/.test(query);
        let matches = response.rows.filter(student =>
          student.studentId.includes(query)
        );

        if (!isNumericQuery) {
          matches = [
            ...matches,
            ...response.rows.filter(student =>
              student.name.toLowerCase().includes(query.toLowerCase())
            )
          ];
        }

        const results = matches.map(student => ({
          value: student.studentId,
          label: `${student.studentId} - ${student.name}`
        }));

        studentOptions.value = results;
        if (results.length === 0) {
          proxy.$modal.msgWarning("未找到匹配的学生");
        }
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
  }, 300);
}

getList()
</script>
