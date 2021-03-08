<template>
<div>
    <div class="info">
        <div class="title">
            <span>编辑个人资料</span>
        </div>
        <hr>
        <div class="personal">
            <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
                <el-form-item prop="username" label="用户名">
                    <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="性别" size="mini">
                    <input type="radio" name="sex" value="0" v-model="registerForm.sex"/>&nbsp;女&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="sex" value="1" v-model="registerForm.sex"/>&nbsp;男
                </el-form-item>
                <el-form-item label="手机" prop="phoneNum" placeholder="手机">
                        <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" placeholder="邮箱">
                        <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="日期" prop="birth" placeholder="生日">
                        <el-date-picker :editable="false"  v-model="registerForm.birth" placeholder="选择日期" style="width: 100%"></el-date-picker>
                </el-form-item>
                <el-form-item label="签名" prop="introduction" placeholder="签名">
                        <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
                </el-form-item>
                <el-form-item label="地区" prop="location" placeholder="地区">
                    <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%">
                        <el-option v-for="item in cities" :key="item.value" :label="item.lable" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div class="btn">
                <el-button type="primary" @click="saveMsg">保存</el-button>
                <el-button @click="goback(-1)">取消</el-button>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {mapGetters} from 'vuex'
import {rules,cities} from '../assets/data/form' 
import {mixin} from '../mixins'
import {getUserOfId,updateUserMsg} from '../api/index'
export default {
    name:'info',
    mixins:[mixin],
    data(){
        return{
            registerForm: {
                username: '',
                password: '',
                sex: '1',
                phoneNum: '',
                email: '',
                birth: '',
                introduction: '',
                location: '',
            },
            cities: [],         //所有省直辖市
            rules: {}       //表单提交规则
        } 
    },
    computed:{
        ...mapGetters([
            'userId'
        ])
    },
    created(){
        this.rules = rules;
        this.cities = cities;
    },
    mounted(){
        this.getMsg(this.userId);
    },
    methods:{
          getMsg(userId){
            getUserOfId(userId)
            .then(res =>{
               this.registerForm.username = res.username;
               this.registerForm.password = res.password;
               this.registerForm.sex = res.sex;
               this.registerForm.phoneNum = res.phoneNum;
               this.registerForm.email = res.email;
               this.registerForm.birth = res.birthday;
               this.registerForm.introduction = res.introduction;
               this.registerForm.location = res.location;
            })
        },
        saveMsg(){
         this.$refs['registerForm'].validate(valid=>{  //验证表单数据，是否满足rules
            if(valid){
            let _this = this;
            let d = new Date(this.registerForm.birth);
            let datetime = d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
            let params = new URLSearchParams();
            params.append('id',this.userId);
            params.append('username',this.registerForm.username);
            params.append('password',this.registerForm.password);
            params.append('sex',this.registerForm.sex);
            params.append('phoneNum',this.registerForm.phoneNum);
            params.append('email',this.registerForm.email);
            params.append('birthday',datetime);
            params.append('introduction',this.registerForm.introduction);
            params.append('location',this.registerForm.location);
            updateUserMsg(params)
              .then(res => {
                if(res.code==1){
                    this.$store.commit('setUserName',this.registerForm.username);
                    this.notify('修改成功','success');
                    setTimeout(function(){
                        _this.$store.commit('setActiveName','')
                    },1000);
                }else{
                    this.notify('修改失败','error');
                }
            })
            .catch(err => {
                this.notify('修改失败','error');
            })
             }
          })
        },
        goback(index){
            this.$store.commit('setActiveName','首页')
            this.$router.push({path: '/'});
        }
    }
}
</script>

<style  lang="scss" scoped>
@import '../assets/css/info.scss';
</style>