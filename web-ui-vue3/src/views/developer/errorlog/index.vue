<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryForm" :inline="true" :model="queryParams">
      <el-form-item label="应用名称" prop="applicationName">
        <el-select
          v-model="queryParams.applicationName"
          clearable
          placeholder="请选择应用名称"
          size="small"
          @change="handleQuery"
        >
          <el-option
            v-for="(item, index) in APPLICATION_ENUMS"
            :key="index"
            :label="item.label"
            :value="item.key"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="status">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择处理状态"
          size="small"
          @change="handleQuery"
        >
          <el-option
            v-for="(item, index) in ERROR_LOG_STATUS_ENUMS"
            :key="index"
            :label="item.label"
            :value="item.key"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <!-- 列表 -->
    <el-table v-loading="loading" :data="list" highlight-current-row stripe>
      <el-table-column align="left" label="应用名称" prop="applicationName" />
      <el-table-column align="center" label="请求来源 " prop="userIp" />
      <el-table-column align="center" label="异常时间" prop="exceptionTime">
        <template #default="scope">
          <span>{{ parseTime(scope.row.exceptionTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="status">
        <template #default="scope">
          <enum-tag :enums="ERROR_LOG_STATUS_ENUMS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
        label="操作"
        width="200"
      >
        <template #default="scope">
          <el-button
            v-hasPermi="['developer:error-log:update']"
            link
            type="primary"
            @click="handleOpenDetail(scope.row.id)"
            >处理
          </el-button>
          <el-button
            v-hasPermi="['developer:error-log:remove']"
            link
            type="danger"
            @click="handleRemove(scope.row.id)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-show="total > 0"
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <ErrorLogDetail ref="detailRef" @close="getList" />
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/developer/error-log'
import { APPLICATION_ENUMS, ERROR_LOG_STATUS_ENUMS } from '@/utils/enums'
import { parseTime } from '@/utils'
import ErrorLogDetail from '@/views/developer/errorlog/ErrorLogDetail.vue' // 代码高亮插件

const message = useMessage()

const loading = ref(true)
const total = ref(0)
const list = ref<any>([])
const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  applicationName: '',
  status: 0
})

const getList = async () => {
  loading.value = true
  // 处理查询参数
  const params = { ...queryParams.value }
  // 执行查询
  HTTP.getPage(params).then((data) => {
    list.value = data.list
    total.value = data.total
  })
  loading.value = false
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

const queryForm = ref()

/** 重置按钮操作 */
const resetQuery = () => {
  queryForm.value.resetFields()
  handleQuery()
}

/** 删除的操作 **/
const handleRemove = async (id: number) => {
  try {
    // 操作的二次确认
    await message.delConfirm()
    // 执行操作
    await HTTP.removeData(id)
    message.success('删除成功')
    // 刷新列表
    await getList()
  } catch {}
}
const detailRef = ref()
/** 查看详情 */
const handleOpenDetail = (id: number) => {
  detailRef.value.open(id)
}

onMounted(() => {
  getList()
})
</script>
