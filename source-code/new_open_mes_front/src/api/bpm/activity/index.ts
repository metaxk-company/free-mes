import request from '@/config/axios'

export const getActivityList = async (params) => {
  return await request.get({
    url: import.meta.env.VITE_API_URL_ADMIN + '/bpm/activity/list',
    params
  })
}
