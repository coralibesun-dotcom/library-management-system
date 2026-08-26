import request from '@/utils/request'

// 首页统计数据
export function getStats() {
  return request({
    url: '/stats',
    method: 'get'
  })
}
