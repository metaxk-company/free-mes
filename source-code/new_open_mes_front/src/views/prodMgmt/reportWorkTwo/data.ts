export const FEEDBACK_TYPE = [
  { value: 'UNI', label: '统一报工' },
  { value: 'UNI', label: '自行报工' }
]

export const STATUS_TYPE = [
  { value: 'NoSTARTED', label: '未开工' },
  { value: 'PAUSED', label: '已暂停' },
  { value: 'FINISHED', label: '已报工' }
]

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  // {
  //   label: '报工类型',
  //   type: 'select',
  //   prop: 'feedbackType',
  //   placeholder: '请选择报工类型',
  //   options: FEEDBACK_TYPE
  // },
  {
    label: '报工状态',
    type: 'select',
    prop: 'status',
    placeholder: '请选择报工状态',
    options: STATUS_TYPE
  },
  {
    label: '工单编号',
    type: 'input',
    prop: 'workorderCode',
    placeholder: '请输入工单编号'
  },
  {
    label: '任务单号',
    type: 'input',
    prop: 'taskCode',
    placeholder: '请输入任务单号'
  },
  {
    label: '产品名称',
    type: 'input',
    prop: 'itemName',
    placeholder: '请输入产品名称'
  },
  {
    label: '报工人',
    type: 'input',
    prop: 'userName',
    placeholder: '请输入报工人'
  }
])

// 按钮功能
export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'primary',
    icon: 'plus',
    state: 'save',
    label: '新增',
    disabled: false,
    permissions: [],
    authority: ''
  },
  {
    type: 'success',
    icon: 'edit',
    state: 'edit',
    label: '修改',
    disabled: true,
    permissions: [],
    authority: ''
  },
  {
    type: 'danger',
    icon: 'delete',
    state: 'remove',
    label: '删除',
    disabled: true,
    permissions: [],
    authority: 'pro:feedbacktwo:delete'
  }
])

export const tableColumns: Table.Column[] = [
  { label: '任务单号', prop: 'taskCode', width: '150', align: 'center' },
  { label: '订单编号', prop: 'workorderCode', width: '150', align: 'center' },
  { label: '产品名称', prop: 'itemName', width: '200', align: 'center' },
  { label: '单位', prop: 'unitOfMeasure', align: 'center' },

  { label: '报工数量', prop: 'quantity', width: '100', align: 'center' },
  { label: '排产数量', prop: 'orderQuantity', width: '100', align: 'center' },
  {
    slot: 'reportingProgress',
    label: '报工进度',
    width: '150'
  },
  { label: '订单数量', prop: 'workOrderQuentity', width: '100', align: 'center' },
  { label: '开工时间', prop: 'feedbackTime', width: '170', align: 'center' },
  { label: '暂停时间', prop: 'pauseTime', width: '170', align: 'center' },
  { label: '暂停原因', slot: 'pauseReason', width: '170', align: 'center' },
  { label: '报工时间', prop: 'finishedTime', width: '170', align: 'center' },
  { label: '工序编号', prop: 'processCode', width: '170', align: 'center' },
  { label: '工序名称', prop: 'processName', width: '170', align: 'center' },
  // { label: '工时(分钟)', prop: 'workHour', width: '150' },
  { label: '报工人', prop: 'userName', align: 'center' },
  { label: '状态', prop: 'status', slot: 'status', fixed: 'right', width: '100', align: 'center' },
  { label: '操作', slot: 'operation', width: '310', fixed: 'right', align: 'center' }
]

export const PRODUCE_COLUMNS: Table.Column[] = [
  {
    label: '序号'
  },
  {
    label: '设备编号',
    slot: 'equipmentCode'
  },
  {
    label: '操作',
    align: 'center',
    slot: 'operate'
  }
]
