/**************商品类别管理*************/
/***
 * 
 * 这位偷看代码的壮士，我看你骨骼精奇，是千百年难得一遇的人才。
 * 有兴趣联系 888@1234560.Net
 * 
 * ***/
//事件监听
$(document).ready(function(){
	//添加微信
	$("#btn-edit").click(function(){
		$("#goodscategory_editor_title").html("编辑分类");
		$("#goodscategory_modify_save").html("保存");
		
		$("#goodscategory_view").css("z-index","9990");
		$("#goodscategory_mode").css("display","inherit");
		$("#goodscategory_modify").css("z-index","9992");
		$("#goodscategory_modify").css("position","absolute");
		$("#goodscategory_modify").show();
		
		
	});
	
	$("#goodscategory_modify_return").click(function(){

		$("#goodscategory_modify").attr("class","animated  fadeOutRightBig");
		
		$("#goodscategory_modify").hide("2000",function(){
		    
		    //遮罩消失
			//$("#goodscategory_mode").css("display","none");
			$("#goodscategory_mode").fadeOut();
			$("#goodscategory_modify").attr("class","animated fadeInRightBig");
		});
		
	});
	

	//新建类别
	$("#goodscategory_new").click(function(){
		$("#goodscategory_editor_title").html("新建分类");
		$("#goodscategory_modify_save").html("添加");
		
		$("#goodscategory_view").css("z-index","9990");
		//$("#goodscategory_mode").css("display","inherit");
		$("#goodscategory_mode").fadeIn();
		$("#goodscategory_modify").css("z-index","9992");
		$("#goodscategory_modify").css("position","absolute");
		$("#goodscategory_modify").show();
	});
	
	
});

//功能函数
function doSearch(){	
	$.post("http://api.tototo.cc/kmd/rs/goods/findByCondition",{
		goodsName:$("#goodsname").val(),
		lowerPrice:$("#goodspricemini").val(),
		topPrice:$("#goodspricemax").val(),
		lowerRate:$("#goodsearnmini").val(),
		topRate:$("#goodsearnmax").val(),
		cateId:$("#goodstypeid").val()
//		cateStore:$("#goodswhereid").val()

	},function(data,status){
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
function createWxIcon(wx_name){
	//内嵌样式
    var html = "<div id=\"wechat\" style=\"float: left;margin-right: 50px;\">"+
					"<i title=\"删除\" class=\"im-cancel-circle\" style=\"color: orangered;\" id=\"wx_delete\"></i>"+
					"<img src=\"assets/img/wechat.png\"/>"+
					"<h5><i class=\"im-checkmark2\"></i> 全网购物省钱优惠券A</h5>"+
				"</div>";
	$("#icon_show").prepend(html);
}
