<template>
  <div class="head-container">
    <el-input v-model="filterText" class="mb-20px" clearable placeholder="请输入节点名称">
      <template #prefix>
        <Icon icon="ep:search" />
      </template>
    </el-input>
  </div>
  <div class="head-container">
    <el-tree
      ref="treeRef"
      :data="list"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      :props="defaultProps"
      highlight-current
      default-expand-all
      node-key="id"
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script lang="ts" setup>
import { ElTree } from 'element-plus'
import * as HTTP from '@/api/developer/cvs/provider'

defineOptions({ name: 'CVSProvideTree' })

const filterText = ref('')
const list = ref<Tree[]>([]) // 树形结构
const treeRef = ref<InstanceType<typeof ElTree>>()
const defaultProps = ref<any>({
  children: 'children',
  label: 'name',
  value: 'id'
})

/** 获得账号树 */
const getTree = async () => {
  const res = await HTTP.getTree()
  list.value = []
  for (let i = 0; i < res.length; i++) {
    list.value.push(res[i])
  }
}

/** 基于名字过滤 */
const filterNode = (name: string, data: Tree) => {
  if (!name) return true
  return data.name.includes(name)
}

/** 处理部门被点击 */
const handleNodeClick = async (row: { [key: string]: any }) => {
  emits('node-click', row)
}
const emits = defineEmits(['node-click'])

/** 监听deptName */
watch(filterText, (val) => {
  treeRef.value!.filter(val)
})

/** 初始化 */
onMounted(async () => {
  await getTree()
})
</script>
