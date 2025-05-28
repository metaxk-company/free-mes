import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/route/list`,
    params
  })
}

// 新增工艺流程
export const addCraftCourseData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/route/save`,
    data: params
  })
}

// 查询工艺流程详细
export const queryCraftCourseInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/route/get/${params}`
  })
}

// 修改工艺流程
export const updateCraftCourseInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/route/update`,
    data: params
  })
}

// 删除工艺流程
export const deleteCraftCourseInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/route/batch`,
    data: params
  })
}

// 查询工艺组成列表
export const queryProcessTableList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProcess/list`,
    params
  })
}

// 查询所有有效生产工序
export const queryCraftListAll = (params?: Object) => {
  return request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/list`,
    params
  })
}

// 新增工艺组成
export const addCraft = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProcess/save`,
    data: params
  })
}

// 修改工艺组成
export const updateCraft = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProcess/update`,
    data: params
  })
}

// 查询工艺组成详细
export const queryCraftInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProcess/get/${params}`
  })
}

// 删除工艺组成
export const deleteCraft = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProcess/batch`,
    data: params
  })
}

// 查询产品制程列表
export const queryProductTableList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProduct/list`,
    params
  })
}

// 新增产品制程
export const addProductData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProduct/save`,
    data: params
  })
}

// 修改产品制程
export const updateProductInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProduct/update`,
    data: params
  })
}

// 查看产品制程
export const queryProductInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProduct/get/${params}`
  })
}

// 删除产品制程
export const deleteProduct = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProduct/batch`,
    data: params
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/route/export`
  })
}
