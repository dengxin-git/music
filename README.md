# music
tutingting.cn(特意为该项目申请了一个域名)
tutingting.cn/system(音乐后台管理系统)
由于歌曲、图片等文件太多且网络也能找到现成的资源，所以就不做上传
前后端项目都放到了服务器中，后端打成jar包并放到服务器的screen上运行保证后台不退出


=============================================================================================

原作者地址：https://github.com/Yin-Hongwei/music-website

该项目原本为GitHub上的开源项目，本人将其用作学习，重新编写了后端、大部分前端Vue的有关数据交互的js代码、前
端页面使用作者提供的页面、布局和样式，并修复了作者的部分bug 。项目已经部署到服务器且加了域名可直接访问。
开发模块：用户登录注册 、歌手歌单歌曲等列表分页显示、歌单歌曲搜索、 歌单打分 、歌曲评论 、音乐播放 、拖动控制、
音量控制、歌词同步显示 、音乐收藏、后台对用户、歌曲、歌手、歌单信息的CRUD管理操作等
相关问题解决方案
1.后端通过重写addCorsMappings进行跨域配置，后端只提供api接口返回JSON数据，实现项目的前后端分离
2.使用MyBatis的动态SQL实现动态更新和插入(只更新不为NULL的字段),使用ResultMap映射Entity和表字段
3.前端使用Axios调用后台api接口实现前后端数据交互。
4.用build命令将音乐网站和后台Admin打包成静态文件部署到Linux服务器上的Nginx中，后端打成jar包部署到服务器
5.优化了部分作者弹窗显示错误的bug。
使用技术: 
SpringBoot+Mybatis+Maven +MySQL+Nginx+Vue+Vue-Router+Axios+ElementUI 
