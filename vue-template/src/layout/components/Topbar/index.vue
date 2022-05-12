<template>
  <div class="navbar">
    <div :class="{'has-logo': true}">
      <div class="bar-container">
        <logo v-if="true" :collapse="false" :horizontal="true" />
        <el-scrollbar>
          <el-menu
            :default-active="activeMenu"
            mode="horizontal"
            background-color="#2b2f3a"
            text-color="#fff"
            active-text-color="#ffd04b"
          >

            <template v-for="(item, index) in (permission_routes.length > 0 ? permission_routes : default_routes)">
              <div v-if="!item.hidden" id="test" :key="index" :style="{float: 'left' ,textAlign: 'center' }">
                <app-link v-if="!item.hidden" :key="index" :to="resolvePath(item.path, item.children ? item.children[0].path : '')">
                  <el-menu-item
                    v-if="!item.hidden"
                    :key="index"
                    :index="resolvePath(item.path, item.children ? item.children[0].path : '')"
                  >
                    {{ (item.children ? item.children[0].meta.title : item.meta ? item.meta.title : '') }}</el-menu-item>
                </app-link>
              </div>
            </template>
          </el-menu>
        </el-scrollbar>
      </div>

      <div class="right-menu">
        <el-dropdown v-if="hasToken" class="avatar-container" trigger="click">
          <div class="avatar-wrapper">
            <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          </div>
          <el-dropdown-menu slot="dropdown" class="user-dropdown">
            <router-link to="/user/profile">
              <el-dropdown-item>个人中心</el-dropdown-item>
            </router-link>
            <el-dropdown-item divided @click.native="logout">
              <span style="display:block;">安全退出</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <div v-else class="avatar-container">
          <div class="button-wrapper ">
            <el-button type="text" @click="handleClick('/login')">登 录</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Logo from '../Sidebar/Logo.vue'
import path from 'path'
import { isExternal } from '@/utils/validate'
import AppLink from '../Sidebar/Link.vue'
import { hasToken } from '@/utils/auth'

export default {
  name: 'Topbar',
  components: {
    Logo,
    AppLink
  },
  data() {
    return {}
  },
  computed: {
    ...mapGetters([
      'permission_routes',
      'default_routes',
      'avatar',
      'name'
    ]),
    activeMenu() {
      const route = this.$route
      const { meta, path } = route

      // if set path, the sidebar will highlight the path you set
      if (meta) {
        console.log(this.permission_routes)
        console.log(this.default_routes)
        if (meta.activeMenu) {
          return meta.activeMenu
        }
      }
      return path
    },
    showLogo() {
      return true
    },
    hasToken() {
      return hasToken()
    }
  },
  methods: {
    handleClick(path) {
      this.$router.push(path)
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '系统提示', {
        confirmButtonText: '确定',
        callback: action => {
          this.$store.dispatch('user/logout').then(() => {
            this.$router.push(`/index`)
          })
        }
      })
    },
    resolvePath(basePath, routePath) {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(basePath)) {
        return basePath
      }
      return path.resolve(basePath, routePath)
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #2b2f3a;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .bar-container {
    float: left;
    min-width: 80%;
    height: 100%;
    line-height: 50px;

    .el-menu--horizontal > .el-menu-item {
      float: left;
      height: 50px !important;
      width: 10%;
      line-height: 50px !important;
      color: #999093 !important;
      padding: 0 5px !important;
      margin: 0 10px !important;
    }
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    width: 200px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;
      float: right;
         .button-wrapper {
           text-align: center;
         }

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
      }
    }
  }
}
</style>
