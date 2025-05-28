<template>
  <el-form ref="refProduct" :model="productFormData" :rules="rules" label-width="120px">
    <el-row>
      <el-col :span="8">
        <el-form-item label="物料编码" prop="itemCode">
          <el-input placeholder="请输入物料编码" v-model="productFormData.itemCode" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="物料名称" prop="itemName">
          <el-input
            v-model="productFormData.itemName"
            placeholder="请输入物料名称"
            clearable
            :disabled="dialogState === 'View'"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="是否启用">
          <el-radio-group v-model="productFormData.enableFlag" :disabled="dialogState === 'View'">
            <el-radio v-for="item in RADIO_STATE" :key="item.key" :label="item.key">{{
              item.label
            }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <el-form-item label="线别名称" prop="lineType">
          <el-select
            v-model="productFormData.lineType"
            placeholder="请选择线别名称"
            :disabled="dialogState === 'View'"
          >
            <el-option
              v-for="item in listLineType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="型号名称" prop="model">
          <el-select
            v-model="productFormData.model"
            placeholder="请选择型号名称"
            :disabled="dialogState === 'View'"
          >
            <el-option
              v-for="item in modelSelectList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="规格名称" prop="spec">
          <el-select
            v-model="productFormData.spec"
            placeholder="请选择规格名称"
            :disabled="dialogState === 'View'"
          >
            <el-option
              v-for="item in specSelectList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <el-form-item label="供应商">
          <el-select
            v-model="productFormData.vendor"
            placeholder="请选择供应商名称"
            :disabled="dialogState === 'View'"
          >
            <el-option
              v-for="item in supplierSelectList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="有效日期">
          <el-date-picker
            :disabled="dialogState === 'View'"
            v-model="productFormData.effectiveDate"
            type="date"
            placeholder="请选择有效日期"
            value-format="YYYY-MM-DD"
            format="YYYY-MM-DD"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="品类名称">
          <el-select
            v-model="productFormData.kind"
            placeholder="请选择品类名称"
            :disabled="dialogState === 'View'"
          >
            <el-option
              v-for="item in classSelectList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-divider />

    <el-row>
      <el-col :span="8">
        <el-form-item label="规格型号" prop="specification">
          <el-input
            v-model="productFormData.specification"
            type="textarea"
            autosize
            maxlength="500"
            :disabled="dialogState === 'View'"
            placeholder="请输入规格型号"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="产品单位" prop="unitOfMeasure">
          <el-select v-model="productFormData.unitOfMeasure" :disabled="dialogState === 'View'">
            <el-option
              v-for="item in measureOptions"
              :key="item.measureCode"
              :label="item.measureName"
              :value="item.measureCode"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="产品分类" prop="itemTypeId">
          <el-cascader
            :options="treeMaterialForm.treeData"
            :show-all-levels="false"
            :props="cascadeProps"
            v-model="productFormData.itemTypeId"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row />

    <el-row>
      <el-col :span="8">
        <el-form-item label="备注">
          <el-input
            :disabled="dialogState === 'View'"
            v-model="productFormData.remark"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="采购价">
          <el-input-number
            v-model="productFormData.purchasePrice"
            :disabled="dialogState === 'View'"
            placeholder="请输入采购价"
            :min="1"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="销售价">
          <el-input-number
            :min="1"
            v-model="productFormData.salePrice"
            :disabled="dialogState === 'View'"
            placeholder="请输入销售价"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="8">
        <el-form-item label="启用安全库存">
          <el-radio-group
            v-model="productFormData.safeStockFlag"
            :disabled="dialogState === 'View'"
          >
            <el-radio v-for="item in RADIO_STATE" :key="item.key" :label="item.key">{{
              item.label
            }}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row v-if="isSafeStockFlag">
      <el-col :span="8">
        <el-form-item label="最小库存量">
          <el-input-number
            :disabled="dialogState === 'View'"
            v-model="productFormData.minStock"
            :percision="2"
            :step="1"
            :min="1"
            placeholder="请输入最小安全库存量"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="最大库存量">
          <el-input-number
            :disabled="dialogState === 'View'"
            v-model="productFormData.maxStock"
            :percision="2"
            :min="1"
            :step="1"
            placeholder="请输入最大安全库存量"
          />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>

  <el-tabs type="border-card" v-if="dialogState !== 'addView'">
    <el-tab-pane label="BOM组成">
      <item-bom :dialogState="dialogState" :bom-params="productFormData.id" />
    </el-tab-pane>
    <!-- <el-tab-pane label="SOP">
      <sop-empty :dialogState="dialogState" />
    </el-tab-pane> -->
  </el-tabs>
</template>

<script setup lang="ts">
import { reactive, ref, nextTick, onMounted, watch } from 'vue'
import type { PropType } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { RADIO_STATE } from '../data'
import itemBom from './ItemBom.vue'
// import sopEmpty from './sopEmpty.vue'
import {
  getCreateCode,
  getListLineType,
  getSupplierSelectList,
  getModelSelectList,
  getSpecSelectList,
  getClassSelectList
} from '@/api/masterData/materialManage'

interface UnitOfMeasure {
  measureCode: string
  measureName: string
  primaryFlag: string
}

const props = defineProps({
  measureOptions: {
    type: Array as PropType<UnitOfMeasure[]>,
    default: () => []
  },
  productFormData: {
    type: Object,
    default: () => {}
  },
  treeMaterialForm: {
    type: Object,
    default: () => {}
  },
  dialogState: {
    type: String,
    default: () => ''
  }
})

/*
watch(
  () => props.productFormData,
  (newValue) => {
    if (newValue.lineType !== '' || newValue.model !== '' || newValue.spec !== '') {
      props.productFormData.itemName =
        newValue.lineType + '|' + newValue.model + '|' + newValue.spec
    }
  },
  { deep: true }
)

 */

const cascadeProps = ref({
  value: 'id',
  label: 'label',
  children: 'children',
  checkStrictly: true
})

const isSafeStockFlag = computed(() => props.productFormData.safeStockFlag === 'Y')

const refProduct = ref<FormInstance | any>()

const rules = reactive<FormRules>({
  itemCode: [
    { required: true, message: '物料/产品编码不能为空', trigger: 'blur' },
    {
      max: 64,
      message: '物料/产品编码长度必须小于64个字符',
      trigger: 'blur'
    }
  ],
  itemName: [{ required: true, message: '物料/产品名称不能为空', trigger: 'blur' }],
  unitOfMeasure: [{ required: true, message: '单位不能为空', trigger: 'blur' }],
  itemTypeId: [{ required: true, message: '产品分类不能为空', trigger: 'blur' }],
  lineType: [{ required: true, message: '线别名称不能为空', trigger: 'blur' }],
  salePrice: [{ required: true, message: '销售价不能为空', trigger: 'blur' }],
  purchasePrice: [{ required: true, message: '采购价不能为空', trigger: 'blur' }],
  // vendor: [{ required: true, message: '供应商名称不能为空', trigger: 'blur' }],
  model: [{ required: true, message: '选择型号名称', trigger: 'blur' }],
  spec: [{ required: true, message: '请选择规格名称', trigger: 'blur' }],
  kind: [{ required: true, message: '请选择品类名称', trigger: 'blur' }]
})

// 线别列表
const listLineType = ref<any>([])
// 供应商列表
const supplierSelectList = ref<any>([])
// 型号列表
const modelSelectList = ref<any>([])
//  规格列表
const specSelectList = ref<any>([])
// 品类数据字典列表
const classSelectList = ref<any>([])

// 自动生成物料编码标识
/*
const handleAutoGenChange = () => {
  getCreateCode('ITEM_CODE').then((res) => {
    nextTick(() => {
      // props.productFormData.itemCode = res
    })
  })
}
 */

// 弹框 - 确定
const handleSubmitForm = async () => {
  if (!refProduct?.value) return
  return await new Promise((resolve) => {
    refProduct.value.validate((valid) => {
      resolve(valid)
    })
  })
}

// 弹框 - 取消
const handleCancel = () => {
  if (!refProduct.value) return
  refProduct.value.resetFields()
}

const getListLineTypeAPI = () => {
  getListLineType().then((res) => {
    listLineType.value = res.map((item) => {
      return {
        value: item.label,
        label: item.labe
      }
    })
  })
}
const getSupplierSelectListAPI = () => {
  getSupplierSelectList().then((res) => {
    supplierSelectList.value = res.list.map((item) => {
      return {
        value: item.vendorName,
        label: item.vendorName
      }
    })
  })
}

const getModelSelectListAPI = () => {
  getModelSelectList().then((res) => {
    modelSelectList.value = res.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
  })
}
const getSpecSelectListAPI = () => {
  getSpecSelectList().then((res) => {
    specSelectList.value = res.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
  })
}
const getClassSelectListAPI = () => {
  getClassSelectList().then((res) => {
    classSelectList.value = res.map((item) => {
      return {
        value: item.label,
        label: item.label
      }
    })
  })
}

const info = () => {
  getListLineTypeAPI()
  getSupplierSelectListAPI()
  getModelSelectListAPI()
  getSpecSelectListAPI()
  getClassSelectListAPI()
  // handleAutoGenChange()
}

onMounted(() => {
  info()
})

defineExpose({
  handleSubmitForm,
  handleCancel
})
</script>

<style scoped lang="scss"></style>
