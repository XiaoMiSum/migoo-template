import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import Migoo from '@/layout/migoo.vue'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: Migoo,
    hidden: true,
    children: [{
      path: '',
      name: 'Login',
      component: () => import('@/views/login'),
      meta: { title: '' }
    }]
  },
  {
    path: '/admin/login',
    component: () => import('@/views/admin/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/register',
    component: Migoo,
    redirect: '/register',
    hidden: true,
    children: [{
      path: '',
      name: 'Register',
      component: () => import('@/views/register'),
      meta: { title: '注册' }
    }]
  },

  {
    path: '/',
    component: Migoo,
    redirect: '/index',
    hidden: true,
    children: [{
      path: 'index',
      name: 'Index',
      component: () => import('@/views//index'),
      meta: { title: '' }
    }]
  }
]
export const asyncRoutes = [
  {
    path: '/admin',
    component: Layout,
    redirect: '/dashboard',
    meta: { roles: ['admin'] },
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/admin/dashboard/index'),
      meta: { title: '工作台', icon: 'dashboard' }
    }]
  },
  {
    path: '/admin/user',
    component: Layout,
    redirect: 'noredirect',
    hidden: true,
    children: [{
      path: 'profile',
      component: () => import('@/views/admin/user/profile/index'),
      name: 'Profile',
      meta: { title: '个人中心', icon: 'user' }
    }]
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

// 防止连续点击多次路由报错
const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
