<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="Key：" prop="key">
                <el-select v-model="ruleForm.key" style="width: 160px;float: left" placeholder="Choose Key Word">
                    <el-option label="ID" value="id"></el-option>
                    <el-option label="Name" value="name"></el-option>
                    <el-option label="Location" value="location"></el-option>
                </el-select>
            </el-form-item>
            <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
                <el-form-item label="Value：">
                    <el-input v-model="ruleForm.value" placeholder="Enter value" style="width: 160px;"></el-input>
                    <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm('ruleForm')">Search</el-button>
                </el-form-item>
            </div>
        </el-form>

        <el-table
                :data="tableData"
                border
                stripe
                style="width: 100%;position: relative;top:-30px">
            <el-table-column
                    fixed
                    prop="id"
                    label="ID"
                    width="160">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="Name"
                    width="160">
            </el-table-column>
            <el-table-column
                    prop="location"
                    label="Location"
                    width="160">
            </el-table-column>
            
            <el-table-column label="Modify">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="edit(scope.row)">Edit</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="del(scope.row)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination style="margin-top: 20px;float: right"
                       background
                       layout="prev, pager, next"
                       :page-size="pageSize"
                       :total="total"
                       :current-page.sync="currentPage"
                       @current-change="page">
        </el-pagination>
    </div>

</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                tableData:null,
                currentPage: 1,
                pageSize: 3,
                total: '',
                key: '',
                value: '',
                ruleForm: {
                    key: '',
                    value: '',
                    page: '',
                    size: 3
                },
                rules: {
                    key: [
                        { required: true, message: 'Please choose key', trigger: 'change' }
                    ]
                }
            }
        },

        methods:{
            submitForm(formName) {
                const _this = this
                _this.currentPage = 1
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this
                        _this.ruleForm.page = _this.currentPage
                        axios.get('http://localhost:8181/rfid/search',{params:_this.ruleForm}).then(function (resp) {
                            _this.tableData = resp.data.data.data
                            _this.total = resp.data.data.total
                        })
                    }
                });
            },
            page(currentPage){
                const _this = this
                if(_this.ruleForm.value == ''){
                    axios.get('http://localhost:8181/rfid/list/'+currentPage+'/'+_this.pageSize).then(function (resp) {
                        _this.tableData = resp.data.data.data
                        _this.total = resp.data.data.total
                    })
                } else {
                    _this.ruleForm.page = _this.currentPage
                    axios.get('http://localhost:8181/rfid/search',{params:_this.ruleForm}).then(function (resp) {
                        _this.tableData = resp.data.data.data
                        _this.total = resp.data.data.total
                    })
                }
            },
            edit(row){
                this.$router.push('/manageRFID?id='+row.id)
            },
            del(row) {
                const _this = this
                this.$confirm('Confirm Deleting【'+row.name+'】？', 'Attention', {
                    confirmButtonText: 'Confirm',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    axios.delete('http://localhost:8181/rfid/deleteById/'+row.id).then(function (resp) {
                        if(resp.data.code==0){
                            _this.$alert('【'+row.name+'】has been deleted', '', {
                                confirmButtonText: 'Confirm',
                                callback: action => {
                                    location.reload()
                                }
                            });
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type:'info',
                        message:'ok'
                    })
                });
            }
        },
        created() {
            const _this = this
            axios.get('http://localhost:8181/rfid/list/1/'+ _this.pageSize).then(function (resp) {

                 _this.tableData = resp.data.data.data
                 _this.total = resp.data.data.total
                console.log(resp.data)

                // _this.tableData = resp.data.data.data
                // _this.total = resp.data.data.total
            })
        }
        
    }
</script>