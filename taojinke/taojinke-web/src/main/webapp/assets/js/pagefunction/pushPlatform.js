/*******推广平台*********/
/***
 * 
 * 这位偷看代码的壮士，我看你骨骼精奇，是千百年难得一遇的鬼才。
 * 有兴趣联系 888@1234560.Net
 * 
 * ***/
//事件监听
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
		//createWxIcon("aa");
		doSignInAndPost(1);

	});
});

//功能函数
function doPostInfo(){
	
	$.post("rs/weiXin/add",{
		name:$("#wx_name").val(),
		description:$("#wx_description").val(),
		weiXinId:$("#wx_account").val(),
		weiXinOriginalId:$("#wx_oid").val(),
		appID:$("#wx_appid").val(),
		appSecret:$("#wx_appsecret").val(),
		token:$("#wx_token").val(),
		encodingAESKey:$("#wx_aeskey").val()

	},function(data,status){
		//alert("return str: "+data + status);
		//alert("添加成功！");
		
	},"JSON");
}
function doSignInAndPost(options){
	
	$.post("rs/user/login",{
		name:"tuguangquan",
		password:"123456"

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
				default:alert("方法未定义！");
					break;
			}
		}
		
	},"JSON");
}
function createWxIcon(wx_name){
	//内嵌样式
    var html = "<div id=\"wechat\" style=\"float: left;margin-right: 50px;\">"+
					"<i title=\"删除\" class=\"im-cancel-circle\" style=\"color: orangered;\" id=\"wx_delete\"></i>"+
					"<img src=\"assets/img/wechat.png\"/>"+
					"<h5><i class=\"im-checkmark2\"></i> 全网购物省钱优惠券A</h5>"+
				"</div>";
	$("#icon_show").prepend(html);
}
