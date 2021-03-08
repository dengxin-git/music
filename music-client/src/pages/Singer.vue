<template>
    <div class="singer">
        <ul class="singer-header">
            <li v-for="(item,index) in singerStyle" :key="index" @click="handleChangeView(item)"
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
import {getAllSinger,getSingerOfSex} from '../api/index';
import {mixin} from "../mixins"
import {singerStyle} from '../assets/data/singer';
export default {
  components: { 
      ContentList 
      },
  name: 'singer',
  data(){
      return{
          albumDatas: [],  //歌手集合
          pageSize: 10,     //页面大小,一页十五条
          currentPage: 1,  //当前页,默认值1
          singerStyle:[]  ,//风格
          activeName: '全部歌手' ,  //当前风格

      }
  },
  computed:{
      //计算当前表格中的数据
      data(){
         return this.albumDatas.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
      }
  },
  mounted(){
      this.singerStyle = singerStyle;
      this.getSingerList();
  },
  methods:{
      getSingerList(){
              getAllSinger()
              .then(res => {
                      this.currentPage = 1;
                      this.albumDatas = res;
              })
      },
      //获取当前页
      handleCurrentChange(val){
          this.currentPage = val;
      },
      //根据type显示对应的歌单
      handleChangeView(item){
           this.activeName = item.name;
           this.albumDatas = [];
           if(item.name == '全部歌手'){
               this.getSingerList();
           }else{
               this.getSingerOfSex(item.type)
           }
      },
      //根据type查询对应的歌单
      getSingerOfSex(type){
            getSingerOfSex(type)
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
@import '../assets/css/singer.scss'
</style>
