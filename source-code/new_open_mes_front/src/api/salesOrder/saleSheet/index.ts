import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/list`,
    params
  })
}

// 查询表格参数（新）
export const querySaleTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/findSale`,
    params
  })
}

// 新增
export const addFormData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/save`,
    data: params
  })
}

// 查询
export const queryFormInfo = async (params: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/find/${params}`
  })
}

// 修改
export const updateFormInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/update`,
    data: params
  })
}

// 删除
export const deleteFormInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/batch`,
    data: params
  })
}

// 导出
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/export`
  })
}

// 查询客户下的销售单列表数据
export const queryClientSaleTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/customerName`,
    params
  })
}

// 查询客户下的地址
export const queryClientAddress = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/find/customerName`,
    params
  })
}

// 获取铜价/铝价的数据列表
export const getMaterialDataList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/price/findPrice`,
    params
  })
}

// 更改订单状态是否完成
export const upDateOrderState = async (params?: String) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/setStatus/${params}`
  })
}

// 查看当前订单进度
export const getOrderSchedule = async (params?: object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/sale/findProgress`,
    params
  })
}

// 打印
export const getPrintingData = async (params?: Object) => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/report/sale/print/`,
    params
  })
}

// 获取详情数据
export const queryDetailsTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/label/detailedInventory`,
    params
  })
}
