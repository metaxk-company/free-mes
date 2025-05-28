import request from '@/config/axios'

// 查询单位换算列表
export const queryUnitConversionList = async (params) => {
  return await request.get({
    url: '/mes/md/conversion/list',
    params
  })
}

// 查询单位换算详细
export const queryUnitDetailed = async (id) => {
  return await request.get({
    url: `/mes/md/conversion/${id}`
  })
}

// 删除单位换算
export const deleteUnit = async (id) => {
  return await request.delete({
    url: `/mes/md/conversion/${id}`
  })
}

// 新增单位换算
export const saveUnitConversion = async (data) => {
  return await request.post({
    url: '/mes/md/conversion',
    data
  })
}

// 修改单位换算
export const updateConversion = async (data) => {
  return request.put({
    url: '/mes/md/conversion',
    data
  })
}

// 根据单位的编号查询单位的名称
export const findMeasureName = async (code) => {
  return await request.get({
    url: `/mes/md/unitmeasure/find/${code}`
  })
}
