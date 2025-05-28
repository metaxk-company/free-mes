import request from '@/config/axios'

// 报工设备列表
export const getReportWorkList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/fed/equipment/list`,
    params
  })
}

// 操作状态
export const setStateValue = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/fed/equipment/updateStatus`,
    data: params
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/fed/equipment/export`
  })
}

// 查看单个报工设备信息
export const getReportWorkListInfo = async (query: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/fed/equipment/get/${query}`
  })
}
