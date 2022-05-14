<template>
  <div>
    <!-- Tag 样式 -->
    <el-tag :disable-transitions="true" :type="getType()">
      {{ getLabel() }}
    </el-tag>
  </div>
</template>

<script>
import { COMMON_STATUS_ENUMS } from '@/utils/enums'

const tags = { 0: 'danger', 1: 'success' }
export default {
  name: 'EnumTag',
  props: {
    enums: {
      type: Array,
      require: false,
      default: COMMON_STATUS_ENUMS
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
      for (let index = 0; index < this.enums.length; index++) {
        const item = this.enums[index]
        if (item.key === this.value) {
          return item.label
        }
      }
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
