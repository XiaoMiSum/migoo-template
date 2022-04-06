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

