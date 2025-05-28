import { UN_KNOW_ENABLE } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '路线编号',
    prop: 'routeCode',
    placeholder: '请输入路线编号'
  },
  {
    type: 'input',
    label: '路线名称',
    prop: 'routeName',
    placeholder: '请输入路线名称'
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
    label: '工艺路线编号',
    slot: 'routeCode'
  },
  {
    prop: 'routeName',
    label: '工艺路线名称'
  },
  {
    prop: 'routeDesc',
    label: '工艺路线说明'
  },
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
    width: '170',
    fixed: 'right'
  }
]

//工序组成表格头部
export const processComposeColumn: Table.Column[] = [
  {
    prop: 'processCode',
    label: '工序编码',
    width: '120'
  },
  {
    prop: 'processName',
    label: '工序名称',
    width: '120'
  },
  {
    prop: 'nextProcessName',
    label: '下一道工序',
    width: '120'
  },
  {
    slot: 'linkType',
    label: '与下一道工序关系',
    width: '150'
  },
  {
    slot: 'keyFlag',
    label: '关键工序',
    width: '120'
  },
  {
    prop: 'defaultPreTime',
    label: '准备时间',
    width: '120'
  },
  {
    prop: 'defaultSufTime',
    label: '等待时间',
    width: '120'
  },
  {
    label: '甘特图颜色',
    slot: 'colorCode',
    width: '120'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]

// 关联产品表格头部
export const productRelevanceColumn: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '产品物料编码',
    width: '150'
  },
  {
    prop: 'itemName',
    label: '产品物料名称'
  },
  {
    prop: 'specification',
    label: '规格型号'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]
