<template>
  <div class="register-page">
    <div class="register-header" />
    <div class="register-banner">
      <el-row :gutter="100">
        <el-col :span="12">
          <img src="../assets/images/login-banner.png" class="img-responsive" alt="">
        </el-col>
        <el-col :span="12">
          <el-card class="register-form-wrapper">
            <el-form
              ref="registerForm"
              auto-complete="on"
              :model="registerForm"
              :rules="registerRules"
            >
              <el-form-item prop="phone">
                <el-input v-model="registerForm.phone" type="text" auto-complete="off" placeholder="请输入您的手机号">
                  <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="registerForm.password" type="password" auto-complete="off" placeholder="请输入您的密码" @keyup.enter.native="handleRegister">
                  <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleRegister">
                  注  册
                </el-button>
              </el-form-item>
              <div class="tips">
                <router-link
                  style="margin-right: 5px"
                  to="/login"
                >已有账号？点击登录</router-link>
              </div>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
      },
      registerRules: {
        phone: [
          { required: true, trigger: 'blur', message: '手机号不能为空' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '登录密码不能为空' }
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
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/register', this.registerForm).then(() => {
            this.$router.push({ path: '/login' })
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
.register-page {
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

    .register-header,
    .register-banner{
        max-width: 100%;
        padding: 30px 50px;
        margin: auto
    }
    .register-form-wrapper {
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

@media (max-width: 768px) {
    .register-page {
        .register-form-wrapper {
            margin-top: 0px;
        }
    }
}

@media (min-width: 768px) and (max-width: 1200px) {
    .register-page {
        .register-form-wrapper {
            margin-top: 50px;
        }
    }
}

@media (min-width: 1200px) {
    .register-page {
        .register-form-wrapper {
            margin-top: 120px;
        }
    }
}
</style>
