<script lang="ts" setup>
import * as HTTP from '@/api/developer/cvs/machine'

defineOptions({ name: 'JobForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const form = ref({
  id: undefined,
  hostname: ''
})
const rules = reactive({
  hostname: [{ required: true, message: '主机名称不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (data?: any) => {
  visible.value = true
  title.value = t('action.update')
  resetForm()
  // 修改时，设置数据
  try {
    form.value = { ...data }
  } finally {
    formLoading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交按钮 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const { id, hostname } = form.value
    await HTTP.updateData({ id, hostname })
    message.success(t('common.updateSuccess'))
    visible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  form.value = {
    id: undefined,
    hostname: ''
  }
  formRef.value?.resetFields()
}
</script>

<template>
  <!-- 添加或修改岗位对话框 -->
  <Dialog v-model="visible" :title="title">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col>
          <el-form-item label="主机名称" prop="hostname">
            <el-input v-model="form.hostname" placeholder="请输入主机名称" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<style lang="scss" scoped></style>
