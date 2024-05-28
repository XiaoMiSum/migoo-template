<script lang="ts" setup>
import * as HTTP from '@/api/developer/cvs/provider'
import MachineTree from './MachineTree.vue'
import MachineForm from './MachineForm.vue'

defineOptions({ name: 'CSVMachine' })

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

const handleSync = async () => {
  await HTTP.syncData()
  message.success('同步中，请稍后刷新数据')
}

/** 处理节点被点击 */
const handleNodeClick = async (data: any) => {
  if (data.children) {
    list.value = []
    return
  }
  queryParams.value.account = data.name
  await getList()
}

// 操作云服务器实例
const handleOption = async (id: any, option: string) => {
  await HTTP.optionCvs(id, option)
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
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

<template>
  <el-row :gutter="20">
    <!--云服务账号数据-->
    <el-col :span="4" :xs="24">
      <ContentWrap class="h-1/1">
        <MachineTree @node-click="handleNodeClick" />
      </ContentWrap>
    </el-col>
    <!--用户数据-->
    <el-col :span="20" :xs="24">
      <ContentWrap>
        <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
          <el-form-item prop="hostname">
            <el-input
              v-model="queryParams.hostname"
              class="!w-240px"
              clearable
              placeholder="请输入主机名称"
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">
              <Icon icon="ep:search" />
              搜索
            </el-button>
            <el-button @click="resetQuery">
              <Icon icon="ep:refresh" />
              重置
            </el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10">
          <el-col :span="1.5">
            <el-button plain type="primary" @click="handleSync">
              <Icon icon="ep:plus" />
              同步数据
            </el-button>
          </el-col>
        </el-row>
      </ContentWrap>

      <ContentWrap>
        <el-table v-loading="loading" :data="list">
          <el-table-column
            :show-overflow-tooltip="true"
            align="left"
            label="实例类型"
            prop="serverType"
          />
          <el-table-column
            :show-overflow-tooltip="true"
            align="left"
            label="主机名称"
            prop="hostname"
          />
          <el-table-column
            :show-overflow-tooltip="true"
            align="left"
            label="实例编号"
            prop="instanceId"
          />
          <el-table-column
            :show-overflow-tooltip="true"
            align="left"
            label="操作系统"
            prop="operateSystem"
          />
          <el-table-column align="center" label="状态" prop="status" width="100">
            <template #default="scope">
              <el-tag
                :disable-transitions="true"
                :type="scope.row.status === 'Running' ? 'success' : 'danger'"
                size="small"
              >
                {{ scope.row.status }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="过期时间" prop="expiredTime" width="180">
            <template #default="scope">
              <span>{{ scope.row.expiredTime }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作">
            <template #default="scope">
              <el-button
                v-hasPermi="['developer:cloud-service:server:update']"
                link
                type="primary"
                @click="openForm('update', scope.row.id)"
                >修改
              </el-button>
              <el-button
                v-if="scope.row.status === 'Running'"
                v-hasPermi="['developer:cloud-service:server:update']"
                link
                type="danger"
                @click="handleOption(scope.row.id, 'stop')"
                >关机
              </el-button>
              <el-button
                v-if="scope.row.status === 'Running'"
                v-hasPermi="['developer:cloud-service:server:update']"
                link
                type="danger"
                @click="handleOption(scope.row.id, 'restart')"
                >重启
              </el-button>
              <el-button
                v-if="scope.row.status === 'Stopped'"
                v-hasPermi="['developer:cloud-service:server:update']"
                link
                type="success"
                @click="handleOption(scope.row.id, 'start')"
                >开机
              </el-button>
              <el-button
                v-hasPermi="['developer:cloud-service:server:remove']"
                :disabled="scope.row.status === 'Running'"
                link
                type="danger"
                @click="handleDelete(scope.row)"
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
      </ContentWrap>
    </el-col>
  </el-row>

  <MachineForm ref="formRef" @success="getList" />
</template>
