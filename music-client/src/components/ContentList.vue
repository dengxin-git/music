<template>
    <div class="content-list">
        <ul class="section-content">
            <li class="content-item" v-for="(item,index) in contentList" :key="index">
                <div class="kuo" @click="goAlbum(item,item.name)">
                   <img class="item-img" :src="attachImageUrl(item.pic)"></img>
                    <div class="mask">
                        <svg class="icon">
                            <use xlink:href="#icon-bofang"></use>
                        </svg>
                    </div>
                </div>
                <p class="item-name">{{item.name||item.title}}</p>
            </li>
        </ul>
    </div>
</template>

<script>
import { mixin } from "../mixins";
export default {
  name: "content-list",
  mixins: [mixin],
  props: ["contentList"],
  methods: {
      goAlbum(item,type){
          window.localStorage.setItem("item", JSON.stringify(item));
          this.$store.commit("setTempList",item);  //刷新后会失效，使用上面的本地储存
          if(type){
              this.$router.push({path:`singer-album/${item.id}`});
          }else{
              this.$router.push({path:`song-list-album/${item.id}`});
          }
      }
  }
};
</script>

<style lang="scss" scoped>
@import "../assets/css/content-list.scss";
</style>