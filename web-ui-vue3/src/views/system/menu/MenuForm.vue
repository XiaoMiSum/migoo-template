<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-form-item label="上级菜单">
        <el-tree-select
          v-model="formData.parentId"
          :data="menuTree"
          :default-expanded-keys="[0]"
          :props="defaultProps"
          check-strictly
          node-key="id"
        />
      </el-form-item>
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="formData.name" clearable placeholder="请输入菜单名称" />
      </el-form-item>
      <el-form-item label="菜单类型" prop="type">
        <el-radio-group v-model="formData.type">
          <el-radio-button
            v-for="item in getIntDictOptions(DICT_TYPE.SYSTEM_MENU_TYPE)"
            :key="item.value"
            :value="item.value"
            border
          >
            {{ item.label }}
          </el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="formData.type !== 3" label="菜单图标">
        <IconSelect v-model="formData.icon" clearable />
      </el-form-item>
      <el-form-item v-if="formData.type !== 3" label="路由地址" prop="path">
        <template #label>
          <Tooltip
            message="访问的路由地址，如：`user`。如需外网地址时，则以 `http(s)://` 开头"
            titel="路由地址"
          />
        </template>
        <el-input v-model="formData.path" clearable placeholder="请输入路由地址" />
      </el-form-item>
      <el-form-item v-if="formData.type === 2" label="组件地址" prop="component">
        <el-input v-model="formData.component" clearable placeholder="例如说：system/user/index" />
      </el-form-item>
      <el-form-item v-if="formData.type === 2" label="组件名字" prop="componentName">
        <el-input v-model="formData.componentName" clearable placeholder="例如说：SystemUser" />
      </el-form-item>
      <el-form-item v-if="formData.type !== 1" label="权限标识" prop="permission">
        <template #label>
          <Tooltip
            message="Controller 方法上的权限字符，如：@PreAuthorize(`@ss.hasPermission('system:user:list')`)"
            titel="权限标识"
          />
        </template>
        <el-input v-model="formData.permission" clearable placeholder="请输入权限标识" />
      </el-form-item>
      <el-form-item label="显示排序" prop="sort">
        <el-input-number v-model="formData.sort" :min="0" clearable controls-position="right" />
      </el-form-item>
      <el-form-item label="菜单状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="item in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="item.value"
            :value="item.value"
            border
          >
            {{ item.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="formData.type !== 3" label="显示状态" prop="visible">
        <template #label>
          <Tooltip message="选择隐藏时，路由将不会出现在侧边栏，但仍然可以访问" titel="显示状态" />
        </template>
        <el-radio-group v-model="formData.visible">
          <el-radio key="true" :value="true" border>显示</el-radio>
          <el-radio key="false" :value="false" border>隐藏</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="formData.type !== 3" label="总是显示" prop="alwaysShow">
        <template #label>
          <Tooltip
            message="选择不是时，当该菜单只有一个子菜单时，不展示自己，直接展示子菜单"
            titel="总是显示"
          />
        </template>
        <el-radio-group v-model="formData.alwaysShow">
          <el-radio key="true" :value="true" border>总是</el-radio>
          <el-radio key="false" :value="false" border>不是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="formData.type === 2" label="缓存状态" prop="keepAlive">
        <template #label>
          <Tooltip
            message="选择缓存时，则会被 `keep-alive` 缓存，必须填写「组件名称」字段"
            titel="缓存状态"
          />
        </template>
        <el-radio-group v-model="formData.keepAlive">
          <el-radio key="true" :value="true" border>缓存</el-radio>
          <el-radio key="false" :value="false" border>不缓存</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import * as HTTP from '@/api/system/menu'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
import { defaultProps, handleTree } from '@/utils/tree'
import { getIntDictOptions, DICT_TYPE } from '@/utils/dictionary'
import { CommonStatus, SystemMenuType } from '@/utils/constants'

defineOptions({ name: 'SystemMenuForm' })

const { wsCache } = useCache()
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: 0,
  name: '',
  permission: '',
  type: SystemMenuType.DIR,
  sort: Number(undefined),
  parentId: 0,
  path: '',
  icon: '',
  component: '',
  componentName: '',
  status: CommonStatus.ENABLE,
  visible: true,
  keepAlive: true,
  alwaysShow: true
})
const formRules = reactive({
  name: [{ required: true, message: '菜单名称不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '菜单顺序不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number, parentId?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (parentId) {
    formData.value.parentId = parentId
  }
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await HTTP.getData(id)
    } finally {
      formLoading.value = false
    }
  }
  // 获得菜单列表
  await getTree()
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
    if (formData.value.type === SystemMenuType.DIR || formData.value.type === SystemMenuType.MENU) {
      if (!isExternal(formData.value.path)) {
        if (formData.value.parentId === 0 && formData.value.path.charAt(0) !== '/') {
          message.error('路径必须以 / 开头')
          return
        } else if (formData.value.parentId !== 0 && formData.value.path.charAt(0) === '/') {
          message.error('路径不能以 / 开头')
          return
        }
      }
    }
    const data = formData.value as unknown as HTTP.MenuVO
    if (formType.value === 'create') {
      await HTTP.addData(data)
      message.success(t('common.createSuccess'))
    } else {
      await HTTP.updateData(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
    // 清空，从而触发刷新
    wsCache.delete(CACHE_KEY.ROLE_ROUTERS)
  }
}

/** 获取下拉框[上级菜单]的数据  */
const menuTree = ref<Tree[]>([]) // 树形结构
const getTree = async () => {
  menuTree.value = []
  const res = await HTTP.listSimpleMenus()
  let menu: Tree = { id: 0, name: '主类目', children: [] }
  menu.children = handleTree(res)
  menuTree.value.push(menu)
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: 0,
    name: '',
    permission: '',
    type: SystemMenuType.DIR,
    sort: Number(undefined),
    parentId: 0,
    path: '',
    icon: '',
    component: '',
    componentName: '',
    status: CommonStatus.ENABLE,
    visible: true,
    keepAlive: true,
    alwaysShow: true
  }
  formRef.value?.resetFields()
}

/** 判断 path 是不是外部的 HTTP 等链接 */
const isExternal = (path: string) => {
  return /^(https?:|mailto:|tel:)/.test(path)
}
</script>
