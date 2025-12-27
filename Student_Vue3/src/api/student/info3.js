import request from '@/utils/request'

// 查询学生信息列表
export function listInfo3(query) {
  return request({
    url: '/student/info3/list',
    method: 'get',
    params: query
  })
}

export function searchInfo(query) {
  return request({
    url: '/student/info3/search',
    method: 'get',
    params: query
  })
}

// 查询学生信息详细
export function getInfo3(id) {
  return request({
    url: '/student/info3/' + id,
    method: 'get'
  })
}

// 新增学生信息
export function addInfo3(data) {
  return request({
    url: '/student/info3',
    method: 'post',
    data: data
  })
}

// 修改学生信息
export function updateInfo3(data) {
  return request({
    url: '/student/info3',
    method: 'put',
    data: data
  })
}

// 删除学生信息
export function delInfo3(id) {
  return request({
    url: '/student/info3/' + id,
    method: 'delete'
  })
}
