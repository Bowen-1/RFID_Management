<template>
    <el-form label-width="90px" :inline="true" class="login-container" :model="loginForm" :rules="rules" ref="loginForm">
        <h3 class="login_title">Login</h3>
        <el-form-item label: label="Username:" prop="name">
            <el-input v-model="loginForm.name" placeholder="Please enter username"></el-input>
        </el-form-item>

        <el-form-item label: label="Password:" prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="Please enter password"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm">Login</el-button>
            <el-button @click="resetForm">Reset</el-button>
        </el-form-item>
    </el-form>
</template>

<script>

import Axios from 'axios';
export default {
  data() {
    return {
      loginForm: {
        name: '',
        password: ''
      },

      type: 'user',

      rules: {
        name: [
          { required: true, message: 'Please enter name', trigger: 'blur' },
          { min: 1, max: 8, message: 'Charcters between 3 to 12', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter password', trigger: 'blur' },
          { min: 1, max: 8, message: 'Charcters between 3 to 12', trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm() {
      // console.log(this.loginForm)
      console.log(this.type)
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          let _this = this
          if (_this.type == 'user') {
            Axios.get('http://localhost:8181/user/login', { params: _this.loginForm }).then(function (resp) {
              // console.log(resp)
              console.log(_this.loginForm)

              if(resp.data.code == -1){
                _this.$alert('user do not exist','alert',{
                  confirmButtonText:'Ok'
                })
              }
              if(resp.data.code == -2){
                _this.$alert('password is not correct','alert',{
                  confirmButtonText:'Ok'
                })
              }
              if(resp.data.code == 0){

                  // localStorage.setItem('systemAdmin', JSON.stringify(resp.data.data));
                   _this.$router.replace({path: '/main'})
              }
            })
          }

        }
      });
    },
    resetForm() {
      this.$refs.loginForm.resetFields();
    }
  }
}
</script>
<style lang="less" scoped>
.login-container {
    width: 280px;
    border: 1px solid #eaeaea;
    margin: 180px auto;
    padding: 35px 35px 15px 35px;
    background-color: #fff;
    border-radius: 15px;
    box-shadow: 0 0 25px #cac6c6;
    box-sizing: border-box;

    .el-input {
        width: 198px;
    }

    .login_title {
        text-align: center;
    }


}
</style>