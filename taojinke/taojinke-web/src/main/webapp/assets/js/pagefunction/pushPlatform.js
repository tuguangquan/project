/*******推广平台*********/
/***
 * 
 * 这位偷看代码的壮士，我看你骨骼精奇，是千百年难得一遇的鬼才。
 * 有兴趣联系 888@1234560.Net
 * 
 * ***/
//事件监听
//按钮状态
var buttonStatus = 1;

$(document).ready(function(){	
	//添加微信
	$("#add_wechat").click(function(){
		$("#bindWechatInfo").css("z-index","9992");
		$("#bindWechatInfo").css("display","inherit");
		$("#bindWechatInfo").removeAttr("hidden");
	});
	//返回按钮
	$("#add_return").click(function(){		
		
		$("#bindWechatInfo").addClass("fadeOutRight",function(){
			$(this).fadeOut("normal",function(){
				//沉底
				$("#bindWechatInfo").css("z-index","9990");
				//隐藏模板
				$("#bindWechatInfo").attr("hidden","hidden");
				//移除淡出效果
				$("#bindWechatInfo").removeClass("fadeOutRight");
			});
		});
		
	});
	//提交
	$("#add_wechat_submit").click(function(){
		if($("#wx_name").val() == ""){
			alert("未填写 “名称”！");
			return;
		}
		if($("#wx_account").val() == ""){
			alert("未填写 “账号”！");
			return;
		}
		if($("#wx_token").val() == ""){
			alert("未填写TOKEN “通讯令牌”！");
			return;
		}
		if($("#wx_aeskey").val() == ""){
			alert("未填写 “加密字符串”！");
			return;
		}
		if($("#wx_oid").val() == ""){
			alert("未填写 “原始ID (必填项)”！");
			return;
		}
		if($("#wx_appid").val() == ""){
			alert("未填写 “Appid”！");
			return;
		}
		if($("#wx_appsecret").val() == ""){
			alert("未填写 “appsecret 秘钥”！");
			return;
		}
		//
	    if(buttonStatus == 1){
	    	doSignInAndPost(1);
	    }else{
	    	doSignInAndPost(2);
	    }
		

	});
});

//功能函数
function doPostInfo(){
	
	$.post("http://api.tototo.cc/kmd/rs/weiXin/submit",{
		name:$("#wx_name").val(),
		description:$("#wx_description").val(),
		weiXinId:$("#wx_account").val(),
		weiXinOriginalId:$("#wx_oid").val(),
		appID:$("#wx_appid").val(),
		appSecret:$("#wx_appsecret").val(),
		token:$("#wx_token").val(),
		encodingAESKey:$("#wx_aeskey").val()

	},function(data){
		if(data.code == 200){
			//返回
			$("#add_return").click();
			//创建图标
			createWxIcon($("#wx_appid").val());
		    
		    //绑定事件
	     	hotBindWxIcon($("#wx_appid").val());
		    			
		}else{
			alert(data.message);
		}
		//alert("return str: "+data + status);
		//alert("添加成功！");
		
	},"JSON");
}
function doResetInfo(){
	
	$.post("http://api.tototo.cc/kmd/rs/weiXin/update",{
		name:$("#wx_name").val(),
		description:$("#wx_description").val(),
		weiXinId:$("#wx_account").val(),
		weiXinOriginalId:$("#wx_oid").val(),
		appID:$("#wx_appid").val(),
		appSecret:$("#wx_appsecret").val(),
		token:$("#wx_token").val(),
		encodingAESKey:$("#wx_aeskey").val()

	},function(data){
		if(data.code == 200){
			//返回
			$("#add_return").click();
			//恢复状态
			buttonStatus = 1;
		}else{
			alert(data.message);
		}		
	},"JSON");
}
function doSignInAndPost(options){
	alert(options);
	$.post("http://api.tototo.cc/kmd/rs/user/login",{
		name:"tuguangquan",
		password:"1234",
		safeCode:"1234"

	},function(data,status){
		//alert("return str: "+data + status);
		//alert("登录成功！");
		//doPostInfo(data);
		if(data == null){			
			alert("Current session already invalid.");
			//登录失败
		}else{
			//方法轮询
			switch (options){
				case 1:doPostInfo();
					break;
				case 2:doResetInfo();
					break;
				default:alert("方法未定义！");
					break;
			}
		}
		
	},"JSON");
}
function createWxIcon(wx_appid){
	//内嵌样式
    var html = "<div data-wxappid=\""+wx_appid+"\" id=\"wechat\" style=\"float: left;margin-right: 50px;\">"+
					"<i title=\"删除\" class=\"im-cancel-circle\" style=\"color: orangered;\" id=\"wx_delete\"></i>"+
					"<img src=\"assets/img/wechat.png\"/>"+
					"<h5><i class=\"im-checkmark2\"></i> "+$("#wx_name").val()+"</h5>"+
				"</div>";
	$("#icon_show").prepend(html);
	
}
function hotBindWxIcon(wx_appid){
	//获取所有wechat
	$("div[data-wxappid='"+wx_appid+"']").click(function(){
		//alert("okokok");
		buttonStatus = 2;
		//fill data
		fillOrderByAppId(wx_appid);
	});
}

function fillOrderByAppId(wx_appid){
	
	$.post("http://api.tototo.cc/kmd/rs/weiXin/get",{
		appId:wx_appid
		
	},function(data){
		
		if(data.code == 200){
			//登录失败	
			//塞数据
			$("#wx_name").val(data.data.agentName);
			$("#wx_description").val("nonono");
			$("#wx_account").val(data.data.weixinId);
			$("#wx_oid").val(data.data.weixinOriginalId);
			$("#wx_appid").val(data.data.appID);
			$("#wx_appsecret").val(data.data.appSecret);
			$("#wx_token").val(data.data.token);
			$("#wx_aeskey").val("nonono");
			
			///返回
			$("#add_wechat").click();
		}else{
			//方法轮询
           alert("AppID 请求数据过程中失败，请重试!");
		}
		
	},"JSON");
}
//rs/weiXin/get
//rs/weiXin/addSubMsg  content

//rs/weiXin/addMsgTemp  msgType modeContent msgMatch{Json} priority

