// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    label: '订单编号',
    type: 'input',
    prop: 'workorderCode',
    placeholder: '订单编号'
  },
  {
    label: '开始时间',
    type: 'date',
    prop: 'startTime',
    placeholder: '请选择开始时间'
  },
  {
    label: '结束时间',
    type: 'date',
    prop: 'requestDate',
    placeholder: '请选择结束时间'
  },
  {
    label: '产品名称',
    type: 'input',
    prop: 'productName',
    placeholder: '产品名称'
  }
])

export const tableColumns: Table.Column[] = [
  { label: '订单编号', prop: 'workorderCode' },
  { label: '订单名称', prop: 'workorderName' },
  { label: '产品编号', prop: 'productCode' },
  { label: '产品名称', prop: 'productName' },
  { label: '开始日期', prop: 'startTime' },
  { label: '结束时间', prop: 'requestDate' }
]
