/*******分店列表*********/
/***
 * 
 * 这位偷看代码的壮士，我看你骨骼精奇，是千百年难得一遇的人才。
 * 有兴趣联系 888@1234560.Net
 * 
 * ***/
//事件监听
$(document).ready(function(){
	//加载店铺列表
	getShopList();
	
	//编辑店铺信息
	$("#shoplist_editor").click(function(){
		//搜索
		doEditor();
	});
	//自动加载
	//getRuleList();
	//getMenu();
	//{"button":[{"type":"click","name":"今日歌曲","key":"V1001_TODAY_MUSIC","sub_button":[]},{"type":"click","name":"歌手简介","key":"V1001_TODAY_SINGER","sub_button":[]},{"name":"菜单","sub_button":[{"type":"click","name":"hello word","key":"V1001_HELLO_WORLD","sub_button":[]},{"type":"click","name":"赞一下我们","key":"V1001_GOOD","sub_button":[]}]}]}
	
});

//功能函数
function doEditor(){	
	$.post("http://api.tototo.cc/kmd/rs/shop/audit",{
		openId:"ofngQ06p2jZfiTdd9q7jtGYLACmM",
		pid:"tGYLACmM",
		shopName:"涂光权",
		description:"涂光权的分店"
		
	},function(data){
		//alert("return str: "+data + status);
		if (data.code == 200) {
			alert("设置成功！");
		} else{
			alert("服务器发疯了，请几秒后再试一次！");
		}
		
	},"JSON");
}

//获取商铺列表
function getShopList(){	
	$.post("http://api.tototo.cc/kmd/rs/shop/shopList",null,function(data){
		
		if (data.code == 200) {
			//alert("设置成功！");
			
		} else{
			alert("服务器发疯了，请几秒后再点击一次！");
		}
		
	},"JSON");
}
//删除商铺
function deleteShopById(){	
	$.post("http://api.tototo.cc/kmd/rs/shop/delete",{
		openId:"ofngQ06p2jZfiTdd9q7jtGYLACmM"
		
	},function(data){
		
		if (data.code == 200) {
			//alert("设置成功！");
			
		} else{
			alert("服务器发疯了，请几秒后再点击一次！");
		}
		
	},"JSON");
}
//批量删除商铺
function deleteShopById(){	
	$.post("http://api.tototo.cc/kmd/rs/shop/batchDelete",{
		openId:"ofngQ06p2jZfiTdd9q7jtGYLACmM,A,B,C,D,E,F"
		
	},function(data){
		
		if (data.code == 200) {
			//alert("设置成功！");
			
		} else{
			alert("服务器发疯了，请几秒后再点击一次！");
		}
		
	},"JSON");
}