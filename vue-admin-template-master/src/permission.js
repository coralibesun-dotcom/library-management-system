import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login', '/register'] // no redirect whitelist

/**
 * 鉴权：检查当前用户角色能否访问目标路由
 * 规则：to.matched 链路上每一个标了 meta.roles 的记录，用户角色都必须命中至少一个
 *       没标 meta.roles 的记录 = 不限制 = 人人可进
 * 注意：roles 标在父路由（如 /category），子路由（/category/index）靠 matched 继承
 */
function hasPermission(roles, to) {
  return to.matched.every(record => {
    if (!record.meta || !record.meta.roles) return true
    return roles.some(role => record.meta.roles.includes(role))
  })
}

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        // 已有用户信息，直接做角色校验
        if (hasPermission(store.getters.roles, to)) {
          next()
        } else {
          // 角色不匹配，提示并跳首页
          Message.error('无权访问该页面')
          next('/')
          NProgress.done()
        }
      } else {
        try {
          // get user info
          await store.dispatch('user/getInfo')

          // 拿到用户信息后再校验角色（roles 在 getInfo 后才有）
          if (hasPermission(store.getters.roles, to)) {
            next()
          } else {
            Message.error('无权访问该页面')
            next('/')
            NProgress.done()
          }
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
