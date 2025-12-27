import request from '@/utils/request'

// 查询教师评价列表
export function listTeacher(query) {
  return request({
    url: '/evaluation/teacher3/list',
    method: 'get',
    params: query
  })
}

// 查询教师评价详细
export function getTeacher(id) {
  return request({
    url: '/evaluation/teacher/' + id,
    method: 'get'
  })
}

// 新增教师评价
export function addTeacher(data) {
  return request({
    url: '/evaluation/teacher',
    method: 'post',
    data: data
  })
}

// 修改教师评价
export function updateTeacher(data) {
  return request({
    url: '/evaluation/teacher',
    method: 'put',
    data: data
  })
}

// 删除教师评价
export function delTeacher(id) {
  return request({
    url: '/evaluation/teacher/' + id,
    method: 'delete'
  })
}
