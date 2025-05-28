export interface IBtnClickFun {
  text: string
  type: string
  plain: boolean
  disabled: boolean
  sign: string
}

export interface IColumnState {
  type?: string
  width?: string
  prop?: string
  label?: string
  align?: string
  slotName?: string
}

export interface ISelectType {
  // 名称
  label: string
  // 值
  value: string
}

export const columnState: Table.Column[] = [
  {
    prop: 'vendorCode',
    label: '供应商编码',
    slot: 'vendorCode'
  },
  {
    label: '供应商名称',
    prop: 'vendorName',
    width: '200'
  },
  {
    label: '供应商简称',
    prop: 'vendorNick'
  },
  {
    label: '供应商等级',
    prop: 'vendorLevel'
  },
  {
    label: '供应商评分',
    slot: 'vendorScore',
    width: '150'
  },
  {
    label: '供应商电话',
    prop: 'tel'
  },
  // {
  //   label: '是否启用',
  //   prop: 'enableFlag',
  //   slot: 'enableFlag'
  // },
  {
    label: '备注',
    prop: 'remark'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '170'
  }
]

export const SUPPLIER_TYPE: ISelectType[] = [
  {
    label: '优质供应商',
    value: 'A'
  },
  {
    label: '正常',
    value: 'B'
  },
  {
    label: '重点关注',
    value: 'C'
  },
  {
    label: '劣质供应商',
    value: 'D'
  },
  {
    label: '黑名单',
    value: 'E'
  }
]

export const unknownValid: ISelectType[] = [
  {
    label: '是',
    value: 'Y'
  },
  {
    label: '否',
    value: 'N'
  }
]

// 客户端搜索条件
export const searchConditionsClient: Search.Column[] = reactive([
  {
    type: 'input',
    label: '供应商编码',
    prop: 'vendorCode',
    placeholder: '请输入供应商编码'
  },
  {
    type: 'input',
    label: '供应商名称',
    prop: 'vendorName',
    placeholder: '请输入供应商名称'
  },
  {
    type: 'input',
    label: '供应商简称',
    prop: 'vendorNick',
    placeholder: '请输入供应商简称'
  },
  {
    type: 'input',
    label: '英文名称',
    prop: 'vendorEn',
    placeholder: '请输入供应商英文名称'
  },
  {
    type: 'input',
    label: '供应商电话',
    prop: 'tel',
    placeholder: '请输入供应商英文名称'
  },
  {
    type: 'select',
    label: '供应商等级',
    prop: 'vendorLevel',
    placeholder: '请输入供应商等级',
    options: SUPPLIER_TYPE
  },
  {
    type: 'select',
    label: '是否启用',
    prop: 'enableFlag',
    placeholder: '选择是和否',
    options: unknownValid
  }
])
