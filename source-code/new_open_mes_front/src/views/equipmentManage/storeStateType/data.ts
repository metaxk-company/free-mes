// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '状态类型',
    prop: 'statusName',
    placeholder: '选择状态类型',
    options: []
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
  }
])

export const tableColumns: Table.Column[] = [
  {
    label: '状态编码',
    prop: 'id'
  },
  {
    prop: 'statusName',
    label: '设备状态'
  },
  {
    prop: 'createTime',
    label: '创建时间'
  },
  {
    prop: 'remark',
    label: '备注'
  }
]
