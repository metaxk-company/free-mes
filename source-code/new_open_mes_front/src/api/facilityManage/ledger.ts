import request from '@/config/axios'

// 生产订单表格数据
export const getListWorkOrder = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/list`,
    params
  })
}

// 新增设备
export const addMachinery = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/save`,
    data: params
  })
}

// 查询设备详细
export const getMachineryInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/get/${params}`
  })
}

// 修改设备
export const upDateMachineryInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/update`,
    data: params
  })
}

// 删除设备
export const deleteMachineryInfo = async (params: Object) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/batch`,
    data: params
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/export`
  })
}

// 状态管理类型列表
export const queryTypeStateList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/status/list`,
    params
  })
}

// 设备类型的导入
export const downloadTemplate = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/templateData`
  })
}
