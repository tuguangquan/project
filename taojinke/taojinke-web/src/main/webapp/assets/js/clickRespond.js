/******首页点击事件响应***********/
/***
 * 
 * 这位偷看代码的壮士，我看你骨骼精奇，是千百年难得一遇的鬼才。
 * 有兴趣联系 888@1234560.Net
 * 
 * ***/
//事件监听
$(document).ready(function(){
	//alert("hey");
	//推广平台菜单监听组
	$("#bindQQ").click(function(){
		loadBindQQPage();
	});
	$("#bindWechat").click(function(){
		loadBindWechatPage();
	});
	$("#bindWeibo").click(function(){
		loadBindWeiboPage();
	});
	$("#bindMomo").click(function(){
		loadBindMomoPage();
	});
	//微信功能菜单监听组
	$("#fattention").click(function(){
		//alert("首次关注")
		loadFattention();
	});
	//图文自动回复
	$("#autoreply").click(function(){
		//alert("首次关注")
		loadAutoreply();
	});
	//自定义菜单
	$("#setmenu").click(function(){
		//alert("自定义菜单")
		loadMenuSettings();
	});
	//推广海报
	$("#pushreport").click(function(){
		//alert("推广海报")
		loadPushReport();
	});
	////////////////////////////////
	//商城设置
	$("#mallsettings").click(function(){
		
		loadMallSettings();
	});
	//模板样式
	$("#mallstyles").click(function(){
		
		loadMallStyleSettings();
	});
	//组件风格
	$("#mallstylewidget").click(function(){
		
		loadMallStyleWidgetSettings();
	});
	//个性域名
	$("#malldomain").click(function(){
		
		loadMallDomain();
	});
	
	/////////////////////////////////
	//商品基本设置
	$("#goodssettings").click(function(){
		
		loadGoodsSettings();
	});
	//商品类别管理
	$("#goodscategory").click(function(){
		
		loadGoodsCategory();
	});
	//商品列表
	$("#goodslist").click(function(){
		
		loadGoodsList();
	});
	//业务人员
	//所有业务员
	$("#allemployee").click(function(){
		
		loadAllEmployee();
	});
	//推广业绩报表
	$("#employeepushreport").click(function(){
		
		loadEmployeePushReport();
	});
	//分店管理
	//分店基本设置
	$("#shopsettings").click(function(){
		
		loadShopSettings();
	});
	//分店列表
	$("#shoplist").click(function(){
		
		loadShopList();
	});
	//佣金结算
	$("#shopcommission").click(function(){
		
		loadShopCommission();
	});
	
	
});
/******功能方法*******/
//加载绑定QQ页面
function loadBindQQPage(){
	alert("QQ模块正在玩命开发中...");
	
}
//加载绑定微信页面
function loadBindWechatPage(){
	$(".content-wrapper").load("fragment_bind_wechat.html");
}
//加载绑定微博页面
function loadBindWeiboPage(){
	alert("微博模块正在玩命开发中...");
}
//加载绑定陌陌页面
function loadBindMomoPage(){
	alert("正在玩命开发中...");
}
/******“微信功能”菜单组******/
//首次关注自动回复
function loadFattention(){
	$(".content-wrapper").load("fragment_fattention.html");
}
//自动回复
function loadAutoreply(){
	$(".content-wrapper").load("fragment_auto_reply.html");
}
//自定义菜单
function loadMenuSettings(){
	$(".content-wrapper").load("fragment_menu_settings.html");
}
//推广海报
function loadPushReport(){
	$(".content-wrapper").load("fragment_push_report.html");
}
//-----------------------------
//商城基本信息设置
function loadMallSettings(){
	$(".content-wrapper").load("fragment_mall_settings.html");
}
//风格设置
function loadMallStyleSettings(){
	$(".content-wrapper").load("fragment_mall_style.html");
}
//模板组件设置
function loadMallStyleWidgetSettings(){
	$(".content-wrapper").load("fragment_mall_style_widget.html");
}
//个性域名
function loadMallDomain(){
	$(".content-wrapper").load("fragment_bind_domain.html");
}
//-------------------------------
//商品管理
//基础设置
function loadGoodsSettings(){
	//$.ajax({cache: false});
	$(".content-wrapper").load("fragment_goods_settings.html");
}
//类别管理
function loadGoodsCategory(){
	$(".content-wrapper").load("fragment_goods_category.html");
}
//商品列表
function loadGoodsList(){
	$(".content-wrapper").load("fragment_goods_list.html");
}
//--------------------------
//业务员管理
//类别管理
function loadAllEmployee(){
	$(".content-wrapper").load("fragment_employee_list.html");
}
//商品列表
function loadEmployeePushReport(){
	$(".content-wrapper").load("fragment_employee_achieve.html");
}
//----------------------------
//分店管理
function loadShopSettings(){
	$(".content-wrapper").load("fragment_shop_settings.html");
}
function loadShopList(){
	$(".content-wrapper").load("fragment_shop_list.html");
}
function loadShopCommission(){
	$(".content-wrapper").load("fragment_shop_commission.html");
}