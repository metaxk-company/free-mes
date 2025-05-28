import { INSPECTION_METHOD } from '@/utils/const'

// 客户端搜索条件
// number检验标准编号，recNumber到货通知单编号，method检验方式
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '检验编码',
    prop: 'number',
    placeholder: '请输入检验编码'
  },
  // {
  //   type: 'input',
  //   label: '到货通知单编号',
  //   prop: 'recNumber',
  //   placeholder: '请输入到货通知单编号'
  // },
  {
    type: 'select',
    label: '检验方式',
    prop: 'method',
    placeholder: '请选择检验方式',
    options: INSPECTION_METHOD
  }
])

// 工序弹框搜索

export const searchProcessConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '物料编码',
    prop: 'itemCode',
    placeholder: '请输入物料编码'
  },
  {
    type: 'input',
    label: '物料名称',
    prop: 'itemName',
    placeholder: '请输入物料名称'
  }
  // {
  //   type: 'input',
  //   label: '供应商名称',
  //   prop: 'vendorName',
  //   placeholder: '请输入供应商名称'
  // }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '检验编码'
  },
  {
    prop: 'name',
    label: '检验名称'
  },
  // {
  //   prop: 'recNumber',
  //   label: '到货通知单编号'
  // },
  // {
  //   prop: 'processName',
  //   label: '物料名称'
  // },
  // {
  //   prop: '',
  //   label: '规格型号'
  // },

  {
    slot: 'method',
    label: '检验方式'
  },
  {
    prop: 'version',
    label: '检验版本'
  },
  {
    slot: 'enableFlag',
    label: '是否启用'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '230'
  }
]

export const tableProcessColumns: Table.Column[] = [
  {
    label: '物料编码',
    prop: 'itemCode',
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
  }
]

export const tableTestingItemsColumns: Table.Column[] = [
  {
    slot: 'itemName',
    label: '检测名称'
  },
  {
    slot: 'itemStandard',
    label: '检测标准'
  },
  {
    slot: 'itemDevice',
    label: '检测器具'
  },
  {
    slot: 'remark',
    label: '检验描述'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
