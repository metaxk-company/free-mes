import { getClientSelect } from '@/api/salesOrder/quotationList'

const getClientSelectListAPI = () => {
  getClientSelect().then((res) => {
    const clientListData = res.map((item: any) => {
      return {
        value: item.customerName,
        label: item.customerName
      }
    })
    searchConditions[1].options = clientListData
  })
}
getClientSelectListAPI()

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '客户编号',
    prop: 'customerNumber',
    placeholder: '请输入客户编号'
  },
  {
    type: 'select',
    label: '客户名称',
    prop: 'customerName',
    placeholder: '请输入客户名称',
    options: []
  }
])

export const tableColumns: Table.Column[] = [
  {
    slot: 'number',
    label: '销售单号'
  },
  {
    prop: 'customerNumber',
    label: '客户编号'
  },
  {
    prop: 'customerName',
    label: '客户名称'
  },
  {
    prop: 'customerOrderNumber',
    label: '客户订单号'
  },
  {
    prop: 'deliveryDate',
    label: '交货日期'
  },
  {
    prop: 'quantity',
    label: '订单总数'
  },
  {
    prop: 'remark',
    label: '备注'
  }
]

export const detailTableColumns: Table.Column[] = [
  { prop: 'number', label: '出库单号', width: '170' },
  { prop: 'saleNumber', label: '销售单号', width: '170' },
  { prop: 'itemCode', label: '产品编号' },
  { prop: 'model', label: '型号', width: '170' },
  { prop: 'spec', label: '规格' },
  { prop: 'lineType', label: '线别' },
  { prop: 'customerNumber', label: '客户单号' },
  { prop: 'customerName', label: '客户名称', width: '170' },
  { prop: 'customerOrderNumber', label: '客户订单号', width: '170' },
  { prop: 'includeTax', label: '价格' },
  { prop: 'quantity', label: '数量' },
  { prop: 'sendOut', label: '已发' },
  { prop: 'noSend', label: '未发' },
  { prop: 'totalTare', label: '总皮重' },
  { prop: 'pieces', label: '件数' }
]
