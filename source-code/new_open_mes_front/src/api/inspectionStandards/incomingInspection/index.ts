import request from '@/config/axios'
// 新增检验标准
export const addTestStandard = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/receive/standard/save`,
    data: params
  })
}
// 查询到货单号
export const queryCraftListAll = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/list`,
    params
  })
}
// 来料检验首页列表
export const getTestStandardList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/receive/standard/list`,
    params
  })
}
// 删除检验标准
export const deleteTestStandardInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/receive/standard/batch`,
    data: query
  })
}
// 生成检验标准接口
export const getTestStandardCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 查询检验标准详细
export const queryTestStandardInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/receive/standard/find/${params}`
  })
}

// 修改检验标准
export const upDateTestStandardInfo = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/receive/standard/update`,
    data: params
  })
}

// 删除检测项目当前行数据
export const deleteTestItemInfo = async (query: Number | String, value: Number | String) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/qc/inspect/standard/remove/${query}/${value}`
  })
}
