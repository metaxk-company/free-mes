// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '线别名称',
    prop: 'lineType',
    placeholder: '请输入线别名称'
  },
  {
    type: 'input',
    label: '型号名称',
    prop: 'model',
    placeholder: '请输入型号名称'
  },
  {
    type: 'input',
    label: '规格名称',
    prop: 'spec',
    placeholder: '请输入规格名称'
  },
  {
    type: 'input',
    label: '盘号名称',
    prop: 'reelNumber',
    placeholder: '请输入盘号名称'
  }
])

export const tableColumns: Table.Column[] = [
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
    prop: 'color',
    label: '颜色'
  },
  {
    prop: 'reelNumber',
    label: '盘号'
  },
  {
    prop: 'totalHeight',
    label: '入库总数'
  },
  {
    prop: 'totalWeight',
    label: '出库总数'
  },
  {
    prop: 'outboundPieces',
    label: '出库总件数'
  },
  {
    prop: 'date',
    label: '日期',
    width: '190'
  }
]
