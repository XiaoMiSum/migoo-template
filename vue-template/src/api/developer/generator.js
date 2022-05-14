import request from '@/utils/request'
const url = '/generator'

// 获得表定义分页
export function getCodegenTablePage(query) {
  return request({
    url,
    method: 'get',
    params: query
  })
}

// 获得表和字段的明细
export function getCodegenDetail(tableId) {
  return request({
    url: url + '/' + tableId,
    method: 'get'
  })
}

// 修改代码生成信息
export function updateCodegen(data) {
  return request({
    url,
    method: 'put',
    data: data
  })
}

// 预览生成代码
export function previewCodegen(tableId) {
  return request({
    url: url + '/' + tableId + '/preview',
    method: 'get'
  })
}

// 下载生成代码
export function downloadCodegen(tableId) {
  return request({
    url: url + '/' + tableId + '/download',
    method: 'get',
    responseType: 'blob'
  })
}

// 获得表定义分页
export function getSchemaTableList(query) {
  return request({
    url: '/tool/codegen/db/table/list',
    method: 'get',
    params: query
  })
}

// 基于 SQL 建表语句，创建代码生成器的表定义
export function createCodegenListFromSQL(data) {
  return request({
    url,
    method: 'post',
    data: 'sql=' + data.sql
  })
}

// 删除数据库的表和字段定义
export function deleteCodegen(tableId) {
  return request({
    url: url + '/' + tableId,
    method: 'delete'
  })
}
