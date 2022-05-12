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
                <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="请输入您的手机号">
                  <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="请输入您的登录密码" @keyup.enter.native="handleLogin">
                  <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
                </el-input>
              </el-form-item>
              <el-form-item v-if="config.captchaEnable" prop="code">
                <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%" @keyup.enter.native="handleLogin">
                  <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
                </el-input>
                <div class="login-code">
                  <img :src="codeUrl" class="login-code-img" @click="getCode">
                </div>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
                  登  录
                </el-button>
              </el-form-item>
              <div class="tips">
                <router-link
                  style="margin-right: 5px"
                  to="/register"
                >没有账号？点击注册</router-link>
              </div>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from '@/api/login'

export default {
  name: 'Login',
  data() {
    return {
      config: {
        title: 'VUE-MIGOO-TEMPLATE',
        captchaEnable: true
      },
      loginForm: {
        client: 'member_client'
      },
      loginRules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入手机号' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入密码' }
        ]
      },
      loading: false
    }
  },
  created() {
    this.getCode()
  },
  destroyed() {
    // window.removeEventListener('hashchange', this.afterQRScan);
  },
  methods: {
    getCode() {
      // 只有开启的状态，才加载验证码。默认开启
      if (!this.captchaEnable) {
        return
      }
      // 请求远程，获得验证码
      getCodeImg().then(res => {
        res = res.data
        this.captchaEnable = res.enable
        if (this.captchaEnable) {
          this.codeUrl = 'data:image/gif;base64,' + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
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
      .tips {
        font-size: 14px;
        color: #409eff;
        margin-bottom: 10px;
        text-align: right;
      }
    }
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.login-code-img {
  height: 38px;
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
            margin-top: 70px;
        }
    }
}

@media (min-width: 1200px) {
    .login-page {
        .login-form-wrapper {
            margin-top: 160px;
        }
    }
}
</style>
