import {get,post} from './http';
import Axios from 'axios';

//-----------------------歌手相关-------------------------//
//查询所有歌手
export const getAllSinger = () => get('singer/allSinger');

//根据性别查询歌手
export const getSingerOfSex = (sex) => get(`singer/singerOfSex?sex=${sex}`);

//-----------------------歌曲相关-------------------------//
//根据歌手id查询歌曲
export const songOfSingerId = (id) => get(`song/singer/detail?singerId=${id}`);

//根据歌曲id查询歌曲对象
export const songOfSongId = (songId) => get(`song/detail?songId=${songId}`);

//根据歌曲名获取歌曲对象
export const songOfSongName = (songName) => get(`song/songOfSongName?songName=${songName}`);

//根据歌曲名模糊查询歌曲对象
export const likeSongOfSongName = (keywords) => get(`song/likeSongOfSongName?songName=${keywords}`);

//查询所有歌曲
export const allSong = () => get('song/allSong');


//-----------------------歌单相关-------------------------//
//查询所有歌单
export const getAllSongList = () => get('songList/allSongList');

//返回标题包含文字的歌单列表
export const getSongListOfLikeTitle = (keywords) => get(`songList/likeTitle?title=${keywords}`);

//返回标题包含文字的歌单列表
export const getSongListOfLikeStyle = (style) => get(`songList/likeStyle?style=${style}`);

//-----------------------歌单的歌曲相关-------------------------//
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);

//-----------------------用户相关-------------------------//
//查询所有用户
export const getAllConsumer = () => get('consumer/allConsumer');

//注册
export const SignUp = (params) => post(`/consumer/add`,params)

//登录
export const loginIn = (params) => post(`/consumer/login`,params)

//根据用户id查询用户
export const getUserOfId = (id) => get(`/consumer/selectByPrimaryKey?id=${id}`)

//更新用户信息
export const updateUserMsg = (params) => post(`/consumer/update`,params);

//下载歌曲
export const download = (url) => Axios({
    method: 'get',
    url: url,
    responseType: 'blob'
});

//-----------------------评价------------------------------//
//提交评分
export const setRank = (params) => post(`/rank/add`,params)

//获取指定歌单的平均分
export const getRankOfSongListId = (songListId) => get(`/rank/average?songListId=${songListId}`);

//-----------------------评论-------------------------------//
//提交评论
export const setComment = (params) => post(`/comment/add`,params)

//点赞
export const setLike = (params) => post(`/comment/like`,params)

//返回当前歌单或歌曲的评论列表
export const getAllComment = (type,id) =>{
    if(type == 0){              //歌曲
        return get(`/comment/commentOfSongId?songId=${id}`)
    }else{                      //歌单
        return get(`/comment/commentOfSongListId?songListId=${id}`)
    }
} 


//-----------------------------收藏---------------------//
//新增收藏
export const setCollect =(params) => post(`/collect/add`,params);
//指定用户的收藏列表
export const getCollectOfUserId =(userId) => get(`/collect/collectOfUserId?userId=${userId}`);

