<template>
  <el-input v-model="treeMaterialForm.classifyName" placeholder="请输入分类名称" clearable />
  <el-tree
    ref="treeRef"
    :data="treeMaterialForm.treeData"
    :props="treeMaterialForm.treeProps"
    @node-click="handleNodeClick"
    :filter-node-method="filterNode"
  />
</template>

<script setup lang="ts">
import { watch, ref } from 'vue'
import { ElTree } from 'element-plus'

const treeRef = ref<InstanceType<typeof ElTree>>()
const emit = defineEmits(['nodeClick'])

const props = defineProps({
  treeMaterialForm: {
    type: Object,
    default: () => {}
  }
})

watch(props.treeMaterialForm, (newValue) => {
  treeRef.value!.filter(newValue.classifyName)
})

// 树型列表的点击事件
const handleNodeClick = (data) => {
  emit('nodeClick', data.id)
}

// 过滤器节点
const filterNode = (value: string, data: Tree) => {
  if (!value) return true
  return data.label.includes(value)
}
</script>

<style scoped lang="scss"></style>
