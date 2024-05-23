<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryForm" :inline="true" :model="queryParams">
      <el-form-item prop="name">
        <el-input
          v-model="queryParams.name"
          clearable
          placeholder="请输入菜单名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="菜单状态">
          <el-option
            v-for="item in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:menu:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain type="danger" @click="toggleExpandAll">
          <Icon class="mr-5px" icon="ep:sort" />
          展开/折叠
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain @click="refreshMenu">
          <Icon class="mr-5px" icon="ep:refresh" />
          刷新菜单缓存
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <ContentWrap>
    <Table
      v-if="refreshTable"
      row-key="id"
      :columns="tableColumns"
      :loading="tableObject.loading"
      :data="list"
      :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <template #action="{ row }">
        <div class="flex items-center justify-center">
          <el-button link type="primary" @click="openForm('update', row.id)">修改 </el-button>
          <el-button link type="primary" @click="openForm('create', undefined, row.id)"
            >新增
          </el-button>
          <el-button link type="danger" @click="handleDelete(row.id)">删除 </el-button>
        </div>
      </template>
    </Table>
  </ContentWrap>
  <MenuForm ref="formRef" @success="getList" />
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/system/menu'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dictionary'
import { handleTree } from '@/utils/tree'
import { t } from '@/hooks/web/useI18n'
import MenuForm from '@/views/system/menu/MenuForm.vue'

import { tableColumns } from './Menu.d'

const { tableMethods, tableObject } = useTable({
  getListApi: HTTP.listData
})

const { getList } = tableMethods

const { wsCache } = useCache()
const message = useMessage() // 消息弹窗
const loading = ref(true) // 列表的加载中
const list = ref<any>([]) // 列表的数据
const queryForm = ref() // 搜索的表单
const isExpandAll = ref(false) // 是否展开，默认全部折叠
const refreshTable = ref(true) // 重新渲染表格状态
// 查询参数
const queryParams = reactive({
  name: undefined,
  status: undefined
})
/** 查询菜单列表 */
const getList2 = async () => {
  loading.value = true
  try {
    await getList()
    list.value = handleTree(tableObject.tableList)
  } finally {
    loading.value = false
  }
}
/** 搜索按钮操作 */
const handleQuery = () => {
  getList2()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryForm.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number, parentId?: number) => {
  formRef.value.open(type, id, parentId)
}

/** 展开/折叠操作 */
const toggleExpandAll = () => {
  refreshTable.value = false
  isExpandAll.value = !isExpandAll.value
  nextTick(() => {
    refreshTable.value = true
  })
}

/** 刷新菜单缓存按钮操作 */
const refreshMenu = async () => {
  try {
    await message.confirm('即将更新缓存刷新浏览器！', '刷新菜单缓存')
    // 清空，从而触发刷新
    wsCache.delete(CACHE_KEY.ROLE_ROUTERS)
    // 刷新浏览器
    location.reload()
  } catch {}
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.delData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList2()
  } catch {}
}

/** 初始化 **/
onMounted(() => {
  getList2()
})
</script>
