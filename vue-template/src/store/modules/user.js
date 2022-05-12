import { login, logout, getInfo, signup } from '@/api/login'
import { setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    name: '',
    avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
    permissions: [],
    isAdminClient: false
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar || state.avatar
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },
  SET_CLIENT: (state, client) => {
    state.isAdminClient = client.toUpperCase() === 'ADMIN_CLIENT'
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, client } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password, client: client.toUpperCase() }).then(response => {
        const { data } = response
        setToken('Bearer ' + data.token)
        commit('SET_CLIENT', client)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  // user login
  register({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      signup({ username: username.trim(), password: password }).then(response => {
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { name, avatar, permissions } = data
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_PERMISSIONS', permissions)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

