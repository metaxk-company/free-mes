import request from '@/config/axios'

export interface MenuVO {
  id: number
  name: string
  permission: string
  type: number
  sort: number
  parentId: number
  path: string
  icon: string
  component: string
  componentName?: string
  status: number
  visible: boolean
  keepAlive: boolean
  alwaysShow?: boolean
  createTime: Date
}

export interface MenuPageReqVO {
  name?: string
  status?: number
}

// 查询菜单（精简）列表
export const listSimpleMenusApi = () => {
  return request.get({ url: import.meta.env.VITE_API_URL_ADMIN + '/system/menu/list-all-simple' })
}

// 查询菜单列表
export const getMenuListApi = (params: MenuPageReqVO) => {
  return request.get({ url: import.meta.env.VITE_API_URL_ADMIN + '/system/menu/list', params })
}

// 获取菜单详情
export const getMenuApi = (id: number) => {
  return request.get({ url: import.meta.env.VITE_API_URL_ADMIN + '/system/menu/get?id=' + id })
}

// 新增菜单
export const createMenuApi = (data: MenuVO) => {
  return request.post({ url: import.meta.env.VITE_API_URL_ADMIN + '/system/menu/create', data })
}

// 修改菜单
export const updateMenuApi = (data: MenuVO) => {
  return request.put({ url: import.meta.env.VITE_API_URL_ADMIN + '/system/menu/update', data })
}

// 删除菜单
export const deleteMenuApi = (id: number) => {
  return request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/menu/delete?id=' + id
  })
}
