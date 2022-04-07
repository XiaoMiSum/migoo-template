<template>
  <div class="login-page">
    <div class="login-header" />
    <div class="login-banner">
      <el-row :gutter="100">
        <el-col :span="12">
          <img src="../assets/images/login-banner.png" class="img-responsive" alt="">
        </el-col>
        <el-col :span="12">
          <el-card class="login-form-wrapper">
            <el-form
              ref="loginForm"
              auto-complete="on"
              :model="loginForm"
              :rules="loginRules"
            >
              <el-form-item prop="username">
                <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
                  <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码" @keyup.enter.native="handleLogin">
                  <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
                  登录
                </el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
      },
      loginRules: {
        username: [
          { required: true, trigger: 'blur' }
        ],
        password: [
          { required: true, trigger: 'blur' }
        ]
      },
      loading: false
    }
  },
  created() {
    // window.addEventListener('hashchange', this.afterQRScan);
  },
  destroyed() {
    // window.removeEventListener('hashchange', this.afterQRScan);
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
@import "@/assets/styles/mixin.scss";
.login-page {
  width: 100%;
  height: 100%;
  .company-info {
    margin-top: 18px;
  }

  .img-responsive {
      display: block;
      max-width: 100%;
      height: auto;
  }

    .login-header,
    .login-banner{
        max-width: 100%;
        padding: 30px 50px;
        margin: auto
    }
    .login-form-wrapper {
      background: #ecf9ff;
      width: 500px;
      margin-top: 40px;
      border-radius: 6px;
      padding: 30px 25px 5px 25px;
    }
}

@media (max-width: 768px) {
    .login-page {
        .login-form-wrapper {
            margin-top: 0px;
        }
    }
}

@media (min-width: 768px) and (max-width: 1200px) {
    .login-page {
        .login-form-wrapper {
            margin-top: 50px;
        }
    }
}

@media (min-width: 1200px) {
    .login-page {
        .login-form-wrapper {
            margin-top: 120px;
        }
    }
}
</style>
