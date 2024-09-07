import request from '@/config/axios'

const url = '/station-letter'

// 查询列表
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 查询详细
export const getData = (id: any) => {
  return request.get({
    url: url + '/' + id
  })
}

// 已读
export const read = (data: any) => {
  return request.put({
    url: url + '/read',
    data: data
  })
}

// 未读
export const unread = (data: any) => {
  return request.put({
    url: url + '/unread',
    data: data
  })
}

// 删除
export const delData = (ids: any[]) => {
  return request.delete({
    url: url + 'ids=' + ids.toString()
  })
}

```

