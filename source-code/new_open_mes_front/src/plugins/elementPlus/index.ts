import type { App } from 'vue'
// 需要全局引入一些组件，如ElScrollbar，不然一些下拉项样式有问题
import { ElLoading, ElScrollbar, ElButton, ElTableColumn, ElSelect } from 'element-plus'

const plugins = [ElLoading]

const components = [ElScrollbar, ElButton]

//  获取组件的props
const TableColumnProps = ElTableColumn.props

// 全局el-table-column设置
TableColumnProps.align = { type: String, default: 'center' } // 居中
TableColumnProps.showOverflowTooltip = { type: Boolean, default: true } // 文本溢出

// 全局ElSelect 设置
ElSelect.props.filterable = { type: Boolean, default: true } // 筛选选项默认为true

export const setupElementPlus = (app: App<Element>) => {
  plugins.forEach((plugin) => {
    app.use(plugin)
  })

  components.forEach((component) => {
    app.component(component.name, component)
  })
}
