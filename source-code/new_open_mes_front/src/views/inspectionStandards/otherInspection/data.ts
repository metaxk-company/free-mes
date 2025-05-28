// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '检验编码',
    prop: 'number',
    placeholder: '请输入检验编码'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '检验编码',
    width: '200'
  },
  {
    prop: 'name',
    label: '检验名称'
  },
  {
    prop: 'method',
    label: '检验方式'
  },
  {
    prop: 'version',
    label: '检验版本'
  },

  {
    slot: 'enableFlag',
    label: '是否启用'
  },
  {
    prop: 'lineType',
    label: '线别'
  },

  {
    prop: 'model',
    label: '型号'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'reelNumber',
    label: '盘号'
  },
  {
    prop: 'color',
    label: '颜色'
  },

  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '230'
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
    slot: 'remark',
    label: '检验描述'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
