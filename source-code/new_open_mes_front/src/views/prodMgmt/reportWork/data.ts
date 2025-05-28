import { EQUIPMENT_STATUS } from '@/utils/const'

// 按钮功能
export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    permissions: []
  }
])

export const tableColumns: Table.Column[] = [
  { label: '设备序号', prop: 'id' },
  { label: '任务编号', prop: 'taskCode', width: '130' },
  { label: '报工编号', prop: 'feedbackCode' },
  { label: '暂停原因', prop: 'remork' },
  { label: '暂停时间', prop: 'pauseTime', width: '150' },
  { label: '状态', prop: 'status', slot: 'status', fixed: 'right' },
  { label: '操作', slot: 'operation', width: '300', fixed: 'right', align: 'center' }
]

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '设备序号',
    prop: 'id',
    placeholder: '请输入设备序号'
  },
  {
    type: 'input',
    label: '报工编号',
    prop: 'feedbackCode',
    placeholder: '请输入报工编号'
  },
  {
    type: 'select',
    label: '设备状态',
    prop: 'status',
    placeholder: '选择状态',
    options: EQUIPMENT_STATUS
  }
])
