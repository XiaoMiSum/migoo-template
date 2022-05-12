import request from '@/utils/request'
import { praseStrEmpty } from '@/utils/ruoyi'

const url = '/dept'

// 查询下拉
export function listSimple(params) {
  return request({
    url: url + '/simple',
    method: 'get',
    params
  })
}

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
export function delData(userId) {
  return request({
    url: url + '/' + praseStrEmpty(userId),
    method: 'delete'
  })
}

