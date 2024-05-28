import request from '@/config/axios'

const url = '/developer/cvs/provider'

// 查询云服务账号列表
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}
// 查询云服务账号
export const getData = (id: any) => {
  return request.get({
    url: url + '/' + id
  })
}

// 新增云服务账号
export const addData = (data: any) => {
  return request.post({
    url,
    data: data
  })
}

// 修改云服务账号
export const updateData = (data: any) => {
  return request.put({
    url,
    data: data
  })
}

// 删除云服务账号
export const delData = (id: any) => {
  return request.delete({
    url: url + '/' + id
  })
}

export const getTree = () => {
  return request.get({
    url: url + '/tree',
    method: 'get'
  })
}
