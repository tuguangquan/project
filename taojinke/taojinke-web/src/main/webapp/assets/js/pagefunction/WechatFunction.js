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
	$("#fa-save").click(function(){
		//搜索
		doSave();
	});
	//自动加载
	//getRuleList();
	//getMenu();
	//{"button":[{"type":"click","name":"今日歌曲","key":"V1001_TODAY_MUSIC","sub_button":[]},{"type":"click","name":"歌手简介","key":"V1001_TODAY_SINGER","sub_button":[]},{"name":"菜单","sub_button":[{"type":"click","name":"hello word","key":"V1001_HELLO_WORLD","sub_button":[]},{"type":"click","name":"赞一下我们","key":"V1001_GOOD","sub_button":[]}]}]}
	
});

//功能函数
function doSave(){	
	$.post("http://api.tototo.cc/kmd/rs/weiXin/addSubMsg",{
		content:$("#fa_content").val()
		
	},function(data){
		//alert("return str: "+data + status);
		if (data.code == 200) {
			alert("设置成功！");
		} else{
			alert("服务器发疯了，请几秒后再试一次！");
		}
		
	},"JSON");
}

//获取自动回复规则列表
function getRuleList(){	
	$.post("http://api.tototo.cc/kmd/rs/weiXin/msgTempList",null,function(data){
		
		if (data.code == 200) {
			//alert("设置成功！");
			
		} else{
			alert("服务器发疯了，请几秒后再点击一次！");
		}
		
	},"JSON");
}

function getMenu(){	
	
	$.post("http://api.tototo.cc/kmd/rs/weiXin/getMenu",null,function(data){
		
		if (data.code == 200) {
			//alert("设置成功！");
			
		} else{
			alert("服务器发疯了，请几秒后再点击一次！");
		}
		
	},"JSON");
}
function getCurrentMenuLie(){
	return $("#bottomer").children("#menu1").size();
}
//插入列
function insertLie(){
	//查看当前列是否大于3
	if(getCurrentMenuLie() >= 3){
		alert("增加菜单个数已经达到上限！");
		return;
	}
	/////////////////////////////////
	
}
//生成海报

//rs/weiXin/msgTempList
//rs/weiXin/getMenu