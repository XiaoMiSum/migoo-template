export const COMMON_STATUS_ENUMS = [
  { key: 1, label: '启用', tag: 'success' },
  { key: 0, label: '禁用', tag: 'danger' }
]

export const SYS_THEME_ENUMS = [
  { key: 'dark', label: '暗夜主题', tag: 'success' },
  { key: 'default', label: '默认主题', tag: 'danger' }
]

export const SYS_ICON_ENUMS = [
  { key: 'vue', label: 'VUE', tag: 'success' },
  { key: 'vite', label: 'VITE', tag: 'danger' },
  { key: 'panda', label: 'PANDA', tag: 'danger' },
  { key: 'tianmao', label: 'TIAN-MAO', tag: 'danger' }
]

export const SYS_LOGIN_PAGE_ENUMS = [{ key: 'default', label: '默认' }]

export const SYS_LAYOUT_ENUMS = [
  { key: 'classic', label: '默认' },
  { key: 'top', label: '顶部' },
  { key: 'cutMenu', label: '缩略' }
]

export const COMMON_STATUS_ENUM = { DISABLE: 0, ENABLE: 1 }

export const MENU_TYPE_ENUMS = [
  { key: 1, label: '目录', tag: 'danger' },
  { key: 2, label: '菜单', tag: 'success' },
  { key: 3, label: '按钮', tag: 'info' }
]

export const MENU_TYPE_ENUM = { DIR: 1, MENU: 2, BUTTON: 3 }

export const GENERATOR_TEMPLATE_TYPE_ENUMS = [
  { key: 1, label: '单表（增删改查）' },
  { key: 2, label: '树表（增删改查）' },
  { key: 3, label: '主子表（增删改查）' }
]

export const REDIS_TIMEOUT_ENUMS = [
  { key: 1, label: '永不超时', tag: 'danger' },
  { key: 2, label: '动态超时', tag: 'success' },
  { key: 3, label: '固定超时', tag: '' }
]

export const PAGE_ENUMS = [
  { key: 'form', label: '表单跳转' },
  { key: 'link', label: '链接跳转' },
  { key: 'scan_code', label: '扫码支付' },
  { key: 'common', label: '系统审核' },
  { key: 'PayLinks', label: '易票联快捷' }
]

export const BIND_CARD_STRATEGY = [
  { key: 'default', label: '默认' },
  { key: 'PayLinks', label: '易票联' }
]

export const ORDER_STATUS_ENUM = { FAILED: 1, SUCCESSFUL: 0, PENDING: 2 }

export const ORDER_STATUS_ENUMS = [
  { key: 1, label: '已失败', tag: 'danger' },
  { key: 0, label: '已成功', tag: 'success' },
  { key: 2, label: '交易中', tag: '' }
]

export const ERROR_LOG_STATUS_ENUMS = [
  { key: 0, label: '未处理', tag: 'danger' },
  { key: 1, label: '已处理', tag: 'success' },
  { key: 2, label: '已忽略', tag: 'info' }
]

export const APPLICATION_ENUMS = [
  { key: 'web-server', label: 'web-server', tag: 'danger' },
  { key: 'open-server', label: 'open-server', tag: 'success' },
  { key: 'channel-server', label: 'channel-server', tag: 'info' }
]

export const TASK_ENUMS = [
  { key: 'balance_query', label: 'balance_query', tag: 'danger' },
  { key: 'order_payment_query', label: 'order_payment_query', tag: 'success' },
  { key: 'order_withdrawal_query', label: 'order_withdrawal_query', tag: 'info' }
]

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

export const JOB_LOG_STATUS_ENUM = { INIT: 0, SUCCESSFUL: 1, FAILURE: 2 }
export const CODE_STATUS_ENUMS = [
  { key: 0, label: '已下架', tag: 'danger' },
  { key: 1, label: '上架中', tag: 'success' }
]

export const BANK_CODE_ENUMS = [
  { bankCode: '01020000', bankName: '工商 银行' },
  { bankCode: '01050000', bankName: '建设 银行' },
  { bankCode: '01030000', bankName: '农业 银行' },
  { bankCode: '03080000', bankName: '招商 银行' },
  { bankCode: '03010000', bankName: '交通 银行' },
  { bankCode: '01040000', bankName: '中国 银行' },
  { bankCode: '03030000', bankName: '光大 银行' },
  { bankCode: '03050000', bankName: '民生 银行' },
  { bankCode: '03090000', bankName: '兴业 银行' },
  { bankCode: '03020000', bankName: '中信 银行' },
  { bankCode: '03060000', bankName: '广发 银行' },
  { bankCode: '03100000', bankName: '浦发 银行' },
  { bankCode: '03070000', bankName: '平安 银行' },
  { bankCode: '03040000', bankName: '华夏 银行' },
  { bankCode: '04083320', bankName: '宁波 银行' },
  { bankCode: '03200000', bankName: '东亚 银行' },
  { bankCode: '04012900', bankName: '上海 银行' },
  { bankCode: '01000000', bankName: '邮储 银行' },
  { bankCode: '04243010', bankName: '南京 银行' },
  { bankCode: '65012900', bankName: '上海 农商' },
  { bankCode: '03170000', bankName: '渤海 银行' },
  { bankCode: '64296510', bankName: '成都 银行' },
  { bankCode: '04031000', bankName: '北京 银行' },
  { bankCode: '64296511', bankName: '徽商 银行' },
  { bankCode: '04341101', bankName: '天津 银行' }
]
