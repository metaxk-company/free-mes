import request from '@/config/axios'

// 查询部门下拉树结构
export const queryTreeSelect = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/itemType/treeSelect`
  })
}

// 查询物料列表
export const queryMDItemList = async (query?: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/list`,
    params: query
  })
}

// 自动生成物料编码
export const getCreateCode = async (query: string) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/system/autocode/get/${query}`
  })
}

// 获取单位
export const getUnitList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/unit/measure/selectall`
  })
}

// 新增物料
export const addMDItem = async (query: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/save`,
    data: query
  })
}

// 修改物料
export const upDateMDItem = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/update`,
    data: query
  })
}

// 查询物料详细
export const queryMDItemInfo = async (query: string | number) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/get/${query}`
  })
}

// BOM 组成表格数据
export const getBOMItemTableList = async (query: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/product/bom/list`,
    params: query
  })
}

// 新增产品BOM关系
export const saveBOMRelation = async (query: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/product/bom/save`,
    data: query
  })
}

// 查询产品BOM关系详细
export const queryBOMRelationInfo = async (query: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/product/bom/get/${query}`
  })
}

// 修改产品BOM关系
export const upDateBOMRelation = async (query: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/product/bom/update`,
    data: query
  })
}

// 删除产品BOM关系
export const deleteBOMRelation = async (query: any) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/product/bom/batch`,
    data: query
  })
}

// 删除物料
export const deleteMDItem = async (query: any) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/batch`,
    data: query
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/export`
  })
}

// 下载模版接口
export const downloadTemplate = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/item/templateData`
  })
}

// 线别列表
export const getListLineType = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dict/data/listLineType`
  })
}
// 供应商列表
export const getSupplierSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/vendor/list`
  })
}
// 型号列表
export const getModelSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/model/listAll`
  })
}

// 规格列表
export const getSpecSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/spec/listAll`
  })
}
// 颜色列表
export const getColorSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/md/color/list`
  })
}
// 品类列表
export const getClassSelectList = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/dict/data/listCategory`
  })
}
// 根据箱号获取信息
export const getProductInformation = async (query: any) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/returns/getOutboundItemLabel/${query}`
    // url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/order/returns/getOutboundItemLabel`,
    // params: query
  })
}
