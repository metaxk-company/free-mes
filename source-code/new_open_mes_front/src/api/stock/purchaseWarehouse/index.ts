import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/other/inbound/list`,
    params
  })
}

// 新增
export const addFormData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/other/inbound/save`,
    data: params
  })
}

// 查询
export const queryFormInfo = async (params: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/other/inbound/find/${params}`
  })
}

// 修改
export const updateFormInfo = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/other/inbound/update`,
    data: params
  })
}

// 删除
export const deleteFormInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/other/inbound/batch`,
    data: params
  })
}

// 导出
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/export`
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
export const getPurchaseOrderList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/inbound/receiptList`,
    params
  })
}
// 打印
export const getPrintingData = async (params?: object) => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/report/purchaseInbound/print
`,
    params
  })
}
// 采购入库确认同步
export const confirmSync = async (params?: object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/sync/inBoundSync`,
    params
  })
}
// 供应商列表
export const getVendorList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/listAll`
  })
}
