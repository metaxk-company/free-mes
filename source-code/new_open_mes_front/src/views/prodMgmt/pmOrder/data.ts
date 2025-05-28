import { reactive } from 'vue'
import { UN_KNOW_ENABLE } from '@/utils/const'
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '订单编码',
    prop: 'workorderCode',
    placeholder: '请输入订单编码'
  },
  {
    type: 'input',
    label: '订单名称',
    prop: 'workorderName',
    placeholder: '请输入订单名称'
  },
  // {
  //   type: 'input',
  //   label: '来源单据',
  //   prop: 'sourceCode',
  //   placeholder: '请输入来源单据'
  // },
  {
    type: 'input',
    label: '产品编号',
    prop: 'productCode',
    placeholder: '请输入产品编号'
  },
  {
    type: 'input',
    label: '产品名称',
    prop: 'productName',
    placeholder: '请输入产品名称'
  },
  // {
  //   type: 'input',
  //   label: '客户编码',
  //   prop: 'clientCode',
  //   placeholder: '请输入客户编码'
  // },
  {
    type: 'input',
    label: '客户名称',
    prop: 'clientName',
    placeholder: '请输入客户名称'
  },
  {
    type: 'date',
    label: '需求日期',
    prop: 'requestDate',
    placeholder: '请选择需求日期'
  }
])

export const taskTableColumns: Table.Column[] = [
  { label: '任务单号', prop: 'taskCode', width: '150', align: 'center' },
  { label: '订单编号', prop: 'workorderCode', width: '150', align: 'center' },
  { label: '产品名称', prop: 'itemName', width: '150', align: 'center' },

  { label: '单位', prop: 'unitOfMeasure', align: 'center' },
  { label: '报工数量', prop: 'quantity', width: '120', align: 'center' },
  { label: '排产数量', prop: 'orderQuantity', width: '120', align: 'center' },
  {
    slot: 'reportingProgress',
    label: '报工进度',
    width: '150'
  },
  { label: '订单数量', prop: 'workOrderQuentity', width: '120', align: 'center' },
  { label: '开工时间', prop: 'feedbackTime', width: '170', align: 'center' },
  { label: '暂停时间', prop: 'pauseTime', width: '170', align: 'center' },
  { label: '暂停原因', prop: 'pauseReason', width: '170', align: 'center' },
  { label: '报工时间', prop: 'finishedTime', width: '170', align: 'center' },
  { label: '工序编号', prop: 'processCode', width: '170', align: 'center' },
  { label: '工序名称', prop: 'processName', width: '170', align: 'center' },
  // { label: '工时(分钟)', prop: 'workHour', width: '150' },
  { label: '报工人', prop: 'userName', align: 'center' },
  { label: '状态', slot: 'status', fixed: 'right', width: '100', align: 'center' }
]

// 客户端搜索条件
export const searchConditionsClient: Search.Column[] = reactive([
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
    label: '客户英文',
    prop: 'clientEn',
    placeholder: '请输入英文名称'
  },
  {
    type: 'select',
    label: '是否启用',
    prop: 'enableFlag',
    placeholder: '是否启用',
    options: UN_KNOW_ENABLE
  }
])

export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'primary',
    icon: 'plus',
    state: 'save',
    label: '新增',
    disabled: false,
    loading: false,
    permissions: []
  },
  {
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    loading: false,
    permissions: []
  },
  {
    type: 'primary',
    icon: 'printer',
    state: 'printer',
    disabled: true,
    label: '打印',
    loading: false,
    permissions: []
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'workorderCode',
    label: '订单编码',
    slot: 'workorderCode',
    width: '150'
  },
  {
    prop: 'workorderName',
    label: '订单名称',
    width: '120'
  },
  {
    prop: 'quantity',
    label: '订单数量'
  },
  {
    label: '订单来源',
    slot: 'orderSource',
    width: '120'
  },

  {
    prop: 'productCode',
    label: '产品编号',
    width: '150'
  },
  {
    prop: 'productName',
    label: '产品名称',
    width: '200'
  },
  {
    prop: 'productSpc',
    label: '规格型号',
    width: '120'
  },
  // {
  //   label: '单据状态',
  //   slot: 'status',
  //   width: '120'
  // },
  {
    label: '生产进度',
    slot: 'productionSchedule',
    width: '120'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },

  {
    prop: 'batchCode',
    label: '批次号'
  },

  {
    prop: 'clientName',
    label: '客户名称',
    width: '120'
  },
  {
    prop: 'requestDate',
    label: '需求日期',
    width: '170'
  },
  {
    prop: 'orderDate',
    label: '生产订单日期',
    width: '170'
  },
  {
    prop: 'produceDate',
    label: '生产日期',
    width: '170'
  },

  {
    label: '操作',
    slot: 'operation',
    width: '210',
    fixed: 'right'
  }
]

export const SOURCE_TYPE = [
  {
    label: 'ORDER',
    text: '客户订单'
  },
  {
    label: 'STORE',
    text: '库存备货'
  }
]

export const columnStateMini: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '物料编码',
    width: '150'
  },
  {
    prop: 'itemName',
    label: '物料名称',
    width: '150'
  },
  {
    prop: 'specification',
    label: '规格型号'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位编号'
  },
  {
    label: '物料/产品',
    slot: 'itemOrProduct',
    width: '100'
  },
  {
    prop: 'itemTypeName',
    label: '所属分类',
    width: '100'
  },
  {
    prop: 'createTime',
    label: '创建时间',
    width: '180'
  }
]

// 客户选择表单头部
export const columnClient: Table.Column[] = [
  {
    prop: 'clientCode',
    label: '客户编码'
    // width: '120'
  },
  {
    prop: 'clientName',
    label: '客户名称'
  },
  {
    prop: 'clientNick',
    label: '客户简称'
  },
  {
    slot: 'clientType',
    label: '客户类型'
  },
  {
    prop: 'tel',
    label: '客户电话',
    width: '120'
  },
  {
    prop: 'contact1',
    label: '联系人'
  },
  {
    prop: 'contact1Tel',
    label: '联系人-电话',
    width: '120'
  }
]

// BOM组成表格头部
export const columnBom: Table.Column[] = [
  {
    prop: 'itemCode',
    label: 'BOM物料编号'
  },
  {
    prop: 'itemName',
    label: 'BOM物料名称'
  },
  {
    prop: 'itemSpc',
    label: '规格型号'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    slot: 'itemOrProduct',
    label: '物料/产品'
  },
  {
    prop: 'quantity',
    label: '预计使用量'
  }
]

// 物料需求表格头部
export const columnMaterialModel: Table.Column[] = [
  {
    prop: 'bomItemCode',
    label: '物料编号'
  },
  {
    prop: 'bomItemName',
    label: '物料名称'
  },
  {
    prop: 'bomItemSpec',
    label: '规格型号'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    slot: 'itemOrProduct',
    label: '物料/产品'
  },
  {
    prop: 'quantity',
    label: '预计使用量'
  }
]
