import {
  UN_KNOW_STATE,
  STORE_HOUSE_STATUS,
  AXLE_NUM_STATUS,
  STORE_HOUSE_STATUS_INFO,
  LINES_NAME
} from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '产品型号',
    prop: 'model',
    placeholder: '请输入产品型号'
  },
  {
    type: 'input',
    label: '产品规格',
    prop: 'spec',
    placeholder: '请输入产品规格'
  },
  {
    type: 'select',
    label: '状态',
    prop: 'status',
    options: STORE_HOUSE_STATUS_INFO,
    placeholder: '请选择状态'
  },
  {
    type: 'input',
    label: '托盘编号',
    prop: 'palletNumber',
    placeholder: '请输入托盘编号'
  },
  {
    type: 'select',
    label: '线别名称',
    prop: 'lineType',
    options: LINES_NAME,
    placeholder: '请选择线别名称'
  },
  {
    type: 'input',
    label: '颜色名称',
    prop: 'color',
    placeholder: '请输入颜色名称'
  },
  {
    type: 'input',
    label: '盘号名称',
    prop: 'reelNumber',
    placeholder: '请输入盘号名称'
  },
  {
    type: 'input',
    label: '箱号名称',
    prop: 'boxNumber',
    placeholder: '请输入箱号名称'
  },
  {
    type: 'input',
    label: '条码名称',
    prop: 'barCode',
    placeholder: '请输入条码'
  },
  {
    type: 'scopeTime',
    label: '日期',
    prop: 'createTimes',
    placeholder: '请选择日期'
  }
])

export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'model',
    label: '型号',
    width: '100'
  },
  {
    prop: 'spec',
    label: '规格',
    width: '100'
  },
  {
    prop: 'boxNumber',
    label: '箱号',
    width: '140'
  },
  {
    prop: 'batchNumber',
    label: '批号',
    width: '140'
  },
  {
    prop: 'lineType',
    label: '线别'
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
    prop: 'clientCode',
    label: '客户代码'
  },
  {
    prop: 'barCode',
    label: '条码',
    width: '140'
  },
  {
    prop: 'axlesNum',
    label: '轴数',
    formatter: (row) => AXLE_NUM_STATUS[row.axlesNum]
  },
  {
    prop: 'totalHeight',
    label: '总净重(KG)',
    width: '140'
  },
  {
    prop: 'oneAxleHeight',
    label: '1轴净重(KG)',
    width: '140'
  },
  {
    prop: 'twoAxleHeight',
    label: '2轴净重(KG)',
    width: '140'
  },
  {
    prop: 'threeAxleHeight',
    label: '3轴净重(KG)',
    width: '140'
  },
  {
    prop: 'fourAxleHeight',
    label: '4轴净重(KG)',
    width: '140'
  },
  {
    prop: 'palletNumber',
    label: '托盘编号'
  },
  {
    prop: 'palletQuantity',
    label: '托盘数量'
  },
  {
    prop: 'returnGood',
    label: '是否退货',
    formatter: (row) => UN_KNOW_STATE[row.returnGood]
  },
  {
    prop: 'returnDate',
    label: '退货日期',
    width: '170'
  },
  // {
  //   prop: 'hePackage',
  //   label: '是否重包',
  //   formatter: (row) => UN_KNOW_STATE[row.hePackage]
  // },
  {
    prop: 'status',
    label: '状态',
    formatter: (row) => STORE_HOUSE_STATUS[row.status]
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '280'
  }
]
