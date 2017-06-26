$.extend(thisApp, {
    'reader' : $('#reader_obj').get(0),
    'err_msg' : {
        '0' : '操作成功',
        '-1' : '未链接设备',
        '-2' : '放卡超时',
        '-3' : '用户取消',
        '-4' : '读取基本信息失败',
        '-5' : '照片创建失败'
    },
    'clear' : function() {
        this.reader.Name = "";
        this.reader.NameL = "";
        this.reader.Sex = "";
        // this.reader.SexL="";
        this.reader.Nation = "";
        // this.reader.NationL="";
        this.reader.Born = "";
        // this.reader.BornL="";
        this.reader.Address = "";
        this.reader.CardNo = "";
        this.reader.Police = "";
        this.reader.Activity = "";
        this.reader.NewAddr = "";
        return true;
    },
    'readCard' : function() {
        this.reader.PhotoPath = "c:\\";
        this.reader.TimeOut = 3;
        this.clear();
        var result = this.reader.ReadCard;
        var ret = {};
        ret = $.extend(ret, this._defualt_card_data);
        ret.success = result === '0';
        ret.message = this.err_msg[result];
        if (ret.success) {
            ret.name = this.reader.NameL;
            ret.sex = this.reader.Sex;
            ret.sex_name = this.reader.SexL;
            ret.nation = this.reader.NationL;
            ret.birthday = this.reader.Born;
            ret.address = this.reader.Address;
            ret.start_period = this.reader.Activity.substr(0,8);
            ret.id = this.reader.CardNo;
            ret.auth_dept = this.reader.Police;
            ret.valid_period = this.reader.Activity.substr(8);
            ret.last_address = this.reader.NewAddr;
            // add by xiebb 2011-10-25 身份验证的时候增加图片存储功能 start
            ret.photoString = this.reader.GetPhotoBuffer;
            // add by xiebb 2011-10-25 身份验证的时候增加图片存储功能 over
        }
        return ret;
    }
});
