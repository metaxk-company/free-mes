import request from '@/config/axios'

// 查询生产任务列表
export const queryTaskList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/listByConditional`,
    params: query
  })
}

// 删除生产任务列表
export const deleteTaskList = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/batch`,
    data: query
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/listByConditional/export`
  })
}
