<template>
    <div class="singer-album">
        <div class="album-slide">
            <div class="singer-img">
               <img :src="attachImageUrl(singer.pic)">
            </div>
            <ul class="info">
               <li v-if="singer.sex==0||singer.sex==1">{{attachSex(singer.sex)}}</li>
               <li>生日: {{attachBirth(singer.birth)}}</li>
               <li>故乡: {{singer.location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="intro">
                <h2>{{singer.name}}</h2>
                <span>{{singer.introduction}}</span>
            </div>
            <div class="content">
                 <album-content :songList="listOfSongs">
                        <template slot="title">歌曲列表</template>
                 </album-content>
            </div>
        </div>
    </div>
</template>

<script>
import {mixin} from '../mixins';
import {mapGetters} from 'vuex';
import { songOfSingerId } from '../api/index';
import AlbumContent from '../components/AlbumContent.vue'
export default {
        name: 'singer-album',
        mixins: [mixin],
        components:{
            AlbumContent,
    },
    data(){
        return {
            singerId: '',  //传入的歌手id
            singer: {}, //当前歌手对象
        }
    },
    computed:{
        ...mapGetters([
            'listOfSongs', //当前播放列表
            'tempList',  //当前的歌手对象,但刷新后会失效，所以使用localStorage代替
            'loginIn',  //用户是否已经登陆
            'userId',  //当前登录用户id
        ])
    },
    created(){
        this.singerId = this.$route.params.id;  //传过来的id
        this.singer = JSON.parse(window.localStorage.getItem("item"));
      //当前的歌手对象,但刷新后会失效，所以使用localStorage代替
        this.getSongOfSingerId();
    },
     methods:{
        //获取当前歌手的歌曲列表
        getSongOfSingerId(){
            songOfSingerId(this.singerId)
            .then(res =>{
                this.$store.commit('setListOfSongs',res);
            })
             .catch(err => {
                console.log(err)
            })
        },

        //根据歌曲id获取歌曲信息
        getSongList(id){
            songOfSongId(id)
            .then(res =>{
                this.songLists.push(res);
            })
            .catch(err => {
                console.log(err)
            })
        },
        //获取性别
        attachSex(value){
            if(value==0){
                return '女';
            }else if(value==1){
                return '男';
            }
            return '';
        }
    }
}
</script>

<style lang="scss" scoped>
@import "../assets/css/singer-album.scss";
</style>