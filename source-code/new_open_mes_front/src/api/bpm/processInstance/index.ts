import request from '@/config/axios'

export type Task = {
  id: string
  name: string
}
export type ProcessInstanceVO = {
  id: number
  name: string
  processDefinitionId: string
  category: string
  result: number
  tasks: Task[]
  fields: string[]
  status: number
  remark: string
  businessKey: string
  createTime: string
  endTime: string
}

export const getMyProcessInstancePageApi = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/process-instance/my-page',
    params
  })
}

export const createProcessInstanceApi = async (data) => {
  return await request.post({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/process-instance/create',
    data: data
  })
}

export const cancelProcessInstanceApi = async (id: number, reason: string) => {
  const data = {
    id: id,
    reason: reason
  }
  return await request.delete({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/process-instance/cancel',
    data: data
  })
}

export const getProcessInstanceApi = async (id: number) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/process-instance/get?id=' + id
  })
}
