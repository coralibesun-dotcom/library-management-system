<template>
  <div class="login-container">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules"
             class="login-form" label-position="left">

      <div class="title-container">
        <h3 class="title">注册账号</h3>
      </div>

      <el-form-item prop="username">
        <el-input v-model="registerForm.username" placeholder="用户名" name="username"
                  type="text" tabindex="1" auto-complete="on" />
      </el-form-item>

      <el-form-item prop="password">
        <el-input v-model="registerForm.password" placeholder="密码（至少6位）" name="password"
                  type="password" tabindex="2" auto-complete="on" />
      </el-form-item>

      <el-form-item prop="confirmPassword">
        <el-input v-model="registerForm.confirmPassword" placeholder="确认密码" name="confirmPassword"
                  type="password" tabindex="3" auto-complete="on"
                  @keyup.enter.native="handleRegister" />
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleRegister">注 册</el-button>

      <div style="text-align:center">
        <el-link type="primary" @click="$router.push('/login')">已有账号？去登录</el-link>
      </div>
    </el-form>
  </div>
</template>

<script>
import { register } from '@/api/user'
import { Message } from 'element-ui'

export default {
  name: 'Register',
  data() {
    const validateConfirm = (rule, value, callback) => {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      registerRules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, min: 6, message: '密码不能少于6位', trigger: 'blur' }],
        confirmPassword: [{ required: true, validator: validateConfirm, trigger: 'blur' }]
      },
      loading: false
    }
  },
  methods: {
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (!valid) return false
        this.loading = true
        register({
          username: this.registerForm.username.trim(),
          password: this.registerForm.password
        }).then(() => {
          Message.success('注册成功，请登录')
          this.$router.push('/login')
        }).catch(() => {
          this.loading = false
        })
      })
    }
  }
}
</script>

<style scoped>
  .login-container {
    min-height: 100vh;
    width: 100%;
    background-color: #2d3a4b;
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .login-form {
    width: 520px;
    max-width: 100%;
    padding: 0 35px;
    box-sizing: border-box;
  }
  .title-container {
    text-align: center;
    margin-bottom: 30px;
  }
  .title {
    color: #fff;
    font-size: 26px;
    font-weight: 500;
    margin: 0;
  }
  .login-container >>> .el-input__inner {
    background: transparent;
    border: 0;
    border-bottom: 1px solid rgba(255, 255, 255, 0.5);
    border-radius: 0;
    color: #fff;
  }
  .login-container >>> .el-input__inner::placeholder {
    color: rgba(255, 255, 255, 0.6);
  }
</style>
