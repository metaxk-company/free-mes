import request from '@/config/axios'
import type { CodegenUpdateReqVO, CodegenCreateListReqVO } from './types'

// 查询列表代码生成表定义
export const getCodegenTablePageApi = (params) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/table/page',
    params
  })
}

// 查询详情代码生成表定义
export const getCodegenTableApi = (id: number) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/detail?tableId=' + id
  })
}

// 新增代码生成表定义
export const createCodegenTableApi = (data: CodegenCreateListReqVO) => {
  return request.post({ url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/create', data })
}

// 修改代码生成表定义
export const updateCodegenTableApi = (data: CodegenUpdateReqVO) => {
  return request.put({ url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/update', data })
}

// 基于数据库的表结构，同步数据库的表和字段定义
export const syncCodegenFromDBApi = (id: number) => {
  return request.put({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/sync-from-db?tableId=' + id
  })
}

// 基于 SQL 建表语句，同步数据库的表和字段定义
export const syncCodegenFromSQLApi = (id: number, sql: string) => {
  return request.put({
    url:
      import.meta.env.VITE_API_URL_ADMIN +
      '/infra/codegen/sync-from-sql?tableId=' +
      id +
      '&sql=' +
      sql
  })
}

// 预览生成代码
export const previewCodegenApi = (id: number) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/preview?tableId=' + id
  })
}

// 下载生成代码
export const downloadCodegenApi = (id: number) => {
  return request.download({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/download?tableId=' + id
  })
}

// 获得表定义
export const getSchemaTableListApi = (params) => {
  return request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/db/table/list',
    params
  })
}

// 基于数据库的表结构，创建代码生成器的表定义
export const createCodegenListApi = (data) => {
  return request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/create-list',
    data
  })
}

// 删除代码生成表定义
export const deleteCodegenTableApi = (id: number) => {
  return request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/infra/codegen/delete?tableId=' + id
  })
}
