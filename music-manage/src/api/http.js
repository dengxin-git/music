import axios from 'axios';
import router from '../router';

axios.defaults.timeout = 5000;
axios.defaults.withCredentials = true; //允许跨域
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
//基础url
axios.defaults.baseURL = "http://47.115.25.201:9000",
//响应拦截器
axios.interceptors.response.use(
        response => {
            if(response.status == 200){
                return Promise.resolve(response);
            }else{
                return Promise.reject(response);
            }
        },
        error => {
            if(error.response.status){
                switch(error.response.status){
                    case 401:
                        router.replace({
                            path:'/',
                            query:{
                                redirect: router.currentRoute.fullPath
                            }
                        });
                    case 404:
                        break;
                }
                return Promise.reject(error.response);
            }
        }
);

export function get(url,params={}){
    return new Promise((resolve,reject)=>{
        axios.get(url,{params:params})
        .then(response =>{
            resolve(response.data);
        })
        .catch(err =>{
            reject(err);
        })
    });
}

export function post(url,data={}){
    return new Promise((resolve,reject)=>{
        axios.post(url,data)
        .then(response =>{
            resolve(response.data);
        })
        .catch(err =>{
            reject(err);
        })
    });
}
