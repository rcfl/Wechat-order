import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/categoryHistory/list',
    method:'get',
    params:params
  })
}
