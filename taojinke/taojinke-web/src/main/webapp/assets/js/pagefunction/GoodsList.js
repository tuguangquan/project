/*******商品/全部商品列表*********/
/***
 * 
 * 这位偷看代码的壮士，我看你骨骼精奇，是千百年难得一遇的人才。
 * 有兴趣联系 888@1234560.Net
 * 
 * ***/
//事件监听
$(document).ready(function(){
	//添加微信
	$("#goodssearch").click(function(){
		//搜索
		doSearch();
	});
	
});

//功能函数
function doSearch(){	
	createGoodsItem("s");
	$.post("http://api.tototo.cc/kmd/rs/goods/findByCondition",{
		goodsName:$("#goodsname").val(),
		lowerPrice:$("#goodspricemini").val(),
		topPrice:$("#goodspricemax").val(),
		lowerRate:$("#goodsearnmini").val(),
		topRate:$("#goodsearnmax").val(),
		cateId:$("#goodstypeid").val(),
		pageSize:$("#goodsmaxitems").val(),
		pageIndex:1

	},function(data){
		//alert("return str: "+data + status);
		//alert("添加成功！");
		
	},"JSON");
}
function doSignInAndPost(options){
	
	$.post("http://api.tototo.cc/kmd/rs/user/login",{
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
function createGoodsItem(data){
	//内嵌样式
    var html = "<tr class=\"animated fadeIn\">"+
                    "<td>"+
                        "<label class=\"checkbox\">"+
                            "<input class=\"check\" type=\"checkbox\" value=\"option2\" style=\"margin-left: 0;\">"+
                        "</label>"+
                    "</td>"+
                    "<td>1</td>"+
                    "<td><img src=\"assets/css/sprflat-theme/images/animated-overlay.gif\" height=\"50px\"/> </td>"+
                    "<td>57694B668422</td>"+
                    "<td>￥ 56.0</td>"+
                    "<td>￥ 23.6 (21%)</td>"+
                    "<td>女装</td>"+
                    "<td>淘宝</td>"+                                
                    "<td>"+
                        "<div class=\"progress\">"+
                            "<div class=\"progress-bar progress-bar-danger animated-bar\" role=\"progressbar\" aria-valuenow=\"90\" style=\"width: 90%;\" title=\"1560次点击\">"+
                            "</div>"+
                        "</div>"+
                    "</td>"+
                    "<td>"+
                        "<div class=\"progress\">"+
                            "<div class=\"progress-bar progress-bar-success animated-bar\" role=\"progressbar\" aria-valuenow=\"70\" style=\"width: 70%;\" title=\"70个订单\">"+
                            "</div>"+
                        "</div>"+
                    "</td>"+
                    "<td>上架</td>"+
                    "<td><button class=\"btn btn-warning\">下架</button>&nbsp; &nbsp; <button class=\"btn btn-danger\">删除</button> </td> "+
                "</tr>";
				
				
				
	$("#GoodsListItem").prepend(html);
}
