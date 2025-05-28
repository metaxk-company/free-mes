import request from '@/config/axios'

export const getTodoTaskPage = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/task/todo-page',
    params
  })
}

export const getDoneTaskPage = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/task/done-page',
    params
  })
}

export const completeTask = async (data) => {
  return await request.put({ url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/task/complete', data })
}

export const approveTask = async (data) => {
  return await request.put({ url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/task/approve', data })
}

export const rejectTask = async (data) => {
  return await request.put({ url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/task/reject', data })
}
export const backTask = async (data) => {
  return await request.put({ url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/task/back', data })
}

export const updateTaskAssignee = async (data) => {
  return await request.put({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/task/update-assignee',
    data
  })
}

export const getTaskListByProcessInstanceId = async (processInstanceId) => {
  return await request.get({
    url:
      import.meta.env.VITE_API_URL_ADMIN +
      '/bpm/task/list-by-process-instance-id?processInstanceId=' +
      processInstanceId
  })
}
