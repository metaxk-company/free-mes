interface IColumnTable {
  type?: string
  width?: string
  align?: string
  label?: string
  prop?: string
}

export interface IPagination {
  limit: number
  page: number
}

export const columnTable: IColumnTable[] = [
  {
    type: 'selection',
    width: '55'
  },
  {
    label: '数值',
    prop: 'quantityFrom'
  },
  {
    label: '数原单位名称值',
    prop: 'unitName'
  },
  {
    label: '转换后数值',
    prop: 'quantityTo'
  },
  {
    label: '现单位名称',
    prop: 'unitTo'
  }
]
