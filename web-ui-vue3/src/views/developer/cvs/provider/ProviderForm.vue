<script lang="ts" setup>
import { DICT_TYPE, getDictOptions } from '@/utils/dictionary'

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
  code: 'ALI_CLOUD',
  account: '',
  accessKeyId: '',
  accessKeySecret: '',
  region: ''
})
const rules = reactive({
  code: [{ required: true, message: '云服务商不能为空', trigger: 'blur' }],
  account: [{ required: true, message: '登录账号不能为空', trigger: 'blur' }],
  accessKeyId: [{ required: true, message: 'accessKeyId不能为空', trigger: 'blur' }],
  accessKeySecret: [{ required: true, message: 'accessKeySecret不能为空', trigger: 'blur' }],
  region: [{ required: true, message: '区域编号不能为空', trigger: 'blur' }]
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
    code: 'ALI_CLOUD',
    account: '',
    accessKeyId: '',
    accessKeySecret: '',
    region: ''
  }
  formRef.value?.resetFields()
}
</script>

<template>
  <!-- 添加或修改岗位对话框 -->
  <Dialog v-model="visible" :title="title">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="云服务商" prop="code">
        <el-select v-model="form.code" placeholder="请选择云服务商">
          <el-option
            v-for="(item, index) in getDictOptions(DICT_TYPE.INFRA_CVS_PROVIDER)"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-input v-model="form.code" placeholder="请输入云服务商" />
      </el-form-item>
      <el-form-item label="登录账号" prop="account">
        <el-input v-model="form.account" placeholder="请输入登录账号" />
      </el-form-item>
      <el-form-item label="accessKeyId" prop="accessKeyId">
        <el-input v-model="form.accessKeyId" placeholder="请输入accessKeyId" />
      </el-form-item>
      <el-form-item label="accessKeySecret" prop="accessKeySecret">
        <el-input v-model="form.accessKeySecret" placeholder="请输入accessKeySecret" />
      </el-form-item>
      <el-form-item label="区域编号" prop="region">
        <el-input v-model="form.region" placeholder="请输入区域编号" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<style lang="scss" scoped></style>
