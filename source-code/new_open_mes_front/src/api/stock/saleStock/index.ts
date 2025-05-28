import request from '@/config/axios'

//   查询有销售单表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/list`,
    params
  })
}

// 查询无销售单表格参数
export const queryNoSaleTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/noSale/list`,
    params
  })
}

// 新增
export const addFormData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/save`,
    data: params
  })
}

// 查询
export const queryFormInfo = async (params: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/find/${params}`
  })
}

// 修改
export const updateFormInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/update`,
    data: params
  })
}

// 删除
export const deleteFormInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/batch`,
    data: params
  })
}

// 导出
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/export`
  })
}

//  类别数据字典列表
export const getKindSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dict/data/listKind`
  })
}

// 生成编号
export const getCreateCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 获取采购订单列表
export const getPurchaseOrderList = async (params: object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/findSaleItem`,
    params
  })
}

// 获取单个采购订单详情明细
export const getPurchaseOrderDetailInfo = async (params: object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/findItem`,
    params
  })
}

// 获取入库详情数据
export const getStorageDetailsList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/findLabel`,
    params
  })
}

// 通过单个详情获取选择列表数据
export const getSingleDetailsList = async (params?: string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/findLabel?model=${params}`,
    params
  })
}

// 保存单个下详情数据明细
export const saveSingleDetailData = async (params?: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/sale/detail/save`,
    data: params
  })
}

// 删除当前明细下选择行的数据
export const deleteRowDelete = async (id, number) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/remove/${id}/${number}`
  })
}

// 确认出库
export const confirmWarehouse = async (params?: string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/outBound/${params}`
  })
}

// 退库
export const confirmRetreatWarehouse = async (params?: string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/stockReturn/${params}`
  })
}

// 删除出库的选择行的数据
export const deleteRowWarehouseDelete = async (number) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/remove/${number}`
  })
}

// 打印测试
export const getPrintingTest = async (params) => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/report/outbound/print`,
    params
  })
}
//添加采购
export const addPurchase = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/outbound/findSaleOrderItem`,
    params
  })
}
//确认同步
export const confirmSync = async (params) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/sync/saleOutBound`,
    params
  })
}
