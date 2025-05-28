// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  // ：recordCode来料检验单编号，recNumber到货通知单编号，itemCode产品编号，，spec规格
  {
    type: 'input',
    label: '来料检验单编号',
    prop: 'recordCode',
    placeholder: '请输入来料检验单编号'
  },
  {
    type: 'input',
    label: '到货通知单编号',
    prop: 'recNumber',
    placeholder: '请输入到货通知单编号'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'recordCode',
    label: '来料检验单编号'
  },

  {
    prop: 'recNumber',
    label: '到货通知单编号'
  },
  {
    slot: 'inspectWay',
    label: '检验方式'
  },

  {
    prop: 'inspectUser',
    label: '质检员'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '130'
  }
]
export const columnState: Table.Column[] = [
  {
    prop: 'itemName',
    label: '检验名称'
  },
  {
    prop: 'itemStandard',
    label: '检验标准'
  },
  {
    prop: 'itemDevice',
    label: '检验仪器'
  },
  {
    slot: 'itemValue',
    label: '实际信息'
  },
  {
    slot: 'status',
    label: '是否合格'
  }
]

export const columnTakeNotes: Table.Column[] = [
  {
    prop: 'recNumber',
    label: '到货通知单编号'
  },
  {
    prop: 'itemCode',
    label: '来料编号'
  },
  {
    prop: 'unqualifiedNum',
    label: '不合格数'
  },
  {
    prop: 'qualifiedNum',
    label: '合格数'
  },
  {
    slot: 'passRate',
    label: '合格率'
  },
  {
    slot: 'failureRate',
    label: '不合格率'
  },
  {
    prop: 'inspectNum',
    label: '检验数量'
  },
  {
    prop: 'quantity',
    label: '到货数'
  }
]
