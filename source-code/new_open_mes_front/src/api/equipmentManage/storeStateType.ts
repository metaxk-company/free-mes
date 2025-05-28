import request from '@/config/axios'

// 状态管理类型列表
export const queryTypeStateList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/status/list`,
    params
  })
}

// 状态管理类型添加
export const addTypeState = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/status/save`,
    data: params
  })
}

// 状态管理类型修改
export const upDateTypeStateInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/status/update`,
    data: params
  })
}

// 查询状态管理类型
export const getTypeStateInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/status/get/${params}`
  })
}

// 状态管理类型删除
export const deleteTypeStateInfo = async (params: Object) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/status/batch`,
    data: params
  })
}
