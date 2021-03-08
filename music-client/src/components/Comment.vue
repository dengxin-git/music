<template>
    <div>
        <div class="comment">
            <h2>评论</h2>
            <div class="comment-msg">
                <div class="comment-img">
                    <img  :src=" attachImageUrl(avator)">
                </div>
                <el-input class="comment-input" type="textarea" :rows="3" placeholder="请输入评价内容" v-model="textarea"></el-input>
            </div>
            <el-button type="primary" class="sub-btn" @click="postComment">评论</el-button>
        </div>
        <p>共{{commentList.length}}条评论</p>
        <ul class="popular" v-for="(item,index) in commentList" :key="index">
            <li>
                <div class="popular-img">
                    <img :src="attachImageUrl(userPic[index])">
                </div>
                <div class="popular-msg">
                    <ul>
                        <li class="name">{{userName[index]}}</li>
                        <li class="time">{{item.createTime}}</li>
                        <li class="content">{{item.content}}</li>
                    </ul>
                </div>
                <div class="up" ref="up" @click="postUp(item.id,item.up,index)">
                     <svg class="icon">
                           <use xlink:href = "#icon-zan"></use>
                     </svg>
                     {{item.up}}
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import {setComment,setLike,getRankOfSongListId,getUserOfId,getAllComment} from '../api/index';
import { mixin } from "../mixins";
export default {
  name: "comment",
  mixins: [mixin],
  props: [
      "playId",  //歌曲或者歌单的id
      "type",   //0歌曲1歌单
    ],
  computed:{
        ...mapGetters([
            'id', //歌单或歌曲id
            'loginIn',  //用户是否已经登陆
            'userId',  //当前登录用户id
            'avator',  //当前登录用户的头像
        ])
   },
   data(){
       return{
           textarea: '' ,  //输入的评论
           commentList: [],  //存放评论列表
           userPic: [],   //用户的头像
           userName: [], //用户的名字
       }
   },
   mounted(){
       this. getComment();
   },
   methods: {
    //提交评论
    postComment(){
        if(this.loginIn){
            let params = new URLSearchParams();
            if(this.type==0){
                params.append('songId',this.playId);
            }else{
                params.append('songListId',this.playId);
            }
            params.append('userId',this.userId);
            params.append('type',this.type);
            params.append('content',this.textarea);
            setComment(params)
                .then(res => {
                    if(res.code==1){
                        this.notify('评论成功','success');
                        this.textarea='';
                        this.getComment()
                    }else{
                        this.notify('评论失败','error')
                    }
                })
                .catch(err => {
                     this.notify('评论失败','error')
            })
        }else{
            this.notify('请先登录','warning');
        }
    },
    //评论列表
    getComment(){
        getAllComment(this.type,this.playId)
            .then(res => {
                this.commentList = res;
                for(let item of res){
                    this.getUsers(item.userId);
                }
            })
            .catch(err => {
                console.log(err);
            })
    },

    //获取用户头像和头像
    getUsers(id){
        getUserOfId(id)
            .then(res => {
                this.userPic.push(res.avator);
                this.userName.push(res.userName);
            })
            .catch(err =>{
                console.log(err);
            })
    },
    //给某个评论点赞
    postUp(id,up,index){
        if(this.loginIn){
            let params = new URLSearchParams();
            params.append('id',id);
            params.append('up',up+1);
            setLike(params)
                .then(res => {
                    if(res.code==1){
                        this.$refs.up[index].children[0].style.color = '#2796cd';
                        this.getComment();
                    }else{
                        this.notify('点赞失败','error')
                    }
                })
                .catch(err => {
                     this.notify('点赞失败','error')
            })
        }else{
            this.notify('请先登录','warning');
        }
    },
    }
};
</script>

<style lang="scss" scoped>
@import "../assets/css/comment.scss";
</style>s