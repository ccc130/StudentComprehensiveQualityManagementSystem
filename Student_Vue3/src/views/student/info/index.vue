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
          v-hasPermi="['student:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['student:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['student:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['student:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange" @sort-change="handleSortChange" :default-sort="{ prop: 'studentId', order: 'ascending' }">
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
      <el-table-column label="学号" align="center" prop="studentId" sortable="custom"/>
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="教学班" align="center" prop="teachingClass" />
      <el-table-column label="总成绩" align="center" prop="totalScores" sortable="custom"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['student:info:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['student:info:remove']">删除</el-button>
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
      <el-form ref="infoRef" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="教学班" prop="teachingClass">
          <el-input v-model="form.teachingClass" placeholder="请输入教学班" />
        </el-form-item>
        <el-form-item label="总成绩" prop="totalScores">
          <el-input v-model="form.totalScores" placeholder="请输入总成绩" />
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

<script setup name="Info">
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/student/info"
import { searchInfo } from "@/api/student/info3"

const { proxy } = getCurrentInstance()
const { school_year, semester } = proxy.useDict('school_year', 'semester')

const infoList = ref([])
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
    studentId: null,
    name: null,
    teachingClass: null,
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
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询学生信息列表 */
function getList() {
  loading.value = true
  listInfo(queryParams.value).then(response => {
    infoList.value = response.rows
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
    schoolYear: 5,
    semester: 2,
    studentId: null,
    name: null,
    teachingClass: null,
    totalScores: null
  }
  proxy.resetForm("infoRef")
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
  getInfo(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改学生信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["infoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addInfo(form.value).then(response => {
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
    return delInfo(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('student/info/export', {
    ...queryParams.value
  }, `info_${new Date().getTime()}.xlsx`)
}

/**
 * 处理表格排序变化
 * @param {Object} param - 排序信息对象
 * @param {Object} param.column - 排序的列对象
 * @param {string} param.prop - 排序字段名
 * @param {string} param.order - 排序顺序 ascending/descending
 */
function handleSortChange({ column, prop, order }) {
  if (!['studentId', 'totalScores'].includes(prop)) {
    return; // 只允许对指定字段进行排序
  }

  const sortedData = [...infoList.value];

  sortedData.sort((a, b) => {
    let valueA = a[prop];
    let valueB = b[prop];

    // 如果是数字，则直接相减比较
    if (typeof valueA === 'number' && typeof valueB === 'number') {
      return order === 'ascending' ? valueA - valueB : valueB - valueA;
    }

    // 默认情况下将值转为字符串再比较
    return order === 'ascending'
      ? String(valueA).localeCompare(String(valueB))
      : String(valueB).localeCompare(String(valueA));
  });

  infoList.value = sortedData;
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

/** 学生姓名远程搜索方法 */
function remoteNameMethod(query) {
  if (query === '') {
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

getList()
</script>
