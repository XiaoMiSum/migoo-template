<script lang="ts" setup>
import * as HTTP from '@/api/developer/cvs/provider'

defineOptions({ name: 'ProviderForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const form = ref({
  id: undefined,
  provide: 'ALI_CLOUD',
  account: '',
  token: '',
  secret: '',
  regionId: ''
})
const rules = reactive({
  provide: [{ required: true, message: '云服务商不能为空', trigger: 'blur' }],
  account: [{ required: true, message: '登录账号不能为空', trigger: 'blur' }],
  token: [{ required: true, message: 'token不能为空', trigger: 'blur' }],
  secret: [{ required: true, message: 'secret不能为空', trigger: 'blur' }],
  regionId: [{ required: true, message: '区域编号不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  visible.value = true
  title.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      form.value = await HTTP.getData(id)
    } finally {
      formLoading.value = false
    }
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
    const data = form.value
    if (formType.value === 'create') {
      await HTTP.addData(data)
      message.success(t('common.createSuccess'))
    } else {
      await HTTP.updateData(data)
      message.success(t('common.updateSuccess'))
    }
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
    provide: 'ALI_CLOUD',
    account: '',
    token: '',
    secret: '',
    regionId: ''
  }
  formRef.value?.resetFields()
}
</script>

<template>
  <!-- 添加或修改岗位对话框 -->
  <Dialog v-model="visible" :title="title">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="云服务商" prop="provide">
        <el-input v-model="form.provide" placeholder="请输入云服务商" />
      </el-form-item>
      <el-form-item label="登录账号" prop="account">
        <el-input v-model="form.account" placeholder="请输入登录账号" />
      </el-form-item>
      <el-form-item label="token" prop="token">
        <el-input v-model="form.token" placeholder="请输入token" />
      </el-form-item>
      <el-form-item label="secret" prop="secret">
        <el-input v-model="form.secret" placeholder="请输入secret" />
      </el-form-item>
      <el-form-item label="区域编号" prop="regionId">
        <el-input v-model="form.regionId" placeholder="请输入regionId" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<style lang="scss" scoped></style>
