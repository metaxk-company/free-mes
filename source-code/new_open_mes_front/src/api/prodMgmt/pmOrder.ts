import request from '@/config/axios'

// 生产订单表格数据
export const getListWorkOrder = async (params: Object) => {
  return request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/list`,
    params
  })
}

// 新增生产订单
export const addWorkOrder = async (params: Object) => {
  return request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/save`,
    data: params
  })
}

// 自动生成订单编号
export const createWordOrder = async (params: String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/system/autocode/get/${params}`
  })
}

// 查询部门下拉树结构
export const queryTreeSelect = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/itemType/treeSelect`
  })
}

// 查询物料列表
export const queryMDItemList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/list`,
    params: query
  })
}

// 查询客户列表
export const queryClientList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/client/list`,
    params: query
  })
}

// 获取BOM组成表格数据
export const getBOMDataList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/bom/list`,
    params: query
  })
}

// 获取物料需求表格数据
export const getMaterialDataList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/listItems`,
    params: query
  })
}

// 修改生产订单
export const upDataWorkOrder = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/update`,
    data: query
  })
}

// 查询生产订单详细
export const getWorkOrderInfo = async (query: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/get/${query}`
  })
}

// 删除生产订单
export const deleteWorkOrderId = async (query) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/delete`,
    data: query
  })
}

// 查询打印订单信息
export const queryPrintInfo = async (query: String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/findTaskOrder/${query}`
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/export`
  })
}

// 查看任务
export const getQueryTask = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/workorder/findFeedBackByWorkOrderCode`,
    params
  })
}

// 更新排产
export const updateDataScheduling = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/orderAddition`,
    data: params
  })
}

// 获取甘特图数据
export const getGanttData = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/listGanttTaskList`
  })
}

// 获取Gantt图的订单详细
export const getGanttDataInfo = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/pro/task/findDetails`,
    params
  })
}
