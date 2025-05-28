import request from '@/config/axios'

// 查询生产排产列表
export const queryProductionList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/listByConditional`,
    params: query
  })
}

// 查询产品的工艺组成列表
export const getProductFormList = async (query: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/routeProcess/listProductProcess/${query}`
  })
}

// 查询设备列表
export const queryEquipmentList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/findWorkstationByCode`,
    params: query
  })
}

// 查询工作站列表
export const getListWorkstation = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workstation/list`,
    params: query
  })
}

// 查询所有有效生产工序
export const getListAllProcess = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/process/availableList`
  })
}

// 查询所有有效车间
export const getListAllWorkShop = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/workshop/listAll`
  })
}

// 新增生产任务
export const addProTask = async (query: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/save`,
    data: query
  })
}

// 查询生产任务列表
export const queryProTaskList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/list`,
    params: query
  })
}

// 查询生产任务详细
export const getProTaskInfo = async (query: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/get/${query}`
  })
}

// 修改生产任务
export const updateProTaskInfo = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/update`,
    data: query
  })
}

// 删除生产任务
export const deleteProTaskInfo = async (query: Object) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/batch`,
    data: query
  })
}

// 获取当前订单下所有的排产数据
export const getAllProduction = async (query: Object) => {
  return request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/findTaskByOrderCode`,
    params: query
  })
}

// 点击排产之前根据序号ID和产品编号查询对应的工序排产数量
export const getWorkSequenceNumAPI = async (query: object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/taskResidueQuantity`,
    params: query
  })
}
