import request from '@/utils/request'

const url = ''

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
