// 自动生成客户代码
export const GENERATE_CLIENT_CODE = 'CLIENT_CODE'

export const OPEN = 'Y'
export const CLOSE = 'N'

// 是否启用
export const UN_KNOW_ENABLE = [
  {
    label: '是',
    value: OPEN
  },
  {
    label: '否',
    value: CLOSE
  }
]

export const UN_KNOW_STATE = {
  [OPEN]: '是',
  [CLOSE]: '否'
}

export const PRODUCT_CLASS = {
  ITEM: '物料',
  PRODUCT: '产品'
}

// 工序关系
/**
 *S-to-S：当前工序开始生产，下一道工序才可开始生产
  F-to-F：当前工序结束生产，下一道工序才可结束生产
  S-to-F：当前工序开始生产，下一道工序才可结束生产
  F-to-S：当前工序结束生产，下一道工序才可开始生产
 */
export const PROCESS_RELATION = [
  {
    label: 'S-to-S',
    value: 'SS'
  },
  {
    label: 'S-to-F',
    value: 'SF'
  },
  {
    label: 'F-to-S',
    value: 'FS'
  },
  {
    label: 'F-to-F',
    value: 'FF'
  }
]

export const PROCESS_RELATION_STATE = {
  SS: 'S-to-S',
  SF: 'S-to-F',
  FS: 'F-to-S',
  FF: 'F-to-F'
}

export const PRODUCE_STARTED = 'STARTED' // 开工
export const PRODUCE_PAUSED = 'PAUSED' // 暂停
export const PRODUCE_RESUMED = 'RESUMED' // 恢复
export const PRODUCE_FINISHED = 'FINISHED' // 报工
export const NO_STARTED = 'NoSTARTED' // 未开工

export const PRODUCE_STATE = {
  [PRODUCE_PAUSED]: '暂停',
  [PRODUCE_RESUMED]: '开工',
  [PRODUCE_FINISHED]: '报工',
  [PRODUCE_STARTED]: '开工',
  [NO_STARTED]: '未开工'
}

// 维修状态
export const MAINTAIN_STATE = {
  STOP: '停机',
  WORKING: '生产中',
  REPAIR: '维修中'
}

// 客户类型
export const CUSTOMER_TYPE = {
  ENTERPRISE: '企业用户',
  PERSON: '个人用户'
}

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

export const SOURCE_TYPE_TEXT = {
  ORDER: '客户订单',
  STORE: '库存备货'
}

export const WORK_STATION = '1' // 工作站
export const EQUIPMENT = '2' // 设备

export const WORK_STATION_STYLE = [
  {
    label: WORK_STATION,
    value: '工作站',
    disabled: false
  }
  // {
  //   disabled: true,
  //   label: EQUIPMENT,
  //   value: '设备'
  // }
]

// 审批状态
export const APPROVAL_STATUS = {
  NOSCHEDUL: '未排产',
  SCHEDUL: '已排产',
  COMPLETED: '已完成'
}

// 设备状态
export const EQUIPMENT_STATUS = [
  {
    label: '开工',
    value: 'STARTED'
  },
  {
    label: '暂停',
    value: 'PAUSED'
  },
  {
    label: '报工',
    value: 'FINISHED'
  },
  {
    label: '未开工',
    value: 'NoSTARTED'
  }
]

export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'primary',
    icon: 'plus',
    state: 'save',
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
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    disabled: false,
    permissions: []
  },
  {
    type: 'primary',
    icon: 'Upload',
    state: 'upload',
    label: '导入',
    disabled: false,
    permissions: []
  }
])

// 质检方式
export const INSPECTION_METHOD = [
  {
    label: '全检',
    value: 'fullInspection'
  },
  {
    label: '抽检',
    value: 'casualInspection'
  },
  {
    label: '批量检',
    value: 'batchInspection'
  }
]

// 质检方式匹配的值
export const MARRY_INSPECTION_METHOD = {
  fullInspection: '全检',
  casualInspection: '抽检',
  batchInspection: '批量检'
}

