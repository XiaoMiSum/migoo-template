<template>
  <el-tabs v-model="activeName" type="card" @tab-change="getList">
    <template #add-icon>
      <el-button
        v-if="activeName === 'first'"
        :disabled="selection.length < 1"
        type="primary"
        text
        @click="handleRead"
      >
        全部已读
      </el-button>
      <el-button
        v-if="activeName === 'second'"
        :disabled="selection.length < 1"
        type="primary"
        text
        @click="handleUnread"
      >
        全部未读
      </el-button>
    </template>
    <el-tab-pane label="未读消息" name="first" />
    <el-tab-pane label="已读消息" name="second" />
  </el-tabs>
  <el-table
    :data="list"
    style="width: 100%"
    v-loading="loading"
    @selection-change="handleSelection"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column label="标题" width="240">
      <template #default="scope">
        <el-popover
          placement="top-start"
          :title="data.title"
          trigger="click"
          :content="data.content"
          @show="handleRead(scope.row.id)"
          @hide="getList"
        >
          <template #reference>
            <el-button class="m-2" text type="primary" @click="handleGetData(scope.row.id)">
              {{ scope.row.title }}
            </el-button>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column label="">
      <template #default="scope">
        <el-button v-if="activeName === 'first'" size="small" @click="handleRead(scope.row.id)">
          已读
        </el-button>
        <el-button v-if="activeName === 'second'" size="small" @click="handleUnread(scope.row.id)">
          未读
        </el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.row.id)"> 删除 </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination size="small" layout="prev, pager, next" :total="total" />
</template>

<script setup lang="ts">
import * as HTTP from '@/api/stationletter'
import { ElNotification } from 'element-plus'

const props = defineProps(['code'])

const activeName = ref('first')

const loading = ref(false)
const list = ref([])
const total = ref(0)
const selection = ref<any>([])

const data = ref<any>({})

const handleGetData = async (id: any) => {
  data.value = await HTTP.getData(id)
}

const handleRead = async (id: any) => {
  await HTTP.read({ ids: id ? [id] : selection.value })
  getList()
}

const handleUnread = async (id: any) => {
  await HTTP.unread({ ids: id ? [id] : selection.value })
  getList()
}

const handleDelete = async (id: any) => {
  await HTTP.delData([id])
  getList()
}

const getList = async () => {
  const params = { unread: activeName.value === 'first' ? 1 : 0, code: props.code }
  const data = await HTTP.getPage(params)
  list.value = data.list
  total.value = data.total
}

const handleSelection = async (newSelection: any[]) => {
  selection.value = Array.from(newSelection, ({ id }) => id)
}

const notify = () => {
  ElNotification({
    title: '系统消息',
    message: h('i', { style: 'color: teal' }, '您有 ' + total.value + ' 条未读站内信')
  })
}

/** 初始化 **/
onMounted(async () => {
  await getList()
  await notify()
})
</script>
