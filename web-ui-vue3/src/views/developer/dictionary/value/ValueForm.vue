<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-form-item label="数据标签" prop="label">
        <el-input v-model="formData.label" placeholder="请输入数据标签" />
      </el-form-item>
      <el-form-item label="数据键值" prop="value">
        <el-input v-model="formData.value" placeholder="请输入数据键值" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
            :value="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="显示排序" prop="sort">
        <el-input-number v-model="formData.sort" :min="0" controls-position="right" />
      </el-form-item>
      <el-form-item label="颜色类型" prop="colorType">
        <el-select v-model="formData.colorType">
          <el-option
            v-for="item in colorTypeOptions"
            :key="item.value"
            :label="item.label + '(' + item.value + ')'"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import { getIntDictOptions, DICT_TYPE } from '@/utils/dictionary'
import * as DictDataApi from '@/api/developer/dictionary/value'

defineOptions({ name: 'SystemDictDataForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sort: undefined,
  label: '',
  value: '',
  status: 1,
  dictCode: '',
  colorType: ''
})
const formRules = reactive({
  label: [{ required: true, message: '数据标签不能为空', trigger: 'blur' }],
  value: [{ required: true, message: '数据键值不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '数据顺序不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

// 数据标签回显样式
const colorTypeOptions = readonly([
  {
    value: 'default',
    label: '默认'
  },
  {
    value: 'primary',
    label: '主要'
  },
  {
    value: 'success',
    label: '成功'
  },
  {
    value: 'info',
    label: '信息'
  },
  {
    value: 'warning',
    label: '警告'
  },
  {
    value: 'danger',
    label: '危险'
  }
])

/** 打开弹窗 */
const open = async (type: string, data?: any, dictCode?: string) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (dictCode) {
    formData.value.dictCode = dictCode
  }
  // 修改时，设置数据
  if (data) {
    formLoading.value = true
    formData.value = { ...data }
    formLoading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    if (formType.value === 'create') {
      await DictDataApi.addData(data)
      message.success(t('common.createSuccess'))
    } else {
      await DictDataApi.updateData(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    sort: undefined,
    label: '',
    value: '',
    status: 1,
    dictCode: '',
    colorType: ''
  }
  formRef.value?.resetFields()
}
</script>
@/utils/dictionary
