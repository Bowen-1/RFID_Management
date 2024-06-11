<template>
  <div style="margin-top: 60px;margin-left:330px;width: 300px;height: 500px;border: 0px solid red;" >
    <el-form :model="rfidForm" :rules="rules" ref="rfidForm" label-width="100px" class="demo-rfidForm">
      <el-form-item label: label="Name" prop="name">
        <el-input v-model="rfidForm.name"></el-input>
      </el-form-item>
    
      <el-form-item label: label="Location" prop="location">
        <el-input v-model="rfidForm.location"></el-input>
      </el-form-item>
    
      <el-form-item>
        <el-button type="primary" @click="submitForm('rfidForm')">Create</el-button>
        <el-button @click="resetForm('rfidForm')">重置</el-button>
      </el-form-item>
    
    
    </el-form>
  </div>
    </template>
    
    
    <script>
    
    import axios from 'axios';
      export default {
        data() {
          return {
            rfidForm: {
              name: '',
              location: ''
    
            },
            rules: {
              name: [
                { required: true, message: 'Please enter name', trigger: 'blur' },
                { min: 1, max: 20, message: 'Charcters between 3 to 12', trigger: 'blur' }
              ],
              location: [
                { required: true, message: 'Please enter password', trigger: 'blur' },
                { min: 1, max: 20, message: 'Charcters between 3 to 12', trigger: 'blur' }
              ]
    
            }
          };
        },
        methods: {
          submitForm() {
            let _this = this
            this.$refs.rfidForm.validate((valid) => {
              if (valid) {
                console.log(_this.rfidForm)
                axios.post('http://localhost:8181/rfid/save', _this.rfidForm).then(function(resp){
                if(resp.data.code ==0){
                  _this.$alert(_this.rfidForm.name + 'success', '',{
                    confirmButtonText: 'Ok',
                  })
                  // this.$refs.rfidForm.resetFields();
                
                }else{
                  alert('fail')
                }
    
              })
    
              // Axios.post('http://localhost:8181/rfid/create', { params: _this.rfidForm }).then(function (resp) {
              //     // console.log(resp)
              //     console.log(_this.rfidForm)
              //              if(resp.data.code ==0){
              //     _this.$alert(_this.rfidForm.name + 'success', '',{
              //       confirmButtonText: 'Ok',
              //     })
              //     this.$refs[rfidForm].resetFields();
                
              //   }else{
              //     alert('fail')
              //   }
    
        
              //   })
    
      
      
              }
            });
          },
          resetForm(rfidForm) {
            this.$refs[rfidForm].resetFields();
          }
        }
      }
    </script>

