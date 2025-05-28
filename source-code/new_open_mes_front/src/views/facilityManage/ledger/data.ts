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
    label: '删除',
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
    label: '设备编码',
    slot: 'machineryCode'
  },
  {
    prop: 'machineryName',
    label: '设备名称',
    width: '120'
  },
  {
    prop: 'machineryTypeName',
    label: '所属分类',
    width: '120'
  },
  {
    prop: 'workshopName',
    label: '所属车间',
    width: '120'
  },
  {
    label: '设备状态',
    prop: 'status'
  },
  {
    label: '位置',
    prop: 'location',
    width: '100'
  },
  {
    prop: 'createTime',
    label: '创建时间',
    width: '180'
  },
  {
    prop: 'purchasingTime',
    label: '购买日期',
    width: '180'
  },
  {
    prop: 'pclModel',
    label: '型号',
    width: '180'
  },
  {
    prop: 'capacity',
    label: '产能',
    width: '180'
  },
  {
    prop: 'updater',
    label: '修改人'
  },
  {
    prop: 'updateTime',
    label: '修改时间',
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
    slot: 'itemCode'
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
    width: '140'
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
