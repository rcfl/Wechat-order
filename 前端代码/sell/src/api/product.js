import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url:'/seller/product/list',
    method:'get',
    params:params
  })
}

export function fetchSimpleList(params) {
  return request({
    url:'/seller/product/simpleList',
    method:'get',
    params:params
  })
}

export function updateDeleteStatus(params) {
  return request({
    url:'/product/update/deleteStatus',
    method:'post',
    params:params
  })
}

export function updateNewStatus(params) {
  return request({
    url:'/product/update/newStatus',
    method:'post',
    params:params
  })
}

export function updateRecommendStatus(params) {
  return request({
    url:'/product/update/recommendStatus',
    method:'post',
    params:params
  })
}

export function updatePublishStatus(params) {
  return request({
    url:'seller/product/on_sale',
    // method:'post',
    method:'get',
    params:params
  })
}

export function createProduct(data) {
  return request({
    url:'seller/product/save',
    method:'post',
    data:data
  })
}

export function updateProduct(productId,data) {
  return request({
    url:'seller/product/save/',
    method:'post',
    data:data
  })
}

export function getProduct(productId) {
  return request({
    url:'seller/product/productInfo/'+ productId,
    method:'get',
  })
}

