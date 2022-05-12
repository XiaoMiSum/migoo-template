import request from '@/utils/request'
import { praseStrEmpty } from '@/utils/ruoyi'

const url = '/generator'

// 查询列表
export function listData(params) {
  return request({
    url,
    method: 'get',
    params
  })
}

// 查询详细
export function getData(userId) {
  return request({
    url: url + '/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增
export function addData(data) {
  return request({
    url,
    method: 'post',
    data
  })
}

// 修改
export function updateData(data) {
  return request({
    url,
    method: 'put',
    data
  })
}

// 删除
export function delData(id) {
  return request({
    url: url + '/' + praseStrEmpty(id),
    method: 'delete'
  })
}
