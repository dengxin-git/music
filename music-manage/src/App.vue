<template>
  <div id="app">
    <SongAudio/>
    <router-view></router-view>
  </div>
</template>

<script>
import SongAudio from './component/SongAudio.vue';
export default {
  name: 'App',
  components:{
    SongAudio
  }
}
</script>

<style>
</style>
