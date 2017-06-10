/*******登录页面*********/
/***
 * 
 * 这位偷看代码的壮士，我看你骨骼精奇，是千百年难得一遇的人才。
 * 有兴趣联系 888@1234560.Net
 * 
 * ***/
//事件监听
$(document).ready(function(){
	//添加微信
	$("#dologin").click(function(){
		//搜索
		doLogin();
	});
});

//功能函数
function doLogin(){	
	$.post("http://123.207.77.16/kmd/rs/user/login",{
		name:$("#emailaccount").val(),
		password:$("#password1").val(),
		safeCode:$("#password2").val()

	},function(data){
		//alert("return str: "+data + status);
		if (data.code == 200) {
			//alert("添加成功！");
			//登录成功跳转
			$(location).attr('href', 'index.html');
		} else{
			//alert("用户名密码组合认证失败！");
			$(".login-wrapper").posterSwing({swingCenter: "50% 100%"});
			$("#logintips").css("display","inherit");
		}
		//alert("添加成功！");
		
	},"JSON");
}
