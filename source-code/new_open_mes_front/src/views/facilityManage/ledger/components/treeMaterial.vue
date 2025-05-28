<template>
  <div>
    <el-input v-model="treeMaterialForm.machineryTypeName" placeholder="请输入分类名称" clearable />
  </div>
  <div>
    <el-tree
      ref="treeRef"
      :data="treeMaterialForm.treeData"
      :props="treeMaterialForm.treeProps"
      @node-click="handleNodeClick"
      :filter-node-method="filterNode"
    />
  </div>
</template>

<script setup lang="ts">
import { watch, ref } from 'vue'
import { ElTree } from 'element-plus'

const treeRef = ref<InstanceType<typeof ElTree>>()

const props = defineProps({
  treeMaterialForm: {
    type: Object,
    default: () => {}
  }
})
const emit = defineEmits(['nodeClick'])

watch(props.treeMaterialForm, (newValue) => {
  treeRef.value!.filter(newValue.machineryTypeName)
})

// 树型列表的点击事件
const handleNodeClick = (data) => {
  emit('nodeClick', data.id)
}

// 过滤器节点
const filterNode = (value: string, data: Tree) => {
  if (!value) return true
  return data.machineryTypeName.includes(value)
}
</script>

<style scoped lang="scss"></style>
