import { reactive } from 'vue'

export const BTN_OPERATION: Btn.IButton[] = reactive([
  {
    type: 'primary',
    icon: 'plus',
    state: 'add',
    label: '新增',
    disabled: false,
    permissions: []
  },
  {
    type: 'success',
    icon: 'edit',
    state: 'edit',
    label: '修改',
    disabled: true,
    permissions: []
  },
  {
    type: 'danger',
    icon: 'delete',
    state: 'remove',
    label: '批量删除',
    disabled: true,
    permissions: []
  },
  {
    type: 'info',
    icon: 'upload2',
    state: 'import',
    label: '导入',
    disabled: false,
    permissions: []
  },
  {
    type: 'warning',
    icon: 'ep:zoom-in',
    state: 'export',
    label: '导出',
    disabled: false,
    permissions: []
  }
])

export const columnState: Table.Column[] = [
  {
    label: '物料编码',
    slot: 'itemCode',
    width: '150'
  },
  {
    prop: 'itemName',
    label: '物料名称',
    width: '190'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'model',
    label: '型号',
    width: '120'
  },
  {
    prop: 'vendor',
    label: '供应商',
    width: '180'
  },
  {
    prop: 'lineType',
    label: '线别 '
  },
  {
    prop: 'effectiveDate',
    label: '有效日期',
    width: '180'
  },
  {
    prop: 'kind',
    label: '品类'
  },
  {
    prop: 'purchasePrice',
    label: '采购价'
  },
  {
    prop: 'salePrice',
    label: '销售价'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    prop: 'itemOrProduct',
    label: '物料/产品',
    slot: 'itemOrProduct',
    width: '100'
  },
  {
    prop: 'itemTypeName',
    label: '所属分类',
    width: '140'
  },
  {
    prop: 'enableFlag',
    label: '是否启用',
    slot: 'enableFlag'
  },
  {
    prop: 'safeStockFlag',
    label: '安全库存',
    slot: 'safeStockFlag',
    width: '120'
  },
  {
    prop: 'createTime',
    label: '创建时间',
    width: '180'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]

export const columnStateMini: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '物料编码',
    slot: 'itemCode',
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
    prop: 'itemOrProduct',
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

export const bomColumnState: Table.Column[] = [
  {
    prop: 'bomItemCode',
    label: '物料编码',
    width: '150'
  },
  {
    prop: 'bomItemName',
    label: '物料名称',
    width: '150'
  },
  {
    prop: 'bomItemSpec',
    label: '规格'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    prop: 'quantity',
    label: '使用比例'
  },
  {
    prop: 'remark',
    label: '备注'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]

export const RADIO_STATE = [
  { label: '是', key: 'Y' },
  { label: '否', key: 'N' }
]
