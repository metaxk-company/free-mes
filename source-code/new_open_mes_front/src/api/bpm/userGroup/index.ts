import request from '@/config/axios'

export type UserGroupVO = {
  id: number
  name: string
  description: string
  memberUserIds: number[]
  status: number
  remark: string
  createTime: string
}

// 创建用户组
export const createUserGroupApi = async (data: UserGroupVO) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/user-group/create',
    data: data
  })
}

// 更新用户组
export const updateUserGroupApi = async (data: UserGroupVO) => {
  return await request.put({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/user-group/update',
    data: data
  })
}

// 删除用户组
export const deleteUserGroupApi = async (id: number) => {
  return await request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/user-group/delete?id=' + id
  })
}

// 获得用户组
export const getUserGroupApi = async (id: number) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/user-group/get?id=' + id
  })
}

// 获得用户组分页
export const getUserGroupPageApi = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/user-group/page',
    params
  })
}

// 获取用户组精简信息列表
export const listSimpleUserGroupsApi = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/user-group/list-all-simple'
  })
}
