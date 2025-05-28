import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
const { t } = useI18n() // 国际化

// 表单校验
export const rules = reactive({
  name: [required],
  code: [required],
  sort: [required]
})

// 增删改查 CrudSchema 配置
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id',
  primaryType: 'id',
  primaryTitle: '岗位编号',
  action: true,
  columns: [
    {
      title: '岗位名称',
      field: 'name',
      isSearch: true
    },
    {
      title: '岗位编码',
      field: 'code',
      isSearch: true
    },
    {
      title: '岗位顺序',
      field: 'sort',
      form: {
        component: 'InputNumber'
      }
    },
    {
      title: t('common.status'),
      field: 'status',
      dictType: DICT_TYPE.COMMON_STATUS,
      dictClass: 'number',
      isSearch: true
    },
    {
      title: '备注',
      field: 'remark',
      isTable: false
    },
    {
      title: t('common.createTime'),
      field: 'createTime',
      formatter: 'formatDate',
      isForm: false,
      table: {
        width: 180
      }
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
