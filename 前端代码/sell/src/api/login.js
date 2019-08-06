import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/seller/user/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo() {
  return request({
    url: '/admin/info',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/seller/user/logout',
    method: 'post'
  })
}
