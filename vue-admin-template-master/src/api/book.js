import request from '@/utils/request'

// 1. 分页查询图书（GET，参数走 params）
export function getBookPage(params) {
  return request({
    url: '/book/page',
    method: 'get',
    params
  })
}

// 2. 根据 id 查询详情 → getBookById(id)   ← 你来写
export function getBookById(id) {
  return request({
    url: '/book/' + id,
    method: 'get'
  })
}
// 3. 新增图书 → addBook(data)             ← 你来写
export function addBook(data) {
  return request({
    url: '/book',
    method: 'post',
    data
  })
}
// 4. 修改图书 → updateBook(data)          ← 你来写
export function updateBook(data) {
  return request({
    url: '/book',
    method: 'put',
    data
  })
}
// 5. 修改状态 → updateBookStatus(params)  ← 你来写
export function updateBookStatus(params) {
  return request({
    url: '/book/status',
    method: 'put',
    params
  })
}
