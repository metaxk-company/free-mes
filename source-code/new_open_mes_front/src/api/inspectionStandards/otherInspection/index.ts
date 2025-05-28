import request from '@/config/axios'

// 检验方式生成编码接口
export const getTestWayGenCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}
// 新增检验标准
export const inspectionStandard = async (params: Object) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + `/qc/other/standard/save`,
    data: params
  })
}
// 查询检验标准列表
export const getTestStandardList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/qc/other/standard/list`,
    params
  })
}

// 查询检验标准详细
export const queryTestStandardInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/qc/other/standard/find/${params}`
  })
}

// 修改检验标准
export const upDateTestStandardInfo = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/qc/other/standard/update`,
    data: params
  })
}
// 删除\批量删除
export const deleteTestStandardInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/qc/other/standard/batchDelete`,
    data: query
  })
}
// 线别
export const getLineTypeList = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/dict/data/listLineType`
  })
}

// 型号
export const getModelList = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/md/model/listAll`
  })
}
//规格
export const getSpecList = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/md/spec/listAll`
  })
}
// 盘号
export const getReelNumberList = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/md/panhao/listAll`
  })
}
//颜色
export const getColorList = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/md/color/listAll`
  })
}
// 检测器具
export const getDevice = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/mes/qc/device/listAll`
  })
}
