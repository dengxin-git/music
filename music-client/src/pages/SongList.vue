<template>
    <div class="song-list">
        <ul class="song-list-header">
            <li v-for="(item,index) in songStyle" :key="index" @click="handleChangeView(item.name)"
            :class="{active:item.name==activeName}">
                {{item.name}}
            </li>
        </ul>
        <div>
            <content-list :contentList="data"></content-list>
            <el-pagination style="text-align: center" @current-change="handleCurrentChange" background="" layout="total,prev,pager,next"
            :current-page="currentPage" :page-size="pageSize" :total="albumDatas.length"></el-pagination>
        </div>
    </div>
</template>

<script>
import ContentList from '../components/ContentList.vue';
import {getAllSongList, getSongListOfLikeStyle} from '../api/index';
import {mixin} from "../mixins"
import {songStyle} from '../assets/data/songList';
export default {
  components: { 
      ContentList 
      },
  name: 'song-lists',
  data(){
      return{
          albumDatas: [],  //歌单集合
          pageSize: 10,     //页面大小,一页十五条
          currentPage: 1,  //当前页,默认值1
          songStyle:[]  ,//风格
          activeName: '全部歌单' ,  //当前风格

      }
  },
  computed:{
      //计算当前表格中的数据
      data(){
         return this.albumDatas.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
      }
  },
  mounted(){
      this.songStyle = songStyle;
      this.getSongList();
  },
  methods:{
      getSongList(){
              getAllSongList()
              .then(res => {
                      this.currentPage = 1;
                      this.albumDatas = res;
              })
      },
      //获取当前页
      handleCurrentChange(val){
          this.currentPage = val;
      },
      //根据style显示对应的歌单
      handleChangeView(style){
           this.activeName = style;
           this.albumDatas = [];
           if(style == '全部歌单'){
               this.getSongList();
           }else{
               this.getSongListOfStyle(style)
           }
      },
      //根据style查询对应的歌单
      getSongListOfStyle(style){
            getSongListOfLikeStyle(style)
                .then(res => {
                    this.currentPage = 1;
                    this.albumDatas = res;
                })
                .catch(err => {
                    console.log(err);
                })
      },

  }
}
</script>



<style lang="scss" scoped>
@import '../assets/css/song-list.scss'
</style>
