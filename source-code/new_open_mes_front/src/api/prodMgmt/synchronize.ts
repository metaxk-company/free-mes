import request from '@/config/axios'

// 同步订单
export const syncDataInfo = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/synchronous`
  })
}

// 查询同步订单列表
export const syncTableList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/synchronizeOrdersList`,
    params: query
  })
}
