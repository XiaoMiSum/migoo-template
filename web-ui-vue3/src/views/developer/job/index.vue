<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="100px">
      <el-form-item label="" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="请输入任务名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="请选择任务状态">
          <el-option
            v-for="item in getIntDictOptions(DICT_TYPE.INFRA_JOB_STATUS)"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="handlerName">
        <el-input
          v-model="queryParams.handlerName"
          clearable
          placeholder="请输入处理器的名字"
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['developer:job:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
        <el-button v-hasPermi="['developer:job:query']" plain type="info" @click="handleJobLog(0)">
          <Icon class="mr-5px" icon="ep:zoom-in" />
          执行日志
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column align="center" label="ID" prop="id" width="50" />
      <el-table-column align="center" label="任务名称" prop="name" />
      <el-table-column align="center" label="状态" prop="status" width="100">
        <template #default="scope">
          <ones-tag :type="DICT_TYPE.INFRA_JOB_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column align="left" label="处理器" prop="handlerName" />
      <el-table-column align="left" label="处理器参数" prop="handlerParam" />
      <el-table-column align="center" label="CRON" prop="cronExpression" />
      <el-table-column align="center" label="操作" width="250">
        <template #default="scope">
          <div class="flex items-center justify-center">
            <el-button
              v-hasPermi="['developer:job:update']"
              link
              type="primary"
              @click="openForm('update', scope.row.id)"
            >
              修改
            </el-button>
            <el-button
              v-hasPermi="['developer:job:update']"
              link
              type="primary"
              @click="handleChangeStatus(scope.row)"
            >
              {{ scope.row.status === JOB_STATUS_ENUM.STOP ? '开启' : '暂停' }}
            </el-button>
            <el-button
              v-hasPermi="['developer:job:remove']"
              link
              type="danger"
              @click="handleDelete(scope.row.id)"
            >
              删除
            </el-button>
            <el-dropdown
              v-hasPermi="['developer:job:trigger', 'developer:job:query']"
              @command="(command) => handleCommand(command, scope.row)"
            >
              <el-button link type="primary">
                <Icon icon="ep:d-arrow-right" />
                更多
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-if="checkPermi(['developer:job:trigger'])"
                    command="handleRun"
                  >
                    执行一次
                  </el-dropdown-item>
                  <el-dropdown-item v-if="checkPermi(['developer:job:query'])" command="openDetail">
                    任务详细
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-if="checkPermi(['developer:job:query'])"
                    command="handleJobLog"
                  >
                    调度日志
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <JobForm ref="formRef" @success="getList" />
  <!-- 表单弹窗：查看 -->
  <JobDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { checkPermi } from '@/utils/permission'
import JobForm from './JobForm.vue'
import JobDetail from './JobDetail.vue'
import * as JobApi from '@/api//developer/job'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dictionary'
import { JOB_STATUS_ENUM } from '@/utils/enums'

defineOptions({ name: 'InfraJob' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const { push } = useRouter() // 路由

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  status: undefined,
  handlerName: undefined
})

const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await JobApi.listJob(queryParams.value)
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
    const text = row.status === JOB_STATUS_ENUM.STOP ? '开启' : '关闭'
    await message.confirm('确认要' + text + '定时任务 为"' + row.name + '"?', t('common.reminder'))
    const status =
      row.status === JOB_STATUS_ENUM.STOP ? JOB_STATUS_ENUM.NORMAL : JOB_STATUS_ENUM.STOP
    await JobApi.updateJobStatus(row.id, status)
    message.success(text + '成功')
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status = row.status === JOB_STATUS_ENUM.STOP ? JOB_STATUS_ENUM.NORMAL : JOB_STATUS_ENUM.STOP
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await JobApi.delJob(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** '更多'操作按钮 */
const handleCommand = (command, row) => {
  switch (command) {
    case 'handleRun':
      handleRun(row)
      break
    case 'openDetail':
      openDetail(row.id)
      break
    case 'handleJobLog':
      handleJobLog(row?.id)
      break
    default:
      break
  }
}

/** 执行一次 */
const handleRun = async (row: any) => {
  try {
    // 二次确认
    await message.confirm('确认要立即执行一次' + row.name + '?', t('common.reminder'))
    // 提交执行
    await JobApi.runJob(row.id)
    message.success('执行成功')
    // 刷新列表
    await getList()
  } catch {}
}

/** 查看操作 */
const detailRef = ref()
const openDetail = (id: number) => {
  detailRef.value.open(id)
}

/** 跳转执行日志 */
const handleJobLog = (id: number) => {
  if (id > 0) {
    push({
      path: '/developer/job/log',
      query: {
        jobId: id
      }
    })
  } else {
    push('/developer/job/log')
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
