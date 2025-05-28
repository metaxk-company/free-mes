import request from '@/config/axios'

// 排班计划新增
export const saveSchedulingPlanData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/save`,
    data: params
  })
}

// 批量删除班排班计划
export const deleteBatchSchedulingPlanData = async (params: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/batch`,
    data: params
  })
}

// 更新排班计划
export const updateSchedulingPlanData = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/update`,
    data: params
  })
}

// 获取排班计划列表
export const getSchedulingPlanDataList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/list`,
    params
  })
}

// 获取排班计划详情
export const getSchedulingPlanDetail = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/get/${params}`
  })
}

// 排班计划导出
export const exportSchedulingPlan = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/export`
  })
}

// 根据编号查询类型和名称
export const getNameOfType = async (params: String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/find/${params}`
  })
}

// 自动生成班组编号
export const generateTypeCode = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/system/autocode/get/CAL_PLAN_CODE`
  })
}

// 获取当前排班人员列表数据
export const getPeopleListData = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/findClassPlanMember`,
    params
  })
}

// 删除当前排班人员
export const deletePeopleAPI = async (params: number | string) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/removePlanMember/${params}`
  })
}

// 提交修改的排班人员
export const editPeopleInfoAPI = async (params: Array<any>) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/updateClassPlanMember`,
    data: params
  })
}

// 获取当前班组任务数量
export const getFindTaskQuantityAPI = async (params: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/plan/findTaskList/${params}`
  })
}
