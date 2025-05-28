<template>
  <vxe-modal v-bind="getBindValue" destroy-on-close show-zoom resize transfer @close="handleClose">
    <template v-if="slots.header" #header>
      <slot name="header"></slot>
    </template>
    <ElScrollbar>
      <template v-if="slots.default" #default>
        <slot name="default"></slot>
      </template>
    </ElScrollbar>
    <template v-if="slots.corner" #corner>
      <slot name="corner"></slot>
    </template>
    <template v-if="slots.footer" #footer>
      <slot name="footer"></slot>
    </template>
  </vxe-modal>
</template>

<script setup lang="ts">
import { propTypes } from '@/utils/propTypes'
const slots = useSlots()

const props = defineProps({
  id: propTypes.string.def('model_1'),
  modelValue: propTypes.bool.def(false),
  fullscreen: propTypes.bool.def(false),
  loading: propTypes.bool.def(false),
  title: propTypes.string.def('弹窗'),
  width: propTypes.string.def('40%'),
  height: propTypes.string,
  minWidth: propTypes.string.def('460'),
  minHeight: propTypes.string.def('320'),
  showFooter: propTypes.bool.def(true),
  maskClosable: propTypes.bool.def(false),
  escClosable: propTypes.bool.def(false)
})

const emit = defineEmits(['beforeClose'])

// 点击关闭按钮时会触发该事件
const handleClose = () => {
  emit('beforeClose')
}

const getBindValue = computed(() => {
  const attrs = useAttrs()
  const obj = { ...attrs, ...props }
  return obj
})
</script>

<style lang="scss" scoped>
.vxe-modal--wrapper {
  color: red !important;
  z-index: 9999999;
}
</style>
