// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '单位编码',
    prop: 'id',
    placeholder: '请输入单位编码'
  },
  {
    type: 'input',
    label: '单位名称',
    prop: 'measureName',
    placeholder: '请输入单位名称'
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
    authority: 'md:unitmeasure:save',
    permissions: []
  },
  {
    type: 'success',
    icon: 'edit',
    state: 'edit',
    label: '修改',
    disabled: true,
    authority: 'md:unitmeasure:update',
    permissions: []
  },
  {
    type: 'danger',
    icon: 'delete',
    state: 'remove',
    label: '批量删除',
    disabled: true,
    permissions: [],
    authority: 'md:unitmeasure:delete'
  },
  {
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    permissions: [],
    authority: 'mes:md:unitmeasure:export'
  }
])

export const tableColumns: Table.Column[] = [
  {
    label: '单位编码',
    prop: 'id',
    align: 'center'
  },
  {
    prop: 'measureName',
    label: '单位名称',
    align: 'center'
  },
  {
    slot: 'primaryFlag',
    label: '是否是主单位',
    align: 'center'
  },
  {
    prop: 'changeRate',
    label: '与主单位换算比例',
    align: 'center'
  },
  {
    label: '是否启用',
    slot: 'enableFlag',
    align: 'center'
  },
  {
    prop: 'remark',
    label: '备注',
    align: 'center'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right',
    align: 'center'
  }
]
