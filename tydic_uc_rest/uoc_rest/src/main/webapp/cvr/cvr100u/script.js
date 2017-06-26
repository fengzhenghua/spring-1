var thisApp = {};
$.extend(thisApp,{
    'reader' : $('#CVR_IDCard').get(0),
    'err_msg' : {
        '0' : '操作成功',
        '-1' : '未链接设备',
        '-2' : '放卡超时',
        '-3' : '用户取消',
        '-4' : '读取基本信息失败',
        '-5' : '照片创建失败'
    },
    clear : function() {
        this.reader.Name = "";// 姓名
        this.reader.Sex = "";// 性别
        this.reader.Nation = "";// 民族
        this.reader.Born = "";// 出生日期
        this.reader.Address = "";// 住址
        this.reader.CardNo = "";// 身份证号
        this.reader.IssuedAt = "";// 签发机关
        this.reader.EffectedDate = ""; // 起始日期
        this.reader.ExpiredDate = ""; // 结束日期
        this.reader.CardReaderId = ""; //阅读器终端ID
        this.reader.Picture = ""; // 照片编码 
        return true;
    },
    readCard : function() {
        this.clear();
        var result = this.reader.ReadCard();
        var ret = {};
        if (result == "0") {
            ret.name = this.reader.Name;
            ret.sex = this.reader.Sex;
            ret.nation = this.reader.Nation;
            ret.birthday = this.reader.Born;
	        if(ret.birthday.length == 8){
		        var y = ret.birthday.substr(0, 4);
		        var m = ret.birthday.substr(4, 2);
		        var d = ret.birthday.substr(6, 2);
		        ret.birthday = y + "-" + m + "-" + d;
	        }
            ret.address = this.reader.Address;
            ret.id = this.reader.CardNo;
            ret.issuedAt = this.reader.IssuedAt;
            ret.effectedDate = this.reader.EffectedDate;
            if(ret.effectedDate.length == 8){
	        	var y = ret.effectedDate.substr(0, 4);
	            var m = ret.effectedDate.substr(4, 2);
	            var d = ret.effectedDate.substr(6, 2);
	            ret.effectedDate = y + "-" + m + "-" + d; 
        	}
            ret.expiredDate = this.reader.ExpiredDate;
            if(ret.expiredDate.length >= 8){
	        	var y = ret.expiredDate.substr(0, 4);
	            var m = ret.expiredDate.substr(4, 2);
	            var d = ret.expiredDate.substr(6, 2);
	            ret.expiredDate = y + "-" + m + "-" + d; 
            }
            ret.cardReaderId = this.reader.CardReaderId;
            ret.picture = this.reader.Picture;
            ret.message = 'success';
            result = '0';
        }else{
        	ret.message = 'error';
        }
        return ret;
    }
});
