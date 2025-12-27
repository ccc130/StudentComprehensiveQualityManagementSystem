import request from '@/utils/request'

// 查询学生评价列表
export function listStudents(query) {
  return request({
    url: '/evaluation/students3/list',
    method: 'get',
    params: query
  })
}

// 查询学生评价详细
export function getStudents(id) {
  return request({
    url: '/evaluation/students/' + id,
    method: 'get'
  })
}

// 新增学生评价
export function addStudents(data) {
  return request({
    url: '/evaluation/students',
    method: 'post',
    data: data
  })
}

// 修改学生评价
export function updateStudents(data) {
  return request({
    url: '/evaluation/students',
    method: 'put',
    data: data
  })
}

// 删除学生评价
export function delStudents(id) {
  return request({
    url: '/evaluation/students/' + id,
    method: 'delete'
  })
}
