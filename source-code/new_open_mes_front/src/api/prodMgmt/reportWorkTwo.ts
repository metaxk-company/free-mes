import request from '@/config/axios'

// 查询生产报工2列表
export const getProductionList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/list`,
    params
  })
}

// 新增生产报工信息
export const addProductionInfo = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/insert`,
    data: params
  })
}

// 查询生产报公信息
export const queryProductionListInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/find/${params}`
  })
}

// 修改生产报公信息
export const upDateProductionListInfo = async (params: Number | String, data: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/updateFeedBack/${params}`,
    data
  })
}

// 工单状态（暂停、恢复）
export const setWorkOrderStatus = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/updateStatus`,
    data: params
  })
}

// 删除生产报公信息
export const deleteProductionListInfo = async (params: Number | String) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/delete/${params}`
  })
}

// 获取暂停原因数据
export const getStopReason = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dict/data/listData`
  })
}

// 获取未开工订单数量
export const getQuantityNotStarted = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/unStartOrders`
  })
}

// 已报工订单数量
export const getQuantityCompleted = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/completed`
  })
}

// 已暂停订单数量
export const getQuantityPaused = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/feedback/paused`
  })
}
