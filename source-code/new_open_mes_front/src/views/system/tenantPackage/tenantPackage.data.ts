import type { VxeCrudSchema } from '@/hooks/web/useVxeCrudSchemas'
const { t } = useI18n() // 国际化

// 表单校验
export const rules = reactive({
  name: [required],
  id: [required],
  type: [required],
  remark: [required],
  status: [required],
  menuIds: [required]
})

// CrudSchema
const crudSchemas = reactive<VxeCrudSchema>({
  primaryKey: 'id',
  primaryType: 'id',
  primaryTitle: '套餐编号',
  action: true,
  columns: [
    {
      title: '套餐名称',
      field: 'name',
      isSearch: true
    },
    {
      title: t('common.status'),
      field: 'status',
      dictType: DICT_TYPE.COMMON_STATUS,
      dictClass: 'number',
      isSearch: true
    },
    {
      title: '菜单权限',
      field: 'menuIds',
      isTable: false,
      form: {
        colProps: {
          span: 24
        }
      }
    },
    {
      title: t('form.remark'),
      field: 'remark',
      isTable: false,
      isSearch: true,
      form: {
        component: 'Input',
        componentProps: {
          type: 'textarea',
          rows: 4
        },
        colProps: {
          span: 24
        }
      }
    },
    {
      title: '创建时间',
      field: 'createTime',
      formatter: 'formatDate',
      isForm: false,
      search: {
        show: true,
        itemRender: {
          name: 'XDataTimePicker'
        }
      }
    }
  ]
})
export const { allSchemas } = useVxeCrudSchemas(crudSchemas)
