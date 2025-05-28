import request from '@/config/axios'

// 生成分类编码接口
export const getTestClassifyCode = async (params: string) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + `/system/autocode/get/${params}`
  })
}

// 查询检验分类列表
export const getTestClassifyList = async (params: Object) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/classify/list`,
    params
  })
}

// 新增检验分类
export const addTestClassify = async (params: Object) => {
  return await request.post({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/classify/save`,
    data: params
  })
}

// 查询检验分类详细
export const queryTestClassifyInfo = async (params: Number | String) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/classify/find/${params}`
  })
}

// 修改检验分类
export const upDateTestClassifyInfo = async (params: Object) => {
  return await request.put({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/classify/update`,
    data: params
  })
}

// 删除检验分类
export const deleteTestClassifyInfo = async (query: Array<Number>) => {
  return await request.delete({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/qc/project/classify/batch`,
    data: query
  })
}
