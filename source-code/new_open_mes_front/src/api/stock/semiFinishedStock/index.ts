import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/semi/label/list
`,
    params
  })
}

// 新增
export const addFormData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/semi/label/save
`,
    data: params
  })
}

// 查询
export const queryFormInfo = async (params: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/semi/label/find/${params}`
  })
}

// 修改
export const updateFormInfo = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/semi/label/update`,
    data: params
  })
}

// 删除
export const deleteFormInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/semi/label/deleteBatch`,
    data: params
  })
}

export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/semi/label/export`
  })
}
