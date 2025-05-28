import request from '@/config/axios'

export interface PermissionAssignUserRoleReqVO {
  userId: number
  roleIds: number[]
}

export interface PermissionAssignRoleMenuReqVO {
  roleId: number
  menuIds: number[]
}

export interface PermissionAssignRoleDataScopeReqVO {
  roleId: number
  dataScope: number
  dataScopeDeptIds: number[]
}

// 查询角色拥有的菜单权限
export const listRoleMenusApi = async (roleId: number) => {
  return await request.get({
    url:
      import.meta.env.VITE_API_URL_ADMIN + '/system/permission/list-role-resources?roleId=' + roleId
  })
}

// 赋予角色菜单权限
export const assignRoleMenuApi = async (data: PermissionAssignRoleMenuReqVO) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/permission/assign-role-menu',
    data
  })
}

// 赋予角色数据权限
export const assignRoleDataScopeApi = async (data: PermissionAssignRoleDataScopeReqVO) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/permission/assign-role-data-scope',
    data
  })
}

// 查询用户拥有的角色数组
export const listUserRolesApi = async (userId: number) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/permission/list-user-roles?userId=' + userId
  })
}

// 赋予用户角色
export const aassignUserRoleApi = async (data: PermissionAssignUserRoleReqVO) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/permission/assign-user-role',
    data
  })
}
