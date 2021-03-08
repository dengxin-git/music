export const mixin ={
    methods:{
        notify(title,type){
            this.$notify({
                title:title,
                type:type,
                duration:1500  //通知框消失时间
            });
        },

        //根据相对地址过去绝对地址
        getUrl(url){
            //return `${this.$store.state.HOST}/${url}` 
            return this.$store.state.HOST+'/'+url
        },
        
        //获取中文性别
        changeSex(value){
            if(value==0){
                return '女';
            }else if(value==1){
                return '男';
            }else if(value==2){
                return '组合';
            }else if(value==3){
                return '不明';
            }
            return value;
        },

        //获取生日
        attachBirth(value){
            return String(value).substr(0,10);
        },

        //上传图片之前的校验
        beforeAvatorUpload(file){
            const isJPG = (file.type==='image/jpeg')||(file.type==='image/png');
            if(!isJPG){
                this.$message.error("上传头像只能是jpg或png格式");
                return false;
            }
            const isLt2M = (file.size / 1024 / 1024)<2;
            if(!isLt2M){
                this.$message.error("图片大小不能超过2M");
                return false;
            }
            return true;
        },

        handleAvatorSuccess(res){
            let _this = this;
            if(res.code==1){
                _this.getData();
                _this.notify(res.msg,"success");
            }else{
                _this.getData();
                _this.notify(res.msg,"error");
            }    
        },

        handleDelete(id){
            this.idx = id;
            this.deleteVisible = true;
        },

        //把已经选择的项赋值给multipleSelection
        handleSelectionChange(val){
            this.multipleSelection = val;
        },

        delAll(){
            for(let item of this.multipleSelection){
                this.handleDelete(item.id);
                this.deleteRow();
            }
            this.multipleSelection = [];
        }
    }

   
}