import request from '@/utils/request'

// 查询学生成绩列表
export function listScores(query) {
  return request({
    url: '/student/scores/list',
    method: 'get',
    params: query
  })
}

// 查询学生成绩详细
export function getScores(courseId) {
  return request({
    url: '/student/scores/' + courseId,
    method: 'get'
  })
}

// 新增学生成绩
export function addScores(data) {
  return request({
    url: '/student/scores',
    method: 'post',
    data: data
  })
}

// 修改学生成绩
export function updateScores(data) {
  return request({
    url: '/student/scores',
    method: 'put',
    data: data
  })
}

// 删除学生成绩
export function delScores(courseId) {
  return request({
    url: '/student/scores/' + courseId,
    method: 'delete'
  })
}
