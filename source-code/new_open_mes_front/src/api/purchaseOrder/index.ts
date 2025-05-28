import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/receipt/list`,
    params
  })
}

// 新增
export const addFormData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/receipt/save`,
    data: params
  })
}

// 查询
export const queryFormInfo = async (params: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/receipt/find/${params}`
  })
}

// 修改
export const updateFormInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/receipt/update`,
    data: params
  })
}

// 删除
export const deleteFormInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/receipt/batch`,
    data: params
  })
}

//  供应商列表
export const getVendorSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/list`
  })
}
//新供应商列表
export const getVendorSelectData = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/listAll`
  })
}
// 产品类别列表
export const productClassList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dict/data/listKind`
  })
}

// 自动生成
export const getCreateCode = async (query: string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/system/autocode/get/${query}`
  })
}

// 采购订单列表数据
export const purchaseOrderList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/list`,
    params
  })
}

// 打印
export const getPrintingData = async (params?: object) => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/report/purchaseOrder/print`,
    params
  })
}

export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/receipt/export`
  })
}

export const addPartialArrival = async (params?: object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/wh/rec/bill/receiveSome`,
    data: params
  })
}
// 全部到货接口
export const addAllPartialArrival = async (params?: object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/wh/rec/bill/receiveAll`,
    data: params
  })
}
