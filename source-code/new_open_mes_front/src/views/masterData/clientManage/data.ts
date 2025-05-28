export interface IBtnClickFun {
  text: string
  type: '' | 'default' | 'primary' | 'success' | 'danger' | 'warning' | 'text' | 'info'
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
  slot?: string
}

export interface ISelectType {
  // 名称
  label: string
  // 值
  value: string
}

import { UN_KNOW_ENABLE } from '@/utils/const'

export const columnState: IColumnState[] = [
  {
    label: '客户编码',
    slot: 'clientCode'
  },
  {
    label: '客户名称',
    prop: 'clientName',
    width: '200'
  },
  {
    label: '客户简称',
    prop: 'clientNick'
  },
  {
    label: '客户类型',
    slot: 'clientType'
  },
  {
    label: '客户电话',
    prop: 'tel'
  },
  {
    label: '联系人',
    prop: 'contact1'
  },
  {
    label: '联系人-电话',
    prop: 'contact1Tel'
  },
  // {
  //   label: '是否启用',
  //   slot: 'enableFlag'
  // },
  {
    label: '操作',
    slot: 'operation',
    width: '170'
  }
]

export const enterpriseType: ISelectType[] = [
  {
    label: '企业用户',
    value: 'ENTERPRISE'
  },
  {
    label: '个人用户',
    value: 'PERSON'
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
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '客户编码',
    prop: 'clientCode',
    placeholder: '请输入客户编码'
  },
  {
    type: 'input',
    label: '客户名称',
    prop: 'clientName',
    placeholder: '请输入客户名称'
  },
  {
    type: 'input',
    label: '客户简称',
    prop: 'clientNick',
    placeholder: '请输入客户简称'
  },
  {
    type: 'input',
    label: '客户英文名',
    prop: 'clientEn',
    placeholder: '请输入客户英文名称'
  },

  {
    type: 'select',
    label: '是否启用',
    prop: 'enableFlag',
    placeholder: '是否启用',
    options: UN_KNOW_ENABLE
  },
  {
    type: 'input',
    label: '客户电话',
    prop: 'tel',
    placeholder: '请输入客户电话'
  }
])
