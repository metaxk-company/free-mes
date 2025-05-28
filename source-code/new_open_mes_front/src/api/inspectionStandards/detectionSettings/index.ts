import request from '@/config/axios'

// 检验设置生成编码接口
export const getTestSettingCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 查询检验设置列表
export const getTestSettingList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/list`,
    params
  })
}

// 新增检验设置
export const addTestSetting = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/save`,
    data: params
  })
}

// 查询检验设置详细
export const queryTestSettingInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/find/${params}`
  })
}

// 修改检验方式
export const upDateTestSettingInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/update`,
    data: params
  })
}

// 删除检验设置
export const deleteTestSettingInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/batch`,
    data: query
  })
}

// 删除检测器具
export const deleteTestEquipment = async (query: String | Number, value: any) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/remove/${query}/${value}`
  })
}
