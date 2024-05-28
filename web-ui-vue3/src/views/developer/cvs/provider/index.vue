<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="" prop="provide">
        <el-input
          v-model="queryParams.provide"
          clearable
          placeholder="请输入服务商"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="account">
        <el-input
          v-model="queryParams.account"
          clearable
          placeholder="请输入账号名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="账号状态">
          <el-option
            v-for="(item, index) in COMMON_STATUS_ENUMS"
            :key="index"
            :label="item.label"
            :value="item.key"
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
          v-hasPermi="['developer:cloud-service:account:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column align="left" label="云服务商" prop="provide" />
      <el-table-column align="left" label="登录账号" prop="account" />
      <el-table-column align="left" label="区域编号" prop="regionId" />
      <el-table-column align="center" label="状态" prop="status" width="80">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleChangeStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button
            v-hasPermi="['developer:cloud-service:account:update']"
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            >修改
          </el-button>
          <el-button
            v-hasPermi="['developer:cloud-service:account:remove']"
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />

    <!-- 表单弹窗：添加/修改 -->
    <ProviderForm ref="formRef" @success="getList" />
  </ContentWrap>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/manager/developer/cvs/provider'

import { COMMON_STATUS_ENUM, COMMON_STATUS_ENUMS } from '@/utils/enums'
import ProviderForm from './ProviderForm.vue'

defineOptions({ name: 'CloudServiceAccount' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  provide: undefined,
  status: undefined,
  account: undefined
})

const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.getPage(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 修改状态操作 */
const handleChangeStatus = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === COMMON_STATUS_ENUM.DISABLE ? '禁用' : '启用'
    await message.confirm('确认要' + text + '云服务账号 "' + row.name + '"?', t('common.reminder'))
    await HTTP.updateData({ id: row.id, status: row.status })
    message.success(text + '成功')
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
      row.status === COMMON_STATUS_ENUM.DISABLE
        ? COMMON_STATUS_ENUM.ENABLE
        : COMMON_STATUS_ENUM.DISABLE
  }
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
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
