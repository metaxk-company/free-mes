import request from '@/config/axios'

// 社交绑定，使用 code 授权码
export const socialBind = (type, code, state) => {
  return request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/social-user/bind',
    data: {
      type,
      code,
      state
    }
  })
}

// 取消社交绑定
export const socialUnbind = (type, openid) => {
  return request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/social-user/unbind',
    data: {
      type,
      openid
    }
  })
}

// 社交授权的跳转
export const socialAuthRedirect = (type, redirectUri) => {
  return request.get({
    url:
      import.meta.env.VITE_API_URL_ADMIN +
      '/system/auth/social-auth-redirect?type=' +
      type +
      '&redirectUri=' +
      redirectUri
  })
}
