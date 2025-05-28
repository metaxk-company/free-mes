import request from '@/config/axios'
// 这个文件主要用来存放公共请求接口，以及数据字典的接口

// 生成编号
export const getCreateCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 数据字典 - 标准下拉列表数据
export const getStandDataList = async (params?: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}
// 同步金蝶   生产领料接口
export const syncProductPick = async (params) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/sync/syncProductPick`,
    params
  })
}
// 同步金蝶   同步半成品
export const syncSemiLabel = async (params) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/sync/syncSemiLabel`,
    params
  })
}

// 同步金蝶   批量同步半成品
export const batchSyncSemiLabel = async (params) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/sync/syncBatchSemiLabel`,
    data: params
  })
}
// 同步金蝶   同步成品
export const syncLabel = async (params) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/sync/syncLabel`,
    params
  })
}
// 同步金蝶   批量同步成品
export const batchSyncLabel = async (params) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/sync/syncBatchLabel`,
    data: params
  })
}

//确认同步
export const confirmSync = async (params) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/sync/saleOutBound`,
    params
  })
}

// 采购订单同步
export const purchaseOrderSync = async (params) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/sync/purchaseOrderSync`,
    params
  })
}
