function gridMappingActionProperty(data,actionpropertyname){
	var prop=new Object();
	for(var p in data){
		if(p!='gridTable_id'){
		prop[p]=data[p];
		}
	}
	for(var p1 in prop){
		data[actionpropertyname+'.'+p1]=prop[p1];
		data[p1]=undefined;
	}
	prop=null;
}
var _all_tydic_conditions=new Array();
var _cur_tydic_conditions=null;

$.initcondition=function(name){
	_cur_tydic_conditions=new Array();
	_all_tydic_conditions[name]=_cur_tydic_conditions;
}
$.addcondition= function(field,data,name,op,leftparenthesis,rightparenthesis){
	if(name){
		_cur_tydic_conditions=_all_tydic_conditions[name];
	}else{
		
	}
	if(_cur_tydic_conditions){
		if(_cur_tydic_conditions.objects==undefined){
			_cur_tydic_conditions.objects=new Array();
		}
	
		if(field==undefined){alert('字段名称 不能为空 !!!');return;}
		if(data==undefined){alert('字段值 不能为空 !!!');return;}
		var o=new Object();
		o.field=field;
		o.data=data;
		o.op=op;
		o.leftparenthesis=leftparenthesis;
		o.rightparenthesis=rightparenthesis;
		_cur_tydic_conditions.objects[_cur_tydic_conditions.objects.length]=
			o;
	}

}
$.removecondition= function(field,name){
	if(name){
		_cur_tydic_conditions=_all_tydic_conditions[name];
	}
	if(_cur_tydic_conditions){
		if(_cur_tydic_conditions.objects!=undefined){
			var _i=0;
			for(p in _cur_tydic_conditions.objects){
				if(p.field==field){
					_cur_tydic_conditions.objects.splice(_i, 1);
				}
				_i++;
			}
		}
	}
}
$.clearcondition= function(name){
	if(name){
		_cur_tydic_conditions=_all_tydic_conditions[name];
	}
	if(_cur_tydic_conditions){
		_cur_tydic_conditions.objects=new Array();
	}
}
$.getconditions=function(groupOp,name){
	if(name){
		_cur_tydic_conditions=_all_tydic_conditions[name];
	}
	var cons=new Object();
	if(groupOp){
		cons.groupOp=groupOp
	}else{
		cons.groupOp='AND';
	}
	cons.rules=_cur_tydic_conditions.objects;
	return JSON.stringify(cons);
}