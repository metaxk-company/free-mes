import request from '@/config/axios'

// 查询设备类型列表
export const queryFacilityTypeList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machineryType/list`,
    params
  })
}

// 新增设备类型
export const addFacilityType = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machineryType/save`,
    data: params
  })
}

// 查询设备类型详细
export const queryFacilityType = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machineryType/get/${params}`
  })
}

// 修改查询设备类型详细
export const upDateFacilityType = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machineryType/update`,
    data: params
  })
}

// 删除设备类型
export const deleteFacilityType = async (params: Number | String) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machineryType/${params}`
  })
}
