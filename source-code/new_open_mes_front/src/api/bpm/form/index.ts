import request from '@/config/axios'

export type FormVO = {
  id: number
  name: string
  conf: string
  fields: string[]
  status: number
  remark: string
  createTime: string
}

// 创建工作流的表单定义
export const createFormApi = async (data: FormVO) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/form/create',
    data: data
  })
}

// 更新工作流的表单定义
export const updateFormApi = async (data: FormVO) => {
  return await request.put({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/form/update',
    data: data
  })
}

// 删除工作流的表单定义
export const deleteFormApi = async (id: number) => {
  return await request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/form/delete?id=' + id
  })
}

// 获得工作流的表单定义
export const getFormApi = async (id: number) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/form/get?id=' + id
  })
}

// 获得工作流的表单定义分页
export const getFormPageApi = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/form/page',
    params
  })
}

// 获得动态表单的精简列表
export const getSimpleFormsApi = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/form/list-all-simple'
  })
}
