import request from '@/config/axios'

// 查询表格参数
export const queryTableList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/problemSubmit/list`,
    params
  })
}

// 新增
export const addFormData = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/problemSubmit/save`,
    data: params
  })
}

// 查询
export const queryFormInfo = async (params: number | string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/problemSubmit/find/${params}`
  })
}

// 修改
export const updateFormInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/problemSubmit/update`,
    data: params
  })
}

// 删除
export const deleteFormInfo = async (params: Array<number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/problemSubmit/batch`,
    data: params
  })
}

//  供应商列表
export const getVendorSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/list`
  })
}
//新供应商列表
export const getVendorSelectData = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/listAll`
  })
}
// 产品类别列表
export const productClassList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dict/data/listKind`
  })
}

// 自动生成
export const getCreateCode = async (query: string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/system/autocode/get/${query}`
  })
}

// 采购订单列表数据
export const purchaseOrderList = async (params?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/list`,
    params
  })
}

// 类型接口
export const getType = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/type/list`
  })
}
// 零部件
export const getComponent = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/component/list`
  })
}
//来源
export const getSource = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/source/list`
  })
}
//模式
export const getMode = async (query?) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/mode/findByIssueType/${query}`
  })
}
// 状态修改
export const changeState = async (query?) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/issue/problemSubmit/check/${query}`
  })
}
