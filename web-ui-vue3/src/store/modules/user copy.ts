import { store } from '@/store'
import { defineStore } from 'pinia'
import { hasToken, removeToken } from '@/utils/auth'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
import { getInfo, loginOut } from '@/api/login'

const { wsCache } = useCache()

interface UserVO {
  id: number
  avatar: string
  nickname: string
}

interface UserInfoVO {
  permissions: string[]
  roles: string[]
  isSetUser: boolean
  user: UserVO
  requiredBindAuthenticator: boolean
}

export const useUserStore = defineStore('admin-user', {
  state: (): UserInfoVO => ({
    permissions: [],
    roles: [],
    isSetUser: false,
    user: {
      id: 0,
      avatar: '',
      nickname: ''
    },
    requiredBindAuthenticator: false
  }),
  getters: {
    getPermissions(): string[] {
      return this.permissions
    },
    getRoles(): string[] {
      return this.roles
    },
    getIsSetUser(): boolean {
      return this.isSetUser
    },
    getUser(): UserVO {
      return this.user
    },
    isRequiredBindAuthenticator(): boolean {
      return this.requiredBindAuthenticator
    }
  },
  actions: {
    async setUserInfoAction() {
      if (!hasToken()) {
        this.resetState()
        return null
      }
      let userInfo = wsCache.get(CACHE_KEY.USER)
      if (!userInfo) {
        userInfo = await getInfo()
      }
      this.permissions = userInfo.permissions
      this.requiredBindAuthenticator = userInfo.requiredBindAuthenticator
      this.roles = userInfo.roles
      this.user = userInfo.user
      this.isSetUser = true
      wsCache.set(CACHE_KEY.USER, userInfo)
      wsCache.set(CACHE_KEY.ROLE_ROUTERS, userInfo.menus)
    },
    async isRequiredBindAuthenticator(requiredBindAuthenticator: boolean) {
      this.requiredBindAuthenticator = requiredBindAuthenticator
      const userInfo = wsCache.get(CACHE_KEY.USER)
      userInfo.requiredBindAuthenticator = requiredBindAuthenticator
      wsCache.set(CACHE_KEY.USER, userInfo)
    },
    async loginOut() {
      await loginOut()
      removeToken()
      wsCache.clear()
      this.resetState()
    },
    resetState() {
      this.permissions = []
      this.roles = []
      this.isSetUser = false
      ;(this.user = {
        id: 0,
        avatar: '',
        nickname: ''
      }),
        (this.requiredBindAuthenticator = true)
    }
  }
})

export const useUserStoreWithOut = () => {
  return useUserStore(store)
}
