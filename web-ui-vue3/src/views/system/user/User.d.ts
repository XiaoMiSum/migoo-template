import {TableColumn} from '@/components/Table'
import {FormSchema} from '@/components/Form'

import {dateFormatter} from '@/utils/formatTime'
import {COMMON_STATUS_ENUM} from '@/utils/enums'

import * as UserApi from '@/api/system/user'

export const searchColumns = reactive<FormSchema[]>([
  {
    label: '登录名或姓名',
    hiddenLabel: true,
    field: 'username',
    component: 'Input'
  }
])

export const tableColumns = reactive<TableColumn[]>([
  {
    label: '登录名',
    field: 'username'
  },
  {
    label: '用户姓名',
    field: 'name'
  },
  {
    label: '部门',
    field: 'dept.name'
  },
  {
    label: '状态',
    field: 'status',
    formatter: (data: Recordable, _: TableColumn, __: number) => {
      return h(ElSwitch, {
        modelValue: data.status,
        activeValue: 1,
        inactiveValue: 0,
        onChange: () => handleStatusChange(data)
      })
    }
  },
  {
    label: '创建时间',
    field: 'createTime',
    formatter: dateFormatter
  },
  {
    label: '操作',
    field: 'action',
    table: {
      width: 160
    }
  }
])

/** 修改用户状态 */
const handleStatusChange = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === COMMON_STATUS_ENUM.ENABLE ? '停用' : '启用'
    const status = row.status ? COMMON_STATUS_ENUM.DISABLE : COMMON_STATUS_ENUM.ENABLE
    await message.confirm('确认要"' + text + '""' + row.name + '"用户吗?')
    // 发起修改状态
    await HTTP.updateUser({ id: row.id, status })
    // 列表数据状态
    row.status = status
  } catch {}
}

export const HTTP = UserApi
export const message = useMessage() // 消息弹窗
