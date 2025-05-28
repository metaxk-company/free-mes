import request from '@/config/axios'

// 班组类型新增
export const saveClassTypeData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/type/save`,
    data: params
  })
}

// 批量删除班组类型
export const deleteBatchClassTypeData = async (params: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/type/batch`,
    data: params
  })
}

// 更新班组类型
export const updateClassTypeData = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/type/update`,
    data: params
  })
}

// 获取班组类型列表
export const getClassTypeDataList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/type/list`,
    params
  })
}

// 获取班组类型详情
export const getClassTypeDetail = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/type/get/${params}`
  })
}

// 班组类型导出
export const exportClassType = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/type/export`
  })
}

// 自动生成班组编号
export const generateTypeCode = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/system/autocode/get/CAL_TEAM_CODE`
  })
}
