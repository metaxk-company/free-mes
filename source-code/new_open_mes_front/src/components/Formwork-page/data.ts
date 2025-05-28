import { UN_KNOW_ENABLE } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '输入框',
    prop: 'input',
    placeholder: '请输入输入框'
  },
  {
    type: 'select',
    label: '下拉框',
    prop: 'select',
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
    label: '删除',
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
