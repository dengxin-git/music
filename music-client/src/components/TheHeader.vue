<template>
  <div class="the-header">    
      <div class="header-logo">
          <svg class="icon">
              <use xlink:href = "#icon-erji"></use>
          </svg>
          <span>T兔T音乐网</span>
      </div>

      <ul class="navbar">
          <li :class="{active : item.name == activeName}" v-for="item in navMsg" :key="item.path" @click="goPage(item.path,item.name)">
              {{item.name}}
          </li>
          <li>
              <div class="header-search">
                  <input type="text" placeholder="搜索音乐" @keyup.enter="goSearch()" v-model="keywords">
                  <div class="search-btn" @click="goSearch()">
                      <svg class="icon">
                          <use xlink:href = "#icon-sousuo"></use>
                      </svg>
                  </div>
              </div>
          </li>

           <li v-show="!loginIn" :class="{active : item.name == activeName}" v-for="item in loginMsg" :key="item.path" @click="goPage(item.path,item.name)">
              {{item.name}}
          </li>
      </ul>

      <div class="header-right" v-show="loginIn">
          <div id="user">
              <img :src="attachImageUrl(avator)"/>
          </div>
          <ul class="menu">
              <li v-for="(item,index) in menuList" :key="index" @click="goMenuList(item.path)">{{item.name}}</li>
          </ul>
      </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { navMsg,loginMsg,menuList } from "../assets/data/header";
export default {
  inject: ['reload'],
  name: 'the-header',
  data(){
      return{
          navMsg:[], //左侧导航栏
          loginMsg:[], //右侧导航栏
          keywords:'',  //搜索关键字
          menuList: [], //用户下拉菜单
          hasOpenMenu: false //是否打开菜单
      }
  },
  computed:{
      ...mapGetters([
          'activeName',
          'loginIn',
          'avator'
      ])
  },
  created(){
      this.navMsg = navMsg;
      this.loginMsg = loginMsg;
      this.menuList = menuList;
  },
  mounted(){
    //  document.querySelector('#user').addEventListener('click',function(e){
    //       document.querySelector('.menu').classList.add("show");
    //       this.hasOpenMenu=true;
    //       e.stopPropagation()   //阻止冒泡
    //   },false); 
      document.querySelector('.menu').addEventListener('click',function(e){
          e.stopPropagation()   //点击菜单内部时,阻止冒泡,这样点击内部时，菜单不会关闭
      },false);
      document.addEventListener('click',function(){
          document.querySelector('.menu').classList.remove('show');
          this.hasOpenMenu=false;
      });
    let _this = this;
       document.querySelector('#user').addEventListener('click',function(e){
            if(_this.hasOpenMenu==true){
                document.querySelector('.menu').classList.remove('show');
                _this.hasOpenMenu=false;
                e.stopPropagation()   //阻止冒泡
            }else{
                document.querySelector('.menu').classList.add("show");
                _this.hasOpenMenu=true;
                e.stopPropagation()   //阻止冒泡
            }
      },true); 
  },
  methods:{
       //提示信息
        notify(title,type){
            this.$notify({
                title:title,
                type:type,
                duration:1500  //通知框消失时间
            })
        },
      goHome(){
          this.$router.push({path:'/'});
      },
      goPage(path,name){
          if(!this.loginIn && path=='/my-music'){
              this.notify('请先登录','warning');
          }else{
            this.$store.commit('setActiveName',name)
            this.$router.push({path: path});
          }
         
      },
      goSearch(){
          this.reload();
          this.$router.push({path:'/search',query:{keywords: this.keywords}});
          this.$store.commit('setActiveName','')
      },
      //获取图片
      attachImageUrl(srcUrl){
            return srcUrl ? this.$store.state.configure.HOST+srcUrl : '../assets/img/user.jpg';
      },
      goMenuList(path){
          if(path == 0){
              this.$store.commit('setLoginIn',false);
              this.$store.commit('setIsActive',false);
              this.$router.go(0);   //刷新页面
          }else{
              this.$router.push({path:path})
          }
      },
    
  }
}
</script>

<style  lang="scss" scoped>
@import '../assets/css/the-header.scss';

</style>
