import { UN_KNOW_ENABLE } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '工序编码',
    prop: 'processCode',
    placeholder: '请输入工序编码'
  },
  {
    type: 'input',
    label: '工序名称',
    prop: 'processName',
    placeholder: '请输入工序名称'
  },
  {
    type: 'select',
    label: '是否启用',
    prop: 'enableFlag',
    placeholder: '是否启用',
    options: UN_KNOW_ENABLE
  }
])

// 按钮功能
export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'primary',
    icon: 'plus',
    state: 'save',
    label: '新增',
    disabled: false,
    permissions: []
  },
  {
    type: 'success',
    icon: 'edit',
    state: 'edit',
    label: '修改',
    disabled: true,
    permissions: []
  },
  {
    type: 'danger',
    icon: 'delete',
    state: 'remove',
    label: '批量删除',
    disabled: true,
    permissions: []
  },
  {
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    permissions: []
  }
])

export const tableColumns: Table.Column[] = [
  {
    label: '工序编码',
    slot: 'processCode'
  },
  {
    prop: 'processName',
    label: '工序名称'
  },
  {
    prop: 'preparationTime',
    label: '准备时间'
  },
  {
    prop: 'productiveTime',
    label: '生产时间'
  },
  // {
  //   prop: 'manHour',
  //   label: '工时(分钟)'
  // },
  {
    label: '是否启用',
    slot: 'enableFlag'
  },
  {
    prop: 'remark',
    label: '备注'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]
