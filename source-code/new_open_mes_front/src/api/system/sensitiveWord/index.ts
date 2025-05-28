import request from '@/config/axios'

export interface SensitiveWordVO {
  id: number
  name: string
  status: number
  description: string
  tags: string[]
  createTime: Date
}

export interface SensitiveWordPageReqVO extends PageParam {
  name?: string
  tag?: string
  status?: number
  createTime?: Date[]
}

export interface SensitiveWordExportReqVO {
  name?: string
  tag?: string
  status?: number
  createTime?: Date[]
}

// 查询敏感词列表
export const getSensitiveWordPageApi = (params: SensitiveWordPageReqVO) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/page',
    params
  })
}

// 查询敏感词详情
export const getSensitiveWordApi = (id: number) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/get?id=' + id
  })
}

// 新增敏感词
export const createSensitiveWordApi = (data: SensitiveWordVO) => {
  return request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/create',
    data
  })
}

// 修改敏感词
export const updateSensitiveWordApi = (data: SensitiveWordVO) => {
  return request.put({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/update',
    data
  })
}

// 删除敏感词
export const deleteSensitiveWordApi = (id: number) => {
  return request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/delete?id=' + id
  })
}

// 导出敏感词
export const exportSensitiveWordApi = (params: SensitiveWordExportReqVO) => {
  return request.download({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/export-excel',
    params
  })
}

// 获取所有敏感词的标签数组
export const getSensitiveWordTagsApi = () => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/get-tags'
  })
}

// 获得文本所包含的不合法的敏感词数组
export const validateTextApi = (id: number) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/sensitive-word/validate-text?' + id
  })
}
