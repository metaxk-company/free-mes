export const searchConditions: Search.Column[] = [
  {
    type: 'input',
    label: '人员姓名',
    prop: 'nameOfPerson',
    placeholder: '请输入人员姓名'
  },
  {
    type: 'input',
    label: '人员编号',
    prop: 'personnelNo',
    placeholder: '请输入人员编号'
  },
  {
    type: 'date',
    label: '填报日期',
    prop: 'startDate',
    placeholder: '请选择填报日期'
  }
]

export const columnState: Table.Column[] = [
  {
    prop: 'taskOrder',
    label: '任务单号',
    align: 'center'
  },
  {
    prop: 'startDate',
    label: '填报日期',
    align: 'center'
  },
  {
    prop: 'nameOfPerson',
    label: '人员姓名',
    align: 'center'
  },
  {
    prop: 'personnelNo',
    label: '人员编号',
    align: 'center'
  },
  {
    prop: 'workingType',
    label: '工时类型',
    align: 'center'
  },
  {
    prop: 'hoursWork',
    label: '工时时长',
    align: 'center'
  },
  {
    prop: 'productionQuantities',
    label: '生产数量',
    align: 'center'
  },
  {
    prop: 'Workbench',
    label: '工作台',
    align: 'center'
  },
  {
    prop: 'remark',
    label: '备注',
    align: 'center'
  },
  {
    slot: 'operate',
    label: '操作',
    align: 'center',
    fixed: 'right'
  }
]
