$.extend(thisApp,{
	'reader' : $('#icr_reader').get(0),
    'port_list':[1,2,1001,1002],
    'inited':false,
	'readCard' : function() {
        var ret = {};
        $.extend(ret, thisApp._defualt_card_data);
        for ( var i = 0; i < this.port_list.length; i++) {
            var isOpen = this.reader.OpenComm(this.port_list[i]);
            if (isOpen === 1){
                //alert('初始化读卡器端口在:'+port_list[i]);
                this.inited = true;
                break;
            }
        }
        if(this.inited){
            if(this.reader.Authen() === 1){
               if(this.reader.ReadCardPath("",1) ===1){
                    ret.success = true;
                    ret.name = this.reader.sName;
		    //alert(ret.name);
		    //alert(this.reader.sName);
                    ret.sex=this.reader.sSex;
		            ret.sex_name = this._data_map.sex_map[ret.sex];
		            ret.id = this.reader.sIDNo;
		            ret.nation =  this._data_map.nation_map[this.reader.sNation];
		            ret.birthday = this.reader.sBornDate;       
		            ret.address = this.reader.sAddress;
		            ret.auth_dept = this.reader.sSignGov;
		            ret.valid_period =this.reader.sEndDate;
		            this.reader.ReadCard(3);
		            ret.last_address = this.reader.sNewAddress;
		            //add by xiebb 2011-10-25  身份验证的时候增加图片存储功能 start
		       		  ret.photoString=this.reader.PhotoBuffer;
		        		//add by xiebb 2011-10-25  身份验证的时候增加图片存储功能 over
               }else{
                 ret.message = '读卡失败,请重试!';
               }
            }else{
               ret.message = '读卡器认证失败,请重新放卡!';
            }
        }else{
           ret.message = '初始化读卡器失败!';
        }
        this.reader.EndComm();
		return ret;
	}
});