import request from '@/config/axios'

// 查询供应商列表
export const queryVendorList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/list`,
    params: query
  })
}

// 新增供应商
export const addVendor = async (query: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/save`,
    data: query
  })
}

// 查看供应商详情
export const queryVendorInfo = async (query: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/get/${query}`
  })
}

// 修改供应商
export const upDateVendorInfo = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/update`,
    data: query
  })
}

// 供应商删除
export const deleteVendor = async (query: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/batch`,
    data: query
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/export`
  })
}
