import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/assets/styles/index.scss' // global css
import './assets/styles/element-variables.scss'
import '@/assets/styles/ruoyi.scss' // ruoyi css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

// 自定分页插件
import Pagination from '@/components/Pagination'
// 自定义表格工具扩展
import RightToolbar from '@/components/RightToolbar'
// 自定义标签展示
import EnumTag from '@/components/EnumTag'
import permission from './directive/permission'

import {
  parseTime,
  resetForm,
  handleTree
} from '@/utils/ruoyi'

Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.handleTree = handleTree

// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('EnumTag', EnumTag)

Vue.prototype.msgSuccess = function(msg) {
  this.$message({ showClose: true, message: msg, type: 'success' })
}

Vue.prototype.msgError = function(msg) {
  this.$message({ showClose: true, message: msg, type: 'error' })
}

Vue.prototype.msgInfo = function(msg) {
  this.$message.info(msg)
}

// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)
Vue.use(permission)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
