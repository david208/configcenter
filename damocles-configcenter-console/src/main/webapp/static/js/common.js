/**
 * 日期格式化
 * 		li
 * @param fmt
 * @returns
 */
Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function searchList(searchFormId,datagrId){
	var param = {};
	$('#'+searchFormId).find('.query').each(function(){
		var name = $(this).attr('name');
		var val = $(this).val();
		if($(this).attr('type')=='radio'){
			 val = $('input[name="'+name+'"]:checked').val();
		}
		
		if ($(this).hasClass('datebox-f')){
			name = $(this).attr('comboname');
			val = $(this).datebox('getValue');//alert(name+'00'+val);
		} else if ($(this).hasClass('combogrid-f')){
			name = $(this).attr('comboname');
			val = $(this).combogrid('getValue');
		} else if ($(this).hasClass('combobox-f')){
			name = $(this).attr('comboname');
			val = $(this).combobox('getValues');
		} 
		param[name] = val;
	});
	
	$('#'+datagrId).datagrid('load', param);
}

function searchParms(searchFormId){
	var param = {};
	$('#'+searchFormId).find('.query').each(function(){
		var name = $(this).attr('name');
		var val = $(this).val();
		if($(this).attr('type')=='radio'){
			 val = $('input[name="'+name+'"]:checked').val();
		}
		
		if ($(this).hasClass('datebox-f')){
			name = $(this).attr('comboname');
			val = $(this).datebox('getValue');//alert(name+'00'+val);
		} else if ($(this).hasClass('combogrid-f')){
			name = $(this).attr('comboname');
			val = $(this).combogrid('getValue');
		} else if ($(this).hasClass('combobox-f')){
			name = $(this).attr('comboname');
			val = $(this).combobox('getValues');
		} 
		param[name] = val;
	});
	return param;
}

function onClear(url){
	window.location.href = url;	
}

/**
 * 弹窗
 * @param id
 * @param areaId
 * @param url
 * @param title
 * @param width
 * @param height
 * @param onCloseHandler
 */
function showCommonDialog(id,areaId,url,title,width,height,onCloseHandler){
	var dlg = $('#'+id);
	if (!dlg.length){  
		dlg = $('<div id="'+id+'"></div>').appendTo('body');
		dlg.dialog({
	        title: title,
	        iconCls:'icon-tab',
	        loadingMessage:'数据装载中......',
	        modal: true,
	        resizable: false,
	        minimizable: false,
	        maximizable: true,
	        shadow: true,
	        closed: true,
	        collapsible:true,
	        width: width,
	        height: height,
	        onMaximize:function(){
	    		dlg.dialog("move",{top:$(document).scrollTop()}); 
	        },
	        //buttons:crud_winButtons,
	        //toolbar:crud_winToolbar
		    onMove:function(left,top){},
			onClose: function () {
				$(this).dialog("destroy");
				if(onCloseHandler){
					onCloseHandler();
				}
			},
		    buttons: [{  
                text:'关闭',  
                handler:function(){  
                	dlg.dialog('close');  
                }  
            }] 
	    });
	}
	dlg.dialog('open').dialog('refresh',url );
	dlg.dialog("move",{top:$(document).scrollTop() + (document.body.clientHeight-height) * 0.5}); 
}


var comboboxUtil = (function() {
	var util = util || {};

	// 根据url填充下拉框
	util.setComboboxByUrl = function(id, url, valueField, textField, width, required, onSelect) {
		$('#' + id).combobox({
			url : url,
			valueField : valueField,
			textField : textField,
			//panelHeight : 'auto',
			width : width,
			required : required,
			validType : 'selectValidator["' + id + '"]',
			filter : function(q, row) {
				return row[$(this).combobox('options').textField].indexOf(q) == 0;
			},
			onSelect : onSelect
		});

		$('#' + id).combobox('textbox').focus(function(){
			$('#' + id).combobox('showPanel');
			});
	},

	// 根据数据源填充下拉框
	util.setComboboxByData = function(id, data, valueField, textField, width, required, onSelect) {
		$('#' + id).combobox({
			data : data,
			valueField : valueField,
			textField : textField,
//			panelHeight : 'auto',
			width : width,
			required : required,
			validType : 'selectValidator["' + id + '"]',
			filter : function(q, row) {
				return row[$(this).combobox('options').textField].indexOf(q) == 0;
			},
			onSelect : onSelect
		});
		$('#' + id).combobox('textbox').focus(function(){
			$('#' + id).combobox('showPanel');
			});
	},
	
	// 给下拉框添加必填属性
	util.addRequiredAttr = function(id) {
		$('#' + id).combobox({
			'required' : true
		});
	},

	// 移除下拉框必填属性
	util.removeRequiredAttr = function(id) {
		$('#' + id).combobox({
			'required' : false
		});
	},

	// 移除下拉框必填属性并清空当前的value
	util.removeRequiredAttrAndClearVal = function(id) {
		this.removeRequiredAttr(id);
		this.clearVal(id);
	},

	// 清空下拉框当前的value
	util.clearVal = function(id) {
		$('#' + id).combobox('clear');
	},

	// 获得下拉框的值
	util.getValue = function(id) {
		return $("#" + id).combobox('getValue');
	},

	// 给下拉框赋值
	util.setValue = function(id, val) {
		$("#" + id).combobox('setValue', val);
	},

	// 获得下拉框Text
	util.getText = function(id) {
		return $("#" + id).combobox('getText');
	},

	util.formatter = function(id, formatter) {
		$('#' + id).combobox({
			formatter : formatter
		});
	},

	util.addOnSelectEvent = function(id, onSelect) {
		$('#' + id).combobox({
			onSelect : onSelect
		});
	},

	util.addOnChangeEvent = function(id, onChange) {
		$('#' + id).combobox({
			onChange : onChange
		});
	}

	return util;
})()