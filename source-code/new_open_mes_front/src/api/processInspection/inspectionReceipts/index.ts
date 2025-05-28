import request from '@/config/axios'

// 查询检验单据列表
export const getTestReceiptList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/list`,
    params
  })
}

// 获取检验单对比信息(有检验标准接口)
export const getInspectionFormInfo = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/qualityInspectionShow1`,
    params
  })
}

// 下一条检验信息调取
export const nextInfoHandle = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/qualityInspectionNext`,
    params
  })
}

// 检验结束完成接口
export const testEndSubmit = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/qualityInspectionFinish`,
    params
  })
}

// 导出功能
export const downloadListData = async () => {
  return await request.download({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/exportProcessForm`
  })
}

// 检验表格行的失去焦点保存
export const getTableBlurServer = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/qualityInspection`,
    params
  })
}

// 处理合格与不合格
export const editQualifiedState = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/qualityInspectionNoStandard`,
    params
  })
}

// 处理完合格不合格提交结果处理
export const submitQualifiedTest = async (params: Object) => {
  return await request.get({
    url: `${
      import.meta.env.VITE_API_URL_ADMIN
    }/mes/qc/process/form/qualityInspectionNoStandardFinish`,
    params
  })
}

// 查看当前质检完成信息
export const getQualityTestingInfo = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/process/form/qualityInspectionResultShow`,
    params
  })
}
