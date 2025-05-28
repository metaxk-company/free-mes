// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '检验单号',
    prop: 'formCode',
    placeholder: '请输入检验单号'
  },
  {
    type: 'input',
    label: '工序编号',
    prop: 'processCode',
    placeholder: '请输入工序编号'
  },
  {
    type: 'input',
    label: '报工人',
    prop: 'reportUser',
    placeholder: '请输入报工人'
  },
  {
    type: 'date',
    label: '单据日期',
    prop: 'orderDate',
    placeholder: '请选择单据日期'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'recordCode',
    label: '检验单号',
    width: '140'
  },
  {
    prop: 'taskCode',
    label: '任务单号',
    width: '140'
  },
  {
    prop: 'orderCode',
    label: '订单编号',
    width: '150'
  },
  {
    prop: 'processCode',
    label: '工序编号',
    width: '130'
  },
  {
    prop: 'processName',
    label: '工序名称',
    width: '90'
  },
  {
    prop: 'quantity',
    label: '数量'
  },
  {
    prop: 'reportUser',
    label: '报工人'
  },
  {
    slot: 'inspectWay',
    label: '检验方式'
  },
  {
    prop: 'version',
    label: '版本'
  },
  {
    prop: 'orderDate',
    label: '单据日期',
    width: '170'
  },
  {
    slot: 'status',
    label: '单据状态',
    width: '130'
  },
  {
    prop: 'inspectGroup',
    label: '质检组'
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
    prop: 'processCode',
    label: '工序编号'
  },
  {
    prop: 'processName',
    label: '工序名称'
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
    prop: 'inspectNum',
    label: '检验数量'
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
    prop: 'quantity',
    label: '总数'
  }
]
