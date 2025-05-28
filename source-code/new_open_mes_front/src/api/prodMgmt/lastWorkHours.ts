import request from '@/config/axios'

// 临时工时表格数据
export const getLastWorkHoursList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/temporary/workHours/list`,
    params
  })
}

// 新增临时工时
export const addLastWorkHours = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/temporary/workHours/save`,
    data: params
  })
}

// 临时工时详情
export const queryLastWorkHoursInfo = async (query: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/temporary/workHours/get/${query}`
  })
}

// 临时工时修改
export const upDataLastWorkHours = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/temporary/workHours/update`,
    data: query
  })
}

// 临时工时删除
export const deleteLastWorkHours = async (query) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/temporary/workHours/batch`,
    data: query
  })
}

// 导出功能
export const downloadListData = async (query) => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/temporary/workHours/export?ids=${query}`
  })
}
