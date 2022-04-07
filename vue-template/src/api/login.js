import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/sign-in',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/user-info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/sign-out',
    method: 'post'
  })
}

export function signup(data) {
  return request({
    url: '/signup',
    method: 'post',
    data
  })
}
// todo
export function getUserProfile(params) {
  return request({
    url: '/profile',
    method: 'get',
    params
  })
}

export function updateUserProfile(params) {
  return request({
    url: '/profile',
    method: 'get',
    params
  })
}

export function updateUserPwd(params) {
  return request({
    url: '/profile',
    method: 'get',
    params
  })
}
export function getRouters(params) {
  return request({
    url: '/profile',
    method: 'get',
    params
  })
}

export function uploadAvatar(params) {
  return request({
    url: '/profile',
    method: 'get',
    params
  })
}

