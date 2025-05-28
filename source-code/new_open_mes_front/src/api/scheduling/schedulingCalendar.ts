import request from '@/config/axios'

export const getBarEchartsData = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cla/calendar/scheduleQuantityList`,
    params
  })
}

// 报工柱状图数据
export const getBarReportWorkData = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cla/calendar/feedbackQuantityList`,
    params
  })
}

export const getCalendarDataAPI = async () => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cla/calendar/calendarList`
  })
}

// 查询当天班组信息
export const getTodayTeamInfo = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cla/calendar/findPlanTeam`,
    params
  })
}

// 根据班组编号和排班日期查询当前班组编号下的所有人员
export const getTeamNamePeopleListAPI = async (params) => {
  return await request.get({
    url: `${import.meta.env.VITE_API_URL_ADMIN}/mes/cla/calendar/findPlanMember`,
    params
  })
}
