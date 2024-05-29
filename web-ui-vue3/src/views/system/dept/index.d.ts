import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { DICT_TYPE } from '@/utils/dictionary'

import * as UserApi from '@/api/system/user'
import * as DeptApi from '@/api/system/dept'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '部门名称',
    field: 'name',
    search: {
      show: true,
      hiddenLabel: true
    },
    table: {
      align: 'left',
      headerAlign: 'left'
    }
  },
  {
    label: '负责人',
    field: 'leaderUserId',
    formatter: (_: Recordable, __: TableColumn, cellValue: number) => {
      return userList.find((user) => user.id === cellValue)?.name
    },
    table: {
      align: 'left',
      headerAlign: 'left'
    }
  },
  {
    label: '显示顺序',
    field: 'sort'
  },
  {
    label: '状态',
    field: 'status',
    dictCode: DICT_TYPE.COMMON_STATUS,
    dictClass: 'number'
  },
  {
    label: '操作',
    field: 'action',
    table: {
      width: 200
    }
  }
])

export const userList = await UserApi.listSimple()

export const { tableMethods, tableObject } = useTable({
  getListApi: DeptApi.listData
})

export const HTTP = DeptApi

export const { getList, setSearchParams } = tableMethods

export const { allSchemas } = useCrudSchemas(crudColumns)
