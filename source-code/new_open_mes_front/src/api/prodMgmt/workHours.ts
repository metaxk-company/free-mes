import request from '@/config/axios'

// 查询生产任务列表
export const getWorkHoursList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/hours/list`,
    params: query
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/hours/export`
  })
}
