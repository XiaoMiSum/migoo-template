const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  permissions: state => state.user.permissions,
  permission_routes: state => state.permission.sidebarRouters,
  default_routes: state => state.permission.default_routes
}
export default getters
