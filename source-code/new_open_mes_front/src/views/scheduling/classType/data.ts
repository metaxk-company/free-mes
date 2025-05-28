// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '类型名称',
    prop: 'typeName',
    placeholder: '请选择类型名称',
    options: []
  },
  {
    type: 'input',
    label: '创建人员',
    prop: 'creator',
    placeholder: '请输入创建人员'
  },
  {
    type: 'date',
    label: '创建时间',
    prop: 'createTime',
    placeholder: '请选择创建时间'
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
    label: '类型编号',
    prop: 'typeCode'
  },
  {
    label: '类型名称',
    prop: 'typeName'
  },
  {
    label: '创建时间',
    prop: 'createTime',
    width: '170'
  },
  {
    label: '创建人员',
    prop: 'creator'
  },
  {
    label: '备注',
    prop: 'remark'
  },
  {
    label: '操作',
    slot: 'operate',
    width: '230',
    fixed: 'right'
  }
]
