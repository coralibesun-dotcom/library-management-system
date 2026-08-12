/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 校验用户名是否有效
 * 只要求非空即可，不再限制固定用户名
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  return str && str.trim().length > 0
}
