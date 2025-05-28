import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/statistics/saleOrderCountList`,
    params
  })
}

// 查询表格参数明细查询
export const queryTableListInfo = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/statistics/findCountOutBound`,
    params
  })
}
