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
    label: '分类名称',
    prop: 'itemTypeName',
    placeholder: '请输入分类名称'
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
  },
  {
    type: 'select',
    label: '物料产品',
    prop: 'itemOrProduct',
    placeholder: '选择物料产品',
    options: [
      { value: 'ITEM', label: '物料' },
      {
        value: 'PRODUCT',
        label: '产品'
      }
    ]
  }
])
