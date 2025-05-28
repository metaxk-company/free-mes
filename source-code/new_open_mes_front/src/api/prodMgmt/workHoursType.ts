import request from '@/config/axios'

// 查询工时类型
export const getWorkHoursTypeList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workhours/list`,
    params: query
  })
}

// 修改
export const upDateWorkHoursType = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workhours/update`,
    data: params
  })
}

// 查询
export const queryWorkHoursType = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workhours/get/${params}`
  })
}

// 删除
export const deleteWorkHoursType = async (params: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workhours/batch`,
    data: params
  })
}

// 新增
export const addWorkHoursType = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workhours/save`,
    data: params
  })
}
