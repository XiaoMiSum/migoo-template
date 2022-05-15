import request from '@/utils/request'
const url = '/redis'

// 获得表定义分页
export function getCache() {
  return request({
    url,
    method: 'get'
  })
}

export function getKeyList() {
  return request({
    url: url + '/keys',
    method: 'get'
  })
}
