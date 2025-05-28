import request from '@/config/axios'

// 班组类型新增
export const saveShiftSetData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/save`,
    data: params
  })
}

// 批量删除班组类型
export const deleteBatchShiftSetData = async (params: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/batch`,
    data: params
  })
}

// 更新班组类型
export const updateShiftSetData = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/update`,
    data: params
  })
}

// 获取班组类型列表
export const getShiftSetDataList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/list`,
    params
  })
}

// 获取班组类型详情
export const getShiftSetDetail = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/get/${params}`
  })
}

// 班组类型导出
export const exportShiftSet = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/export`
  })
}

// 自动生成班组编号
export const generateTypeCode = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/system/autocode/get/TEAM_GROUPS_CODE`
  })
}

// 获取人员列表
export const getUserList = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/system/user/list`,
    params
  })
}

// 保存当前班组人员数据
export const saveTeamsPeople = async (params: Array<Object>) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/member/save`,
    data: params
  })
}

// 查看当前班组人员数据
export const getTeamsPeopleList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/member/find`,
    params
  })
}

// 选择班组并获取人员数据
export const getShiftPersonnelData = async (params: string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/team/findMember/${params}`
  })
}

// 移除人员
export const deletePeopleData = async (params) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cal/member/batch`,
    data: params
  })
}
