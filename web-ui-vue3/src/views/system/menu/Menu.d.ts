import {TableColumn} from '@/components/Table'
import {FormSchema} from '@/components/Form'

import type {CrudSchema} from '@/hooks/web/useCrudSchemas'
import {DICT_TYPE} from '@/utils/dictionary'
import OnesTag from '@/components/OnesTag/src/OnesTag.vue'

export const searchColumns = reactive<FormSchema[]>([
  {
    label: '菜单名称',
    hiddenLabel: true,
    field: 'name',
    component: 'Input'
  }
])

export const tableColumns = reactive<CrudSchema[]>([
  {
    label: '菜单名称',
    field: 'name'
  },
  {
    label: '排序',
    field: 'sort'
  },
  {
    label: '权限标识',
    field: 'permission'
  },
  {
    label: '组件路径',
    field: 'component'
  },
  {
    label: '状态',
    field: 'status',
    formatter: (data: Recordable, _: TableColumn, __: number) => {
      return h(OnesTag, {
        type: DICT_TYPE.COMMON_STATUS!, // ! 表示一定不为空
        value: data.status
      })
    },
    dictCode: DICT_TYPE.COMMON_STATUS
  },
  {
    label: '操作',
    field: 'action',
    width: 160
  }
])
export const message = useMessage() // 消息弹窗
