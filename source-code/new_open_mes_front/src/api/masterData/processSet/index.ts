import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/list`,
    params
  })
}

// 新增生产工序
export const addProcessData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/save`,
    data: params
  })
}

// 查询生产工序详细
export const queryProcessInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/get/${params}`
  })
}

// 修改生产工序
export const updateProcessInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/update`,
    data: params
  })
}

// 删除生产工序
export const deleteProcessInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/batch`,
    data: params
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/export`
  })
}
