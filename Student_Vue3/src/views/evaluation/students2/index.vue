<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评价对象" prop="name">
        <el-select
          v-model="queryParams.name"
          placeholder="请输入评价对象"
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
      <el-form-item label="评价对象学号" prop="nameId">
        <el-select
            v-model="queryParams.nameId"
            placeholder="请输入评价对象学号"
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
      <el-form-item label="班级" prop="teachingClass">
        <el-input
          v-model="queryParams.teachingClass"
          placeholder="请输入班级"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评价时间" style="width: 308px">
        <el-date-picker
          v-model="daterangeTimeEvaluation"
          value-format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['evaluation:students:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['evaluation:students:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['evaluation:students:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['evaluation:students:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="studentsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="内容" align="center" prop="content">
        <template #default="scope">
          <div v-if="scope.row.content" v-html="renderRichText(scope.row.content)"></div>
          <div v-else></div>
        </template>
      </el-table-column>
      <el-table-column label="评分" align="center" prop="scores" />
      <el-table-column label="评价人" align="center" prop="name" />
      <el-table-column label="评价人学号" align="center" prop="nameId" />
      <el-table-column label="班级" align="center" prop="teachingClass" />
      <el-table-column label="评价时间" align="center" prop="timeEvaluation" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.timeEvaluation, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['evaluation:students:edit']">查看</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['evaluation:students:remove']">删除</el-button>
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

    <!-- 添加或修改学生评价对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="studentsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="评分" prop="scores">
          <el-input v-model="form.scores" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评价人" prop="name">
          <el-input v-model="form.name" placeholder="请输入评价人" />
        </el-form-item>
        <el-form-item label="评价标记" prop="isevaluation">
          <el-input v-model="form.isevaluation" placeholder="请输入评价标记" />
        </el-form-item>
        <el-form-item label="评价人id" prop="nameId">
          <el-input v-model="form.nameId" placeholder="请输入评价人id" />
        </el-form-item>
        <el-form-item label="班级" prop="teachingClass">
          <el-input v-model="form.teachingClass" placeholder="请输入班级" />
        </el-form-item>
        <el-form-item label="评价时间" prop="timeEvaluation">
          <el-date-picker clearable
            v-model="form.timeEvaluation"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择评价时间">
          </el-date-picker>
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

<script setup name="Students">
import { listStudents, getStudents, delStudents, addStudents, updateStudents } from "@/api/evaluation/students2"
import { searchInfo } from "@/api/student/info3"

const { proxy } = getCurrentInstance()

const studentsList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const daterangeTimeEvaluation = ref([])
const studentOptions = ref([])
const loadingStudent = ref(false)
const searchTimer = ref(null)
const studentNameOptions = ref([])
const loadingStudentName = ref(false)
const nameSearchTimer = ref(null)

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    content: null,
    scores: null,
    name: null,
    isevaluation: null,
    nameId: null,
    teachingClass: null,
    timeEvaluation: null
  },
  rules: {
    content: [
      { required: true, message: "内容不能为空", trigger: "blur" }
    ],
    scores: [
      { required: true, message: "评分不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询学生评价列表 */
function getList() {
  loading.value = true
  queryParams.value.params = {}
  if (null != daterangeTimeEvaluation && '' != daterangeTimeEvaluation) {
    queryParams.value.params["beginTimeEvaluation"] = daterangeTimeEvaluation.value[0]
    queryParams.value.params["endTimeEvaluation"] = daterangeTimeEvaluation.value[1]
  }
  listStudents(queryParams.value).then(response => {
    studentsList.value = response.rows
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
    content: null,
    scores: null,
    name: null,
    isevaluation: null,
    nameId: null,
    teachingClass: null,
    timeEvaluation: null
  }
  proxy.resetForm("studentsRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeTimeEvaluation.value = []
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
  title.value = "添加学生评价"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getStudents(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改学生评价"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["studentsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateStudents(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addStudents(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除学生评价编号为"' + _ids + '"的数据项？').then(function() {
    return delStudents(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 学生姓名远程搜索方法 */
function remoteNameMethod(query) {
  if (query === '') {
    studentNameOptions.value = [];
    return;
  }

  clearTimeout(nameSearchTimer.value);
  nameSearchTimer.value = setTimeout(() => {
    loadingStudentName.value = true;

    // 只允许长度 ≥ 1 的关键词触发搜索
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

        // 如果不是纯数字查询，也支持学号匹配
        if (!isNumericQuery) {
          const idMatches = response.rows.filter(student =>
            student.studentId.includes(query)
          );
          matches = [
            ...new Map([...idMatches, ...matches].map(item => [item.studentId, item])).values()
          ];
        }

        // 映射为选项并去重（基于 name）
        const results = matches.map(student => ({
          value: student.name,
          label: `${student.name} - ${student.studentId}`
        }));

        const uniqueResults = Array.from(new Map(results.map(item => [item.value, item])).values());

        studentNameOptions.value = uniqueResults;

        if (uniqueResults.length === 0) {
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
  }, 300); // 防抖 300ms
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
        // 精确匹配学号 + 模糊匹配姓名
        const isNumericQuery = /^\d+$/.test(query);
        let matches = response.rows.filter(student =>
          student.studentId.includes(query)
        );

        if (!isNumericQuery) {
          matches = [
            ...new Set([
              ...matches,
              ...response.rows.filter(student =>
                student.name.toLowerCase().includes(query.toLowerCase())
              )
            ])
          ]
        }

        // 使用 Map 去重（基于 studentId）
        const uniqueMatches = Array.from(new Map(matches.map(student => [student.studentId, {
          value: student.studentId,
          label: `${student.studentId} - ${student.name}`
        }])).values())

        studentOptions.value = uniqueMatches;
        if (uniqueMatches.length === 0) {
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

/** 渲染富文本内容并限制长度 */
function renderRichText(content) {
  // 移除 script 和 style 标签以防止 XSS 攻击
  const sanitizedContent = content
    .replace(/<script.*?>.*?<\/script>/gis, '')
    .replace(/<style.*?>.*?<\/style>/gis, '');

  // 截取前 100 字符
  const maxLength = 100;
  let tempDiv = document.createElement('div');
  tempDiv.innerHTML = sanitizedContent;
  const textContent = tempDiv.textContent || tempDiv.innerText || '';
  return textContent.length > maxLength 
    ? tempDiv.innerHTML.substring(0, maxLength) + '...' 
    : tempDiv.innerHTML;
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('evaluation/students/export', {
    ...queryParams.value
  }, `students_${new Date().getTime()}.xlsx`)
}

getList()
</script>
