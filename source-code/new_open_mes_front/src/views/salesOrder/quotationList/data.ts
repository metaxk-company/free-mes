import { LINES_NAME } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = [
  // {
  //   type: 'input',
  //   label: '客户编号',
  //   prop: 'customerNumber',
  //   placeholder: '请输入客户编号'
  // },
  {
    type: 'input',
    label: '客户名称',
    prop: 'customerName',
    placeholder: '请输入客户名称'
  },
  {
    type: 'select',
    label: '线别名称',
    prop: 'lineType',
    placeholder: '请选择线别',
    options: LINES_NAME
  },
  {
    type: 'scopeTime',
    label: '创建日期',
    prop: 'createTimes',
    placeholder: '请输入创建日期'
  },
  {
    type: 'input',
    label: '型号名称',
    prop: 'model',
    placeholder: '请输入型号名称'
  }
]

export const tableColumns: Table.Column[] = [
  {
    prop: 'customerNumber',
    label: '客户编号',
    width: '90'
  },
  {
    prop: 'customerName',
    label: '客户名称',
    width: '220'
  },
  {
    prop: 'lineType',
    label: '线别'
  },
  {
    prop: 'model',
    label: '型号',
    width: '300'
  },
  {
    prop: 'createTime',
    label: '创建日期',
    width: '220'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '240'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    slot: 'spec',
    label: '规格'
  },
  {
    slot: 'startSpec',
    label: '开始规格'
  },
  {
    slot: 'endSpec',
    label: '结束规格'
  },
  {
    slot: 'price',
    label: '加工费（元）'
  }
]
