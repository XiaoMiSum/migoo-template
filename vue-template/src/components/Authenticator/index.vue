<template>
  <el-dialog
    title="身份验证器"
    :visible.sync="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :show-close="false"
    width="30%"
  >
    <el-row :gutter="20" type="flex" justify="center">
      <el-col :span="8">
        <el-image style="width: 160px; height: 160px" :src="data.quickMark" fit="cover" />
      </el-col>
      <el-col :span="14">
        <el-divider>请扫描二维码</el-divider>

        <b id="securityCode">安全码：{{ data.securityCode }}</b> <el-button type="text" @click="handleCopy">复制</el-button>
        <br> <br>
        <span>用于找回身份验证器，请妥善保存</span>
        <br>
        <span>为了您的账号安全，请勿将二维码及安全码透露给他人</span>
      </el-col>
    </el-row>
    <br>
    <el-row :gutter="20" type="flex" justify="center">
      <el-col :span="22">
        <el-input v-model="code" placeholder="请输入身份验证码" />
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { setToken } from '@/utils/auth'
import { getUserAuthenticator, bindUserAuthenticator } from '@/api/profile'

export default {
  name: 'Authenticator',
  props: {
    path: {
      type: String,
      require: true,
      default: '/'
    }
  },
  data() {
    return {
      visible: false,
      data: {
        quickMark: '',
        securityCode: ''
      },
      code: null
    }
  },
  methods: {
    handleShow(token) {
      this.token = token
      getUserAuthenticator(token).then(response => {
        this.data = response.data
        this.visible = true
      })
    },
    handleCopy() {
      navigator.clipboard.writeText(this.data.securityCode)
      this.msgSuccess('复制成功!')
    },
    submitForm() {
      bindUserAuthenticator(this.token, this.code).then(response => {
        setToken('Bearer ' + this.token)
        this.$router.push({ path: this.path })
        this.visible = false
      })
    }
  }
}
</script>
