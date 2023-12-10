import request from '@/config/axios'
import { UserStatus, User } from './index.d'

const url = '/user'

// 查询用户列表
export const listUser = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 查询用户列表
export const listSimple = () => {
  return request.get({
    url: url + '/simple'
  })
}

// 查询用户详细
export const getUser = (userId: any) => {
  return request.get({
    url: url + '/' + userId
  })
}

// 新增用户
export const addUser = (data: User) => {
  return request.post({
    url,
    data
  })
}

// 修改用户
export const updateUser = (data: UserStatus) => {
  return request.put({
    url,
    data
  })
}

// 删除用户
export const delUser = (userId: any) => {
  return request.delete({
    url: url + '/' + userId
  })
}

// 用户密码重置
export const resetUserPwd = (id: any, password: any) => {
  const data = {
    id,
    password
  }
  return request.post({
    url: url + '/password',
    data
  })
}

// 查询用户列表
export const listUserRoles = (id: any) => {
  return request.get({
    url: url + '/' + id + '/role'
  })
} // 查询用户列表
export const updateUserRoles = (data: User) => {
  return request.post({
    url: url + '/role',
    data
  })
}
