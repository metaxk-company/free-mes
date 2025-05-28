import request from '@/config/axios'

export interface PostVO {
  id?: number
  name: string
  code: string
  sort: number
  status: number
  remark: string
  createTime?: Date
}

export interface PostPageReqVO extends PageParam {
  code?: string
  name?: string
  status?: number
}

export interface PostExportReqVO {
  code?: string
  name?: string
  status?: number
}

// 查询岗位列表
export const getPostPageApi = async (params: PostPageReqVO) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/post/page',
    params
  })
}

// 获取岗位精简信息列表
export const listSimplePostsApi = async () => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/post/list-all-simple'
  })
}

// 查询岗位详情
export const getPostApi = async (id: number) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/post/get?id=' + id
  })
}

// 新增岗位
export const createPostApi = async (data: PostVO) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/post/create',
    data
  })
}

// 修改岗位
export const updatePostApi = async (data: PostVO) => {
  return await request.put({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/post/update',
    data
  })
}

// 删除岗位
export const deletePostApi = async (id: number) => {
  return await request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/post/delete?id=' + id
  })
}

// 导出岗位
export const exportPostApi = async (params: PostExportReqVO) => {
  return await request.download({
    url: import.meta.env.VITE_API_URL_ADMIN + '/system/post/export',
    params
  })
}
