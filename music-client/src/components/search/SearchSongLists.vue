<template>
    <div class="search-song-Lists">
       <content-list :contentList="albumDatas"></content-list>
    </div>
</template>

<script>
import ContentList from '../ContentList.vue';
import {getSongListOfLikeTitle} from '../../api/index';
export default {
  inject:['reload'],
  components: { 
      ContentList 
      },
  name: 'search-song-lists',
  data(){
      return{
          albumDatas: []
      }
  },
  mounted(){
      this.getSearchList();
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
      getSearchList(){

          if(!this.$route.query.keywords){
              this.notify('您输入的内容为空','warning');
          }else{
              getSongListOfLikeTitle(this.$route.query.keywords)
              .then(res => {
                  if(res){
                      this.albumDatas = res;
                  }else{
                      this.notify('暂无该歌单内容','warning');
                  }
              })
              .catch(err =>{})
            //   .finally(()=>{
            //       this.reload();
            //   })
          }
      }
  }
}
</script>



<style lang="scss" scoped>
@import '../../assets/css/search-song-lists.scss'
</style>
