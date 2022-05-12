import { constantRoutes, asyncRoutes } from '@/router'
import { getRouters } from '@/api/login'
import Layout from '@/layout'
import ParentView from '@/components/ParentView'

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap, isRewrite = false) {
  return asyncRouterMap.filter(route => {
    // 将 ruoyi 后端原有耦合前端的逻辑，迁移到此处
    // 处理 meta 属性
    route.meta = {
      title: route.name,
      icon: route.icon
    }
    // 处理 component 属性
    if (route.children) { // 父节点
      // debugger
      if (route.parentId === 0) {
        route.component = Layout
      } else {
        route.component = ParentView
      }
    } else { // 根节点
      route.component = loadView(route.component)
    }

    // filterChildren
    if (isRewrite && route.children) {
      route.children = filterChildren(route.children)
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children, route, isRewrite)
    }
    return true
  })
}

function filterChildren(childrenMap) {
  var children = []
  childrenMap.forEach((el, index) => {
    if (el.children && el.children.length) {
      if (el.component === 'ParentView') {
        el.children.forEach(c => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children, c))
            return
          }
          children.push(c)
        })
        return
      }
    }
    children = children.concat(el)
  })
  return children
}

export const loadView = (view) => { // 路由懒加载
  return (resolve) => require([`@/views/${view}`], resolve)
}

const state = {
  routes: [],
  addRoutes: [],
  sidebarRouters: [],
  default_routes: constantRoutes
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  },
  SET_SIDEBAR_ROUTERS: (state, routers) => {
    state.sidebarRouters = constantRoutes.concat(routers)
  }
}

const actions = {
  generateRoutes({ commit }, requireGetRouter) {
    return new Promise(resolve => {
      if (requireGetRouter) {
        // 向后端请求路由数据
        getRouters().then(res => {
          const sidebarRoutes = filterAsyncRouter(JSON.parse(JSON.stringify(res.data)))
          const rewriteRoutes = filterAsyncRouter(JSON.parse(JSON.stringify(res.data)), true)
          rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
          commit('SET_ROUTES', rewriteRoutes)
          commit('SET_SIDEBAR_ROUTERS', sidebarRoutes)
          resolve(rewriteRoutes)
        })
      } else {
        const sidebarRoutes = asyncRoutes.slice(0)
        const rewriteRoutes = asyncRoutes.slice(0)
        rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })
        commit('SET_ROUTES', rewriteRoutes)
        commit('SET_SIDEBAR_ROUTERS', sidebarRoutes)
        resolve(rewriteRoutes)
      }
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