// 检验练级分类
export const TEST_CLASSIFY = [
  {
    label: '一级分类',
    value: '一级分类'
  },
  {
    label: '二级分类',
    value: '二级分类'
  }
]
// 仓库分类
export const WAREHOUSE_LIST = [
  { label: '成品仓库', value: '成品仓库' },
  { label: '半成品仓库', value: '半成品仓库' },
  { label: '原材料', value: '原材料' }
]
// 数据协议
export const DATA_PROTOCOL = [
  {
    label: 'TCP/IP',
    value: 'TCP/IP'
  },
  {
    label: 'HTTP',
    value: 'HTTP'
  },
  {
    label: 'MOTT',
    value: 'MOTT'
  },
  {
    label: 'Modbus',
    value: 'Modbus'
  },
  {
    label: '蓝牙',
    value: 'BT'
  }
]

// 产品质量检测
export const QUALITY_STATUS = [
  {
    label: '合格',
    value: 1
  },
  {
    label: '不合格',
    value: 0
  },
  {
    label: 'NA',
    value: 2
  }
]

export const LINES_NAME = [
  {
    label: '铜线',
    value: '铜线'
  },
  {
    label: '铝线',
    value: '铝线'
  },
  {
    label: '铜包铝',
    value: '铜包铝'
  }
]

// 币种类型
export const CURRENCY_TYPE = [
  {
    label: '人民币',
    value: '人民币'
  }
]

// 铜税率
export const COPPER_TAXRATE_DATA = [
  {
    label: '3',
    value: '3'
  }
  // {
  //   label: '7',
  //   value: '7'
  // },
  // {
  //   label: '7.3',
  //   value: '7.3'
  // }
]

// 铝税率
export const AI_TAXRATE_DATA = [
  // {
  //   label: '5',
  //   value: '5'
  // },
  {
    label: '7',
    value: '7'
  }
  // {
  //   label: '7.3',
  //   value: '7.3'
  // }
]

export const TAXRATE_DATA = [
  {
    label: '5',
    value: '5'
  },
  {
    label: '7',
    value: '7'
  },
  {
    label: '7.3',
    value: '7.3'
  }
]

// 采购状态
export const PURCHASE_STATE = [
  {
    label: '采购申请',
    value: '采购申请'
  },
  {
    label: '领料申请',
    value: '领料申请'
  }
]

// 采购来源
export const PURCHASE_SOURCE = [
  {
    label: '采购',
    value: '采购'
  },
  {
    label: '生产',
    value: '生产'
  }
]

// 仓库状态
export const STORE_HOUSE_STATE = [
  {
    label: '已入库',
    value: '已入库'
  },
  {
    label: '待入库',
    value: '待入库'
  }
]

// 完成状态

export const FINISH_STATUS = [
  {
    label: '已完成',
    value: '已完成'
  },
  {
    label: '未完成',
    value: '未完成'
  }
]

// 出库来源
export const OUTBOUND_SOURCE = [
  {
    label: '销售',
    value: '销售'
  },
  {
    label: '仓库',
    value: '仓库'
  }
]

// 出库状态
export const WARE_HOUSE_STATUS = [
  {
    label: '已出库',
    value: '已出库'
  },
  {
    label: '已生成应收',
    value: '已生成应收'
  },
  {
    label: '已退库',
    value: '已退库'
  },
  {
    label: '待出库',
    value: '待出库'
  },
  {
    label: '打印送货单',
    value: '打印送货单'
  },
  {
    label: '打印送货明细',
    value: '打印送货明细'
  },
  {
    label: '退货',
    value: '退货'
  }
]
export const STORE_HOUSE_STATUS = {
  1: '已入库',
  2: '已出库',
  3: '已重包'
}
export const STORE_HOUSE_STATUS_INFO = [
  {
    label: '已入库',
    value: '1'
  },
  {
    label: '已出库',
    value: '2'
  },
  {
    label: '已重包',
    value: '3'
  }
]

export const AXLE_NUM_STATUS = {
  oneAxle: '1轴',
  twoAxle: '2轴',
  threeAxle: '3轴',
  fourAxle: '4轴',
  fiveAxle: '5轴',
  sixAxle: '6轴',
  sevenAxle: '7轴',
  eightAxle: '8轴'
}

// 轴数
export const AXLE_NUM = Object.entries(AXLE_NUM_STATUS).map(([value, label]) => ({
  value,
  label
}))

// 客户退货状态
export const RETURN_STARUS = [
  {
    label: '直接入库',
    value: '1'
  },
  {
    label: '重包',
    value: '2'
  },
  {
    label: '报废',
    value: '3'
  }
]
