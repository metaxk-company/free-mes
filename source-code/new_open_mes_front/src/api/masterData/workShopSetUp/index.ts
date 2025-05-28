import request from '@/config/axios'

// 查询车间列表
export const queryWorkshopList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/list`,
    params: query
  })
}

// 获取车间拉下列表参数
export const getWorkshopSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/listAll`
  })
}

// 新增车间
export const addWorkshop = async (query: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/save`,
    data: query
  })
}

// 查询车间详细
export const queryWorkshopInfo = async (query: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/get/${query}`
  })
}

// 修改车间
export const upDateWorkshopInfo = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/update`,
    data: query
  })
}

// 删除车间
export const deleteWorkshop = async (query: any) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/batch`,
    data: query
  })
}
