import { reactive } from 'vue'

export const btnOperation: Btn.IButton[] = reactive([
  {
    type: 'primary',
    icon: 'plus',
    state: 'add',
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
  }
])
export const columnState: Table.Column[] = [
  {
    prop: 'workshopCode',
    label: '车间编码',
    slot: 'workshopCode'
  },
  {
    prop: 'workshopName',
    label: '车间名称'
  },
  {
    prop: 'area',
    label: '面积'
  },
  {
    prop: 'charge',
    label: '负责人'
  },
  {
    prop: 'enableFlag',
    label: '是否启用',
    slot: 'enableFlag'
  },
  {
    prop: 'remark',
    label: '备注'
  },
  {
    label: '操作',
    slot: 'operate'
  }
]

// 客户端搜索条件
export const searchConditionsClient: Search.Column[] = reactive([
  {
    type: 'input',
    label: '车间编码',
    prop: 'workshopCode',
    placeholder: '请输入车间编码'
  },
  {
    type: 'input',
    label: '车间名称',
    prop: 'workshopName',
    placeholder: '请输入车间名称'
  },
  {
    type: 'input',
    label: '负责人',
    prop: 'charge',
    placeholder: '请输入负责人名称'
  }
])
