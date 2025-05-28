import request from '@/config/axios'

// 生成检测器具编码接口
export const getUtensilGenCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 查询检测器具列表
export const getUtensilList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/device/list`,
    params
  })
}

// 新增检测器具
export const addUtensil = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/device/save`,
    data: params
  })
}

// 查询检测器具详细
export const queryUtensilInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/device/find/${params}`
  })
}

// 修改检测器具
export const upDateUtensilInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/device/update`,
    data: params
  })
}

// 删除检测器具
export const deleteUtensilInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/device/batch`,
    data: query
  })
}

// 获取所属车间列表
export const getWorkshopList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/availableList`
  })
}

// 获取所属部门列表
export const getDeptList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/system/dept/findList`
  })
}

// 查询所有有效生产工序
export const queryCraftListAll = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/availableList`
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/device/importExcel`
  })
}

// 导入
export const downloadTemplate = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/device/templateData`
  })
}
