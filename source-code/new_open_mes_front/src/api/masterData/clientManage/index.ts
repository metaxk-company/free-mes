import request from '@/config/axios'

// 生成客户端代码
export const getGenCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 删除客户
export const deleteClient = async (clientCode: string | string) => {
  return await request.delete({
    url: `/mes/md/client/${clientCode}`
  })
}

// 查询客户列表
export const getListClientList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/client/list`,
    params
  })
}

// 添加客户
export const addClient = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/client/save`,
    data: params
  })
}

// 查询客户详细
export const queryClientInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/client/get/${params}`
  })
}

// 修改客户
export const upDateClientInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/client/update`,
    data: params
  })
}

// 删除客户
export const deleteClientInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/client/batch`,
    data: query
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/client/export`
  })
}
