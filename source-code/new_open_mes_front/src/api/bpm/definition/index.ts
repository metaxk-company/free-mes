import request from '@/config/axios'

export const getProcessDefinitionBpmnXMLApi = async (id: number) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/process-definition/get-bpmn-xml?id=' + id
  })
}

export const getProcessDefinitionPageApi = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/process-definition/page',
    params
  })
}

export const getProcessDefinitionListApi = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/process-definition/list',
    params
  })
}
