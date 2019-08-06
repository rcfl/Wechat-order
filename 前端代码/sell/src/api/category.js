import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/seller/category/list',
    method:'get',
    params:params
  })
}

export function createCoupon(data) {
  return request({
    url:'seller/category/save',
    method:'post',
    data:data
  })
}

export function getCoupon(categoryId) {
  return request({
    url:'seller/category/index/'+ categoryId,
    method:'get',
  })
}

export function updateCoupon(categoryId, data) {
  return request({
    url:'seller/category/save',
    method:'post',
    data:data
  })
}

export function deleteCoupon(id) {
  return request({
    url:'/category/delete/'+id,
    method:'post',
  })
}
