<template>
    <div>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="6">
                   <el-card>
                       <div class="grid-content">
                           <div class="grid-content-center">
                                <div class="grid-num">{{consumerCount}}</div>
                                <div>用户总数</div>
                            </div>
                       </div>
                   </el-card>
            </el-col>    
            <el-col :span="6">
                   <el-card>
                       <div class="grid-content">
                           <div class="grid-content-center">
                                <div class="grid-num">{{songCount}}</div>
                                <div>歌曲总数</div>
                            </div>
                       </div>
                   </el-card>
            </el-col>    
            <el-col :span="6">
                   <el-card>
                       <div class="grid-content">
                           <div class="grid-content-center">
                                <div class="grid-num">{{singerCount}}</div>
                                <div>歌手数量</div>
                            </div>
                       </div>
                   </el-card>
            </el-col>   
            <el-col :span="6">
                   <el-card>
                       <div class="grid-content">
                           <div class="grid-content-center">
                                <div class="grid-num">{{songListCount}}</div>
                                <div>歌单数量</div>
                            </div>
                       </div>
                   </el-card>
            </el-col>   
        </el-row>
        
        <el-row :gutter="20" class="mgb20">
            <el-col :span="12">
                <h3 class="mgb20">用户性别比列</h3>
                <div style="background-color:white">
                    <ve-pie :data="consumerSex" :theme='options'></ve-pie>
                </div>
            </el-col>   

            <el-col :span="12" >
                <h3 class="mgb20">歌曲类型分布</h3>
                <div style="background-color:white">
                    <ve-histogram :data="songStyle" ></ve-histogram>
                </div>
            </el-col>  
        </el-row>    

        <el-row :gutter="20" class="mgb20">
            <el-col :span="12">
                <h3 class="mgb20">歌手性别比列</h3>
                <div style="background-color:white">
                    <ve-pie :data="singerSex"></ve-pie>
                </div>
            </el-col>   
            <el-col :span="12">
                <h3 class="mgb20">歌手国籍分布</h3>
                <div style="background-color:white">
                     <ve-histogram :data="country" :theme='options1' ></ve-histogram>
                </div>
            </el-col>   
        </el-row> 
    </div>
</template>



<script>
import {allSong, getAllConsumer, getAllSinger,getAllSongList } from '../api/index';
export default {
    data(){
        return {
            consumerCount: 0, //用户总数
            songCount: 0, //歌曲总数
            singerCount: 0, //歌手数量
            songListCount: 0, //歌单数量
            consumer:[],  //所有用户对象
            consumerSex:{  //按性别分类的用户数
                columns:['性别','总数'],
                rows:[
                    {'性别':'男','总数':0},  
                    {'性别':'女','总数':0},
                ]
            },
            options:{
                color:['#87cefa','#ffc2cb']
            },
            options1:{
                color:['#87a8fa']
            },
            songStyle:{
                columns:['风格','总数'],
                rows:[
                    {'风格':'华语','总数':0},
                    {'风格':'粤语','总数':0},
                    {'风格':'欧美','总数':0},
                    {'风格':'BGM','总数':0},
                    {'风格':'日韩','总数':0},
                    {'风格':'轻音乐','总数':0},
                    {'风格':'乐器','总数':0},
                ]
            },
            singerSex:{
                columns:['性别','总数'],
                rows:[
                    {'性别':'组合','总数':0},
                    {'性别':'男','总数':0},
                    {'性别':'女','总数':0},
                    {'性别':'不明','总数':0},
                ]
            },
            country:{
                columns:['国籍','总数'],
                rows:[
                    {'国籍':'中国','总数':0},
                    {'国籍':'韩国','总数':0},
                    {'国籍':'日本','总数':0},
                    {'国籍':'美国','总数':0},
                    {'国籍':'英国','总数':0},
                    {'国籍':'新加坡','总数':0},
                    {'国籍':'澳大利亚','总数':0},
                    {'国籍':'西班牙','总数':0},
                ]
            }
        }
    },

    created(){

    },

    //页面渲染完成后调用
    mounted(){
        this.getConsumer();
        this.getAllSong();
        this.allSinger();
        this.allSongList();
        this.setSongStyle();
    },

    methods:{
        getConsumer(){
            getAllConsumer().then(res=>{
                this.consumer=res;
                this.consumerCount=res.length;
                this.consumerSex.rows[0]['总数'] = this.setSex(1,this.consumer);  //分别获取用户中的男女对象
                this.consumerSex.rows[1]['总数'] = this.setSex(0,this.consumer);
                
            })
        },

        getAllSong(){
            allSong().then(res=>{
                this.songCount=res.length;
            })
        },
        
        allSinger(){
            getAllSinger().then(res=>{
                this.singerCount=res.length;
                this.singerSex.rows[0]['总数'] = this.setSex(1,res);  //分别获取歌手中不同性别的对象
                this.singerSex.rows[1]['总数'] = this.setSex(0,res);  
                this.singerSex.rows[2]['总数'] = this.setSex(2,res);  
                this.singerSex.rows[3]['总数'] = this.setSex(3,res);  
                for(let item of res){
                    this.setCountry(item.location);
                }
            })
        }, 
        allSongList(){
            getAllSongList().then(res=>{
                this.songListCount=res.length;
                for(let item of res){
                    this.setSongStyle(item.style);
                }
            })
        },

        //根据性别设置用户或者歌手数
        setSex(sex,obj){
            let count = 0;
            for(let item of obj){
                if(sex==item.sex){
                    count++;
                }
            }
            return count;
        },

        //根据风格设置歌曲数
        setSongStyle(style){
            for(let item of this.songStyle.rows){
                if(style==item['风格']){
                    item['总数']++;
                }
            }
        },  
        setCountry(location){
            for(let item of this.country.rows){
                if(location==item['国籍']){
                    item['总数']++;
                }
            }
        },
    }


}
</script>


<style scoped>
.grid-content{
    display: flex;
    align-items: center;
    height: 50px;
}
.grid-content-center{
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: darkgray;
}

.grid-num{
    font-size: 30px;
    font-weight: bold;
}
</style>