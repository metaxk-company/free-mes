import request from '@/config/axios'

// 订单总数
export const getWorkerOrderAll = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/pro/workorder/findWorkerOrderAll`
  })
}

// 未开工订单
export const getStartOrders = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/pro/feedback/unStartOrders`
  })
}

// 已报工订单
export const getCompleted = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/pro/feedback/completed`
  })
}

// 设备状态数据
export const getFindStatus = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/dv/machinery/findStatus`
  })
}
