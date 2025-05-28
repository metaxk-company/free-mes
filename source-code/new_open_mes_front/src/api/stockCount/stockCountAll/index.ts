import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/statistics/list`,
    params
  })
}
