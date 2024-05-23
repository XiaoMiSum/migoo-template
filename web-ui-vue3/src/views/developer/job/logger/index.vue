<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="handlerName">
        <el-input
          v-model="queryParams.handlerName"
          class="!w-240px"
          clearable
          placeholder="请输入处理器的名字"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="status">
        <el-select
          v-model="queryParams.status"
          class="!w-240px"
          clearable
          placeholder="请选择调度状态"
        >
          <el-option
            v-for="item in getIntDictOptions(DICT_TYPE.INFRA_JOB_LOG_STATUS)"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="beginTime">
        <el-date-picker
          v-model="queryParams.beginTime"
          class="!w-240px"
          clearable
          placeholder="选择开始执行时间"
          type="date"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item prop="endTime">
        <el-date-picker
          v-model="queryParams.endTime"
          :default-time="new Date('1 23:59:59')"
          class="!w-240px"
          clearable
          placeholder="选择结束执行时间"
          type="date"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
    </el-form>
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button type="primary" @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column align="center" label="ID" prop="id" />
      <el-table-column align="center" label="任务编号" prop="jobId" />
      <el-table-column align="center" label="处理器名字" prop="handlerName" />
      <el-table-column align="center" label="处理器参数" prop="handlerParam" />
      <el-table-column align="center" label="第几次执行" prop="executeIndex" />
      <el-table-column align="center" label="执行时间" width="170s">
        <template #default="scope">
          <span>{{ formatDate(scope.row.beginTime) + ' ~ ' + formatDate(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="执行时长" prop="duration">
        <template #default="scope">
          <span>{{ scope.row.duration + ' 毫秒' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="status">
        <template #default="scope">
          <ones-tag :type="DICT_TYPE.INFRA_JOB_LOG_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button
            v-hasPermi="['developer:job:query']"
            link
            type="primary"
            @click="openDetail(scope.row.id)"
          >
            详细
          </el-button>
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

  <!-- 表单弹窗：查看 -->
  <JobLogDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { formatDate } from '@/utils/formatTime'
import JobLogDetail from './JobLogDetail.vue'
import * as HTTP from '@/api/developer/job/jobLog'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dictionary'

defineOptions({ name: 'InfraJobLog' })

const { query } = useRoute() // 查询参数

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  jobId: query ? query.id : undefined,
  handlerName: undefined,
  beginTime: undefined,
  endTime: undefined,
  status: undefined
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.getJobLogPage(queryParams.value)
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

/** 查看操作 */
const detailRef = ref()
const openDetail = (rowId?: number) => {
  detailRef.value.open(rowId)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
