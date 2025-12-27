<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评价人" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入评价人"
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
          v-hasPermi="['evaluation:teacher:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['evaluation:teacher:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['evaluation:teacher:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['evaluation:teacher:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="teacherList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="内容" align="center" prop="content">
        <template #default="scope">
          <div v-if="scope.row.content" v-html="renderRichText(scope.row.content)"></div>
          <div v-else></div>
        </template>
      </el-table-column>
      <el-table-column label="评分" align="center" prop="scores" />
      <el-table-column label="评价人" align="center" prop="name" />
      <el-table-column label="评价时间" align="center" prop="timeEvaluation" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.timeEvaluation, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['evaluation:teacher:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['evaluation:teacher:remove']">删除</el-button>
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

    <!-- 添加或修改教师评价对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="teacherRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评价对象" prop="target">
          <el-input v-model="form.target" placeholder="请输入评价对象" />
        </el-form-item>
        <el-form-item label="评价对象学号" prop="targetId">
          <el-input v-model="form.targetId" placeholder="请输入评价对象学号" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="评分" prop="scores">
          <el-input v-model="form.scores" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评价人" prop="name">
          <el-input v-model="form.name" placeholder="请输入评价人" />
        </el-form-item>
        <el-form-item label="评价人id" prop="nameId">
          <el-input v-model="form.nameId" placeholder="请输入评价人工号" />
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

<script setup name="Teacher">
import { listTeacher, getTeacher, delTeacher, addTeacher, updateTeacher } from "@/api/evaluation/teacher2"

const { proxy } = getCurrentInstance()

const teacherList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const daterangeTimeEvaluation = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    target: null,
    targetId: null,
    content: null,
    scores: null,
    name: null,
    nameId: null,
    isevaluation: null,
    timeEvaluation: null
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询教师评价列表 */
function getList() {
  loading.value = true
  queryParams.value.params = {}
  if (null != daterangeTimeEvaluation && '' != daterangeTimeEvaluation) {
    queryParams.value.params["beginTimeEvaluation"] = daterangeTimeEvaluation.value[0]
    queryParams.value.params["endTimeEvaluation"] = daterangeTimeEvaluation.value[1]
  }
  listTeacher(queryParams.value).then(response => {
    teacherList.value = response.rows
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
    target: null,
    targetId: null,
    content: null,
    scores: null,
    name: null,
    nameId: null,
    isevaluation: null,
    timeEvaluation: null
  }
  proxy.resetForm("teacherRef")
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
  title.value = "添加教师评价"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getTeacher(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改教师评价"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["teacherRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTeacher(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addTeacher(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除教师评价编号为"' + _ids + '"的数据项？').then(function() {
    return delTeacher(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 渲染富文本内容并限制长度 */
function renderRichText(content) {
  // 移除 script 和 style 标签以防止 XSS 攻击
  const sanitizedContent = content
    .replace(/<script.*?>.*?<\/script>/gis, '')
    .replace(/<style.*?>.*?<\/style>/gis, '');

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
  proxy.download('evaluation/teacher2/export', {
    ...queryParams.value
  }, `teacher_${new Date().getTime()}.xlsx`)
}

getList()
</script>
