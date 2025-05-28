import { INSPECTION_METHOD } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '检验编码',
    prop: 'inspectCode',
    placeholder: '请输入检验编码'
  },
  {
    type: 'input',
    label: '工序名称',
    prop: 'processName',
    placeholder: '请输入工序名称'
  },
  {
    type: 'select',
    label: '检验方式',
    prop: 'select',
    placeholder: '请选择检验方式',
    options: INSPECTION_METHOD
  }
])

// 工序弹框搜索
export const searchProcessConditions: Search.Column[] = reactive([
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
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'inspectCode',
    label: '检验编码'
  },
  {
    prop: 'inspectName',
    label: '检验名称'
  },
  // {
  //   prop: 'processCode',
  //   label: '工序编码'
  // },
  // {
  //   prop: 'processName',
  //   label: '工序名称'
  // },
  {
    prop: 'processCode',
    label: '物料编码'
  },
  {
    prop: 'processName',
    label: '物料名称',
    width: '150'
  },

  {
    slot: 'inspectMethod',
    label: '检验方式'
  },
  {
    prop: 'version',
    label: '检验版本'
  },
  {
    prop: 'result',
    label: '检测结果'
  },
  {
    prop: 'inspector',
    label: '检测人'
  },
  {
    prop: 'inspectTime',
    label: '检测时间'
  },
  {
    slot: 'enableFlag',
    label: '是否启用'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '230'
  }
]

export const tableProcessColumns: Table.Column[] = [
  {
    prop: 'processCode',
    label: '工序编码'
  },
  {
    prop: 'processName',
    label: '工序名称'
  },
  {
    slot: 'enableFlag',
    label: '是否启用'
  },
  {
    prop: 'remark',
    label: '备注'
  }
]

export const tableTestingItemsColumns: Table.Column[] = [
  {
    slot: 'itemName',
    label: '检测名称'
  },
  {
    slot: 'itemStandard',
    label: '检测标准'
  },
  {
    slot: 'itemDevice',
    label: '检测器具'
  },
  {
    slot: 'actual',
    label: '实际信息'
  },
  {
    slot: 'remark',
    label: '检验描述'
  },
  {
    slot: 'status',
    label: '是否合格',
    width: '300'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
