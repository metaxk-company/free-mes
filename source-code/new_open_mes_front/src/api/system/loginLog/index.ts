import request from '@/config/axios'

export interface LoginLogVO {
  id: number
  logType: number
  traceId: number
  userId: number
  userType: number
  username: string
  status: number
  userIp: string
  userAgent: string
  createTime: Date
}

export interface LoginLogReqVO extends PageParam {
  userIp?: string
  username?: string
  status?: boolean
  createTime?: Date[]
}

// 查询登录日志列表
export const getLoginLogPageApi = (params: LoginLogReqVO) => {
  return request.get({ url: import.meta.env.VITE_API_URL_ADMIN + '/system/login-log/page', params })
}
// 导出登录日志
export const exportLoginLogApi = (params: LoginLogReqVO) => {
  return request.download({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/login-log/export',
    params
  })
}
