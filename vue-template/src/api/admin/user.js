import request from '@/utils/request'
import { praseStrEmpty } from '@/utils/ruoyi'

const url = '/user'

// 查询用户列表
export function listUser(params) {
  return request({
    url,
    method: 'get',
    params
  })
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: url + '/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url,
    method: 'post',
    data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url,
    method: 'put',
    data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: url + '/' + praseStrEmpty(userId),
    method: 'delete'
  })
}

// 用户密码重置
export function resetUserPwd(id, password) {
  const data = {
    id,
    password
  }
  return request({
    url: url + '/password',
    method: 'post',
    data
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: url + '/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: url + '/profile',
    method: 'put',
    data
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: url + '/password',
    method: 'put',
    data: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: url + '/profile/avatar',
    method: 'put',
    data: data
  })
}
