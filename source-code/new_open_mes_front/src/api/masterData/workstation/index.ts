import request from '@/config/axios'
// workstation

// 查询工作站列表
export const queryWorkstationList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstation/list`,
    params: query
  })
}

// 获取车间下拉列表
export const queryWorkshopList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/listAll`
  })
}

// 获取所属工序下拉列表
export const queryProcessList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/availableList`
  })
}

// 新增工作站
export const addWorkstationInfo = async (query: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstation/save`,
    data: query
  })
}

// 修改工作站
export const upDateWorkstation = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstation/update`,
    data: query
  })
}

// 查询工作站详细
export const queryWorkstationInfo = async (query: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstation/get/${query}`
  })
}

// 工作站资源 - 设备资源列表查询
export const queryFacilityList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstationMachine/list`,
    params: query
  })
}

// 工作站资源 - 设备资源列表新增
export const addFacilityList = async (query: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstationMachine/save`,
    data: query
  })
}

// 工作站资源 - 设备资源列表删除
export const deleteFacilityList = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstationMachine/batch`,
    data: query
  })
}

// 查询设备类型列表 - tree 结构
export const queryDeviceTypeTree = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machineryType/list`
  })
}

// 查询设备类型列表
export const queryDeviceType = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dv/machinery/list`,
    params: query
  })
}

// 删除工作站
export const deleteWorkstationInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstation/batch`,
    data: query
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstation/export`
  })
}
