<template>
  <div id="app">
    <the-header />
    <router-view class="music-content" v-if="isRouterAlive" />
    <song-audio></song-audio>
    <the-aside></the-aside>
    <play-bar></play-bar>
    <scroll-top></scroll-top>
    <the-footer></the-footer>
  </div>
</template>

<script>
import PlayBar from './components/PlayBar.vue';
import ScrollTop from "./components/ScrollTop.vue";
import SongAudio from "./components/SongAudio.vue";
import TheAside from './components/TheAside.vue';
import TheFooter from "./components/TheFooter.vue";
import TheHeader from "./components/TheHeader.vue";

export default {
  name: "App",
  provide(){
    return{
      reload: this.reload
    }
  },
  data(){
    return{
      isRouterAlive: true
    }
  },
  methods:{
    reload(){
      this.isRouterAlive = false;
      this.$nextTick(function(){
        this.isRouterAlive = true;
      })
    }
  },
  components: {
    TheHeader,
    ScrollTop,
    TheFooter,
    SongAudio,
    PlayBar,
    TheAside,
  },
};
</script>

<style  lang="scss" scoped>
@import "./assets/css/app.scss";
</style>
