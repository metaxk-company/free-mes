import request from '@/config/axios'

export interface ConfigVO {
  id: number | undefined
  category: string
  name: string
  key: string
  value: string
  type: number
  visible: boolean
  remark: string
  createTime: Date
}

export interface ConfigExportReqVO {
  name?: string
  key?: string
  type?: number
  createTime?: Date[]
}

// 查询参数列表
export const getConfigPage = (params: PageParam) => {
  return request.get({ url: import.meta.env.VITE_API_URL_ADMIN + '/infra/config/page', params })
}

// 查询参数详情
export const getConfig = (id: number) => {
  return request.get({ url: import.meta.env.VITE_API_URL_ADMIN + '/infra/config/get?id=' + id })
}

// 根据参数键名查询参数值
export const getConfigKey = (configKey: string) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/config/get-value-by-key?key=' + configKey
  })
}

// 新增参数
export const createConfig = (data: ConfigVO) => {
  return request.post({ url: import.meta.env.VITE_API_URL_ADMIN + '/infra/config/create', data })
}

// 修改参数
export const updateConfig = (data: ConfigVO) => {
  return request.put({ url: import.meta.env.VITE_API_URL_ADMIN + '/infra/config/update', data })
}

// 删除参数
export const deleteConfig = (id: number) => {
  return request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/config/delete?id=' + id
  })
}

// 导出参数
export const exportConfigApi = (params: ConfigExportReqVO) => {
  return request.download({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/config/export',
    params
  })
}
