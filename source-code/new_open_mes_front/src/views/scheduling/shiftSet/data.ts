// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '班组类型',
    prop: 'teamType',
    placeholder: '选择班组类型',
    options: []
  },
  {
    type: 'input',
    label: '班组编号',
    prop: 'teamCode',
    placeholder: '请输入班组编号'
  },
  {
    type: 'input',
    label: '班组名称',
    prop: 'teamName',
    placeholder: '请输入班组名称'
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
    disabled: false,
    permissions: []
  }
])

export const tableColumns: Table.Column[] = [
  {
    label: '班组编号',
    prop: 'teamCode'
  },
  {
    label: '班组类型',
    prop: 'teamType'
  },
  {
    label: '班组名称',
    prop: 'teamName'
  },
  {
    label: '班组组长',
    prop: 'teamLeader'
  },
  {
    label: '班组人员',
    slot: 'shiftPersonnel'
  },
  {
    label: '备注',
    prop: 'remark'
  },
  {
    label: '操作',
    slot: 'operate',
    width: '380',
    fixed: 'right'
  }
]

export const personnelColumns: Table.Column[] = [
  {
    label: '人员编号',
    prop: 'id'
  },
  {
    label: '人员名称',
    prop: 'userName'
  }
]
