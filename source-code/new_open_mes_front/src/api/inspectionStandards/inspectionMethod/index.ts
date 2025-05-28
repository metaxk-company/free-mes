import request from '@/config/axios'

// 检验方式生成编码接口
export const getTestWayGenCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 查询检验方式列表
export const getTestWayList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/way/list`,
    params
  })
}

// 新增检验方式
export const addTestWay = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/way/save`,
    data: params
  })
}

// 查询检验方式详细
export const queryTestWayInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/way/find/${params}`
  })
}

// 修改检验方式
export const upDateTestWayInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/way/update`,
    data: params
  })
}

// 删除检验方式
export const deleteTestWayInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/way/batch`,
    data: query
  })
}
