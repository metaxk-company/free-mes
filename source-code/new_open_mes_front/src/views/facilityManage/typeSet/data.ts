export const STATE_YES = 'Y'
export const STATE_NO = 'N'

export const ITEM = '物料'
export const PRODUCT = '产品'

export const STATE_TYPE = {
  [STATE_YES]: '是',
  [STATE_NO]: '否'
}

export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '类型名称',
    prop: 'machineryTypeName',
    placeholder: '请输入设备类型名称'
  },
  {
    type: 'select',
    label: '是否启用',
    prop: 'enableFlag',
    placeholder: '选择是或否',
    options: [
      { value: STATE_YES, label: '是' },
      {
        value: STATE_NO,
        label: '否'
      }
    ]
  }
])
