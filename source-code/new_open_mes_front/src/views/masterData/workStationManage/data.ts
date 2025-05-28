import { reactive } from 'vue'

export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '工作站编码',
    prop: 'workstationCode',
    placeholder: '请输入工作站编码'
  },
  {
    type: 'input',
    label: '工作站名称',
    prop: 'workstationName',
    placeholder: '请输入工作站名称'
  },
  {
    type: 'select',
    label: '车间名称',
    prop: 'workshopId',
    placeholder: '请选择车间',
    options: []
  },
  {
    type: 'select',
    label: '所属工序',
    prop: 'processId',
    placeholder: '请选择工序',
    options: []
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'workstationCode',
    label: '工作站编号',
    slot: 'workstationCode'
  },
  {
    prop: 'workstationName',
    label: '工作站名称'
  },
  {
    prop: 'workstationAddress',
    label: '工作站地点'
  },
  {
    prop: 'workshopName',
    label: '所在车间名称'
  },
  {
    prop: 'processName',
    label: '所属工序'
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
    slot: 'operation',
    width: '130'
  }
]

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
    state: 'delete',
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
