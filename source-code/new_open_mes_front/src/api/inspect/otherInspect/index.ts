import request from '@/config/axios'

// 查询检验标准列表
export const getOtherRecordList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/other/record/list`,
    params
  })
}
// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/other/record/exportExcel`
  })
}
// 新增
export const addFormData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/other/record/save`,
    data: params
  })
}
// 检验方式生成编码接口
export const getOtherInspectCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}
// 选择生产订单编号
export const getWorkOrder = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/pro/workorder/list`,
    params
  })
}
// 新增信息
export const addFormList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/other/record/show`,
    params
  })
}
// 查询检测器具详细
export const queryOtherInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/other/record/find/${params}`
  })
}

// 修改检测器具
export const upDateOtherInfo = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/other/record/update`,
    data: params
  })
}
