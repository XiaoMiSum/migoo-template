export const COMMON_STATUS_ENUMS = [
  { key: 1, label: '启用', tag: 'success' },
  { key: 0, label: '禁用', tag: 'danger' }
]

export const COMMON_STATUS_ENUM = { DISABLE: 0, ENABLE: 1 }

export const MENU_TYPE_ENUMS = [
  { key: 1, label: '目录', tag: 'danger' },
  { key: 2, label: '菜单', tag: 'success' },
  { key: 3, label: '按钮', tag: 'info' }
]

export const MENU_TYPE_ENUM = { DIR: 1, MENU: 2, BUTTON: 3 }

export const JOB_STATUS_ENUMS = [
  { key: 0, label: '初始化', tag: '' },
  { key: 1, label: '运行中', tag: 'success' },
  { key: 2, label: '已暂停', tag: 'danger' }
]

export const JOB_STATUS_ENUM = { INIT: 0, NORMAL: 1, STOP: 2 }

export const JOB_LOG_STATUS_ENUMS = [
  { key: 0, label: '调度中', tag: '' },
  { key: 1, label: '调度成功', tag: 'success' },
  { key: 2, label: '调度失败', tag: 'danger' }
]

export const ERROR_LOG_STATUS_ENUMS = [
  { key: 0, label: '未处理', tag: 'danger' },
  { key: 1, label: '已处理', tag: 'success' },
  { key: 2, label: '已忽略', tag: 'info' }
]

export const JOB_LOG_STATUS_ENUM = { INIT: 0, SUCCESSFUL: 1, FAILURE: 2 }

export const FILE_STORAGE = [
  { key: 1, label: '数据库', tag: '' },
  { key: 10, label: '本地存储', tag: 'success' },
  { key: 20, label: 'S3协议', tag: 'danger' }
]

export const TRUE_FALSE = [
  { key: false, label: '否', tag: '' },
  { key: true, label: '是', tag: 'success' }
]
