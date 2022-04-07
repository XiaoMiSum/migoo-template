<template>
  <div class="login-page">
    <div class="login-header">
      <div class="clearfix">
        <div class="logo pull-left">
          <img src="" alt="">
        </div>
        <div class="company-info pull-right" />
      </div>
    </div>
    <div class="login-banner-wrapper">
      <div class="login-banner">
        <el-row :gutter="20">
          <el-col :span="12">
            <img src="../assets/images/login-banner.png" class="img-responsive" alt="">
          </el-col>
          <el-col :span="12">
            <el-card class="login-form-wrapper pull-right">

              <el-form
                ref="loginForm"
                auto-complete="on"
                :model="loginForm"
                :rules="loginRules"
                class="card-box login-form"
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
          this.$store.dispatch('LoginByEmail', this.loginForm).then(() => {
            this.loading = false
            this.$router.push({ path: '/' })
            // this.showDialog = true;
          }).catch(err => {
            this.$message.error(err.message)
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
    @import "@/assets/styles/mixin.scss";

    .tips {
        font-size: 14px;
        color: #fff;
        margin-bottom: 5px;
    }

    .login-page {
        min-width: 768px;
        .company-info {
            margin-top: 18px;
        }

        .img-responsive {
            display: block;
            max-width: 100%;
            height: auto;
        }

        .pull-left {
            float: left;
        }

        .pull-right {
            float: right;
        }

        .clearfix:after {
            content: "";
            display: table;
            clear: both;
        }

        .login-header,
        .login-banner{
            max-width: 1260px;
            padding: 30px 50px;
            margin: auto
        }

        .login-banner-wrapper {

        }

        .login-form-wrapper {
            background: #ecf9ff;

            width: 400px;
            margin-top: 40px;
              border-radius: 6px;

  padding: 25px 25px 5px 25px;
        }

        .login-form-wrapper .form-title {
            margin-bottom: 15px;
            font-size: 16px;
        }

        .login-form-wrapper .btn-login {
            background-color: #00a0e9;
            color: #FFF;
        }

        .section {
            padding: 30px 50px;
        }

        .section.gray {
            background-color: #f4f6f7;
        }

        .section img {
            margin: auto
        }

        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px #FFF inset !important;
            -webkit-text-fill-color: #555 !important;
        }

        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0;
            padding: 12px 5px 12px 15px;
            color: #555;
            height: 47px;
        }
        .el-input {
            display: inline-block;
            height: 47px;
            width: 85%;
        }
        .svg-container {
            padding: 6px 5px 6px 15px;
            color: #889aa4;
        }
        .title {
            font-size: 26px;
            font-weight: 400;
            color: #eeeeee;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
        }
        .el-form-item {
            border: 1px solid #EBEBEB;
            background: #FFF;
            border-radius: 5px;
            color: #555;
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
                margin-top: 80px;
            }
        }
    }

    .login-container {
        @include relative;
        height: 100vh;
        background-color: #2d3a4b;
        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px #293444 inset !important;
            -webkit-text-fill-color: #fff !important;
        }
        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0;
            padding: 12px 5px 12px 15px;
            color: #eeeeee;
            height: 47px;
        }
        .el-input {
            display: inline-block;
            height: 47px;
            width: 85%;
        }
        .svg-container {
            padding: 6px 5px 6px 15px;
            color: #889aa4;
        }
        .title {
            font-size: 26px;
            font-weight: 400;
            color: #eeeeee;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
        }
        .login-form {
            position: absolute;
            left: 0;
            right: 0;
            width: 400px;
            padding: 35px 35px 15px 35px;
            margin: 120px auto;
        }
        .el-form-item {
            border: 1px solid rgba(255, 255, 255, 0.1);
            background: rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            color: #454545;
        }
        .forget-pwd {
            color: #fff;
        }
    }
</style>
