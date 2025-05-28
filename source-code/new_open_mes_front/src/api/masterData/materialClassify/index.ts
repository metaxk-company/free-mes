import request from '@/config/axios'

// 查询分类列表
export const queryClassList = async (params?) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/itemType/list`,
    params
  })
}

// 查询分类详细
export const queryClassifyInfoAPI = async (itemTypeId) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/itemType/get/${itemTypeId}`
  })
}

// 删除分类详细
export const deleteClassInfo = async (itemTypeId) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/itemType/${itemTypeId}`
  })
}

// 新增分类
export const addClass = async (data) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/itemType/save`,
    data
  })
}

// 修改分类详细
export const updateClassInfo = async (data) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/itemType/update`,
    data
  })
}

// 查询分类列表（排除节点）
export const queryClassNode = async (itemTypeId) => {
  return await request.get({
    url: `/mes/md/itemtype/list/exclude/${itemTypeId}`
  })
}
