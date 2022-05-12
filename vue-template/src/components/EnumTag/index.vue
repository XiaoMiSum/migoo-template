<template>
  <div>
    <!-- Tag 样式 -->
    <el-tag :disable-transitions="true" :type="getType()">
      {{ getLabel() }}
    </el-tag>
  </div>
</template>

<script>
import { CommonStatusEnumList } from '@/utils/enums'

const tags = { 0: 'danger', 1: 'success' }
export default {
  name: 'EnumTag',
  props: {
    enums: {
      type: Array,
      require: false,
      default: CommonStatusEnumList
    },
    value: {
      type: Number,
      require: true,
      default: 0
    }
  },
  methods: {
    getType() {
      const type = tags[this.value]
      return type || 'info'
    },
    getLabel() {
      this.enums.forEach(item => {
        if (item.key === this.value) {
          return item.label
        }
      })
      return '未知'
    }
  }
}
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
