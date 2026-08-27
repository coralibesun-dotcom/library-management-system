import request from '@/utils/request'

export function borrowBook(data) {
  return request({
    url: '/borrow',
    method: 'post',
    data
  })
}

export function returnBook(id) {
  return request({
    url: '/borrow/return/' + id,
    method: 'put'
  })
}

export function getMyBorrows() {
  return request({
    url: '/borrow/my',
    method: 'get'
  })
}

export function getAllBorrows() {
  return request({
    url: '/borrow/all',
    method: 'get'
  })
}

export function getBorrowPage(params) {
  return request({
    url: '/borrow/page',
    method: 'get',
    params
  })
}

