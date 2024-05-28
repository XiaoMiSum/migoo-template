import request from '@/config/axios'

const url = '/developer/cvs/machine'

// 查询云服务器列表
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 操作云服务器
export const optionCvs = (id: any, option: string) => {
  return request.post({url: url + '/' + id + '/' + option})
}

// 同步云服务器
export const syncData = () => {
  return request.post({url})
}

// 修改云服务器
export const updateData = (data: any) => {
  return request.put({
    url,
    method: 'put',
    data: data
  })
}

// 删除云服务器
export const delData = (id: any) => {
  return request.delete({
    url: url + '/' + id,
    method: 'delete'
  })
}

export const getTree = () => {
  return request.get({
    url: url + '/tree'
  })
}
