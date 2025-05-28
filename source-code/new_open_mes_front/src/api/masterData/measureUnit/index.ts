import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/list`,
    params
  })
}

// 主单位下拉参数
export const queryUnitLits = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/list`
  })
}

// 新增单位
export const addUnitData = async (params) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/save`,
    data: params
  })
}

// 查询单位列表
export const queryUnitList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/list`
  })
}

// 修改单位
export const updateUnitData = async (params) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/update`,
    data: params
  })
}

// 获取单个表格信息
export const getUnitDetail = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/get/${params}`
  })
}

// 删除单位
export const deleteUnitData = async (params) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/batch`,
    data: params
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/export`
  })
}
