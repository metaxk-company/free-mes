<template>
  <ContentWrap>
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" />
    <Table
      v-model:pageSize="tableObject.pageSize"
      v-model:currentPage="tableObject.currentPage"
      :columns="allSchemas.tableColumns"
      :data="tableObject.tableList"
      @register="register"
      :loading="tableObject.loading"
      :pagination="{ total: tableObject.total }"
    >
      <template #action>
        <el-button>21212</el-button>
      </template>
    </Table>
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { Table } from '@/components/Table'
import { Search } from '@/components/Search'
import { CrudSchema, useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { useTable } from '@/hooks/web/useTable'

const crudSchemas = reactive<CrudSchema[]>([
  {
    field: 'title',
    label: '标题',
    search: {
      show: true
    },
    form: {
      colProps: {}
    },
    detail: {
      span: 24
    }
  },
  {
    field: 'display_time',
    label: '创建时间',
    form: {
      component: 'DatePicker',
      componentProps: {
        type: 'datetime',
        valueFormat: 'YYYY-MM-DD HH:mm:ss'
      }
    }
  },
  {
    field: 'author',
    label: '作者'
  },
  {
    field: 'action',
    width: '260px',
    label: '操作',
    form: {
      show: false
    },
    detail: {
      show: false
    }
  }
])

interface TableData {
  id: string
  author: string
  title: string
  content: string
  importance: number
  display_time: string
  pageviews: number
}

const { register, tableObject, methods } = useTable<TableData>()
const { getList, setSearchParams } = methods
const { allSchemas } = useCrudSchemas(crudSchemas)
</script>

<style scoped lang="scss"></style>
