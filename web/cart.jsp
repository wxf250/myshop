<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>购物车</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	function pNum(cid,cnum,price){
		cnum++;
		location.href="cart?method=updateCart&cid="+cid+"&cnum="+cnum+"&price="+price;
	}
	function mNum(cid,cnum,price){
		if (cnum==1) {
			if (confirm("当前商品只剩一件,是否要删除")) {
				location.href="cart?method=clearCart&cid="+cid;
			}
		} else {
			cnum--;
			location.href="cart?method=updateCart&cid="+cid+"&cnum="+cnum+"&price="+price;
		}
	}
	function clearCart(cid){
		if(confirm("确认要删除吗")){
			location.href="cart?method=clearCart&cid="+cid;
		}
	}
	function clearAllCart(uid) {
		if (confirm("是否要清空购物车")) {
			location.href="cart?method=clearAllCart&uid="+uid;
		}
	}
</script>
</head>
<body style="background-color:#f5f5f5">
<%@ include file="header.jsp"%>
<div class="container" style="background-color: white;">
	<div class="row" style="margin-left: 40px">
		<h3>我的购物车<small>温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</small></h3>
	</div>
	<div class="row" style="margin-top: 40px;">
		<div class="col-md-10 col-md-offset-1">
			<table class="table table-bordered table-striped table-hover">
 				<tr>
 					<th>序号</th>
 					<th>商品名称</th>
 					<th>价格</th>
 					<th>数量</th>
 					<th>小计</th>
 					<th>操作</th>
 				</tr>
				<c:if test="${empty carts}">
					<h1>购物车空空如也,快去添加购物车吧</h1>
				</c:if>

					<c:set value="0" var="sum"></c:set>
					<c:forEach items="${carts}" var="c" varStatus="i">
						<tr>
							<th>${i.count}</th>
							<th>${c.product.pname}</th>
							<th>${c.product.pprice}</th>
							<th width="100px">
								<div class="input-group">
		 						<span class="input-group-btn">
		 							<button  class="btn btn-default " type="button" onclick="mNum(${c.cid},${c.cnum},${c.product.pprice})">-</button>
		 						</span>
									<input type="text" class="form-control" id="num_count${i.count}" value="${c.cnum}" readonly="readonly" style="width:40px">
									<span class="input-group-btn">
		 							<button class="btn btn-default" type="button" onclick="pNum(${c.cid},${c.cnum},${c.product.pprice})">+</button>
		 						</span>
								</div>
							</th>
							<th>¥&nbsp;${c.ccount }</th>
							<th>
								<button type="button" class="btn btn-default" onclick="clearCart(${c.cid})">删除</button>
							</th>
						</tr>
						<c:set var="sum" value="${sum+c.ccount}"></c:set>
					</c:forEach>
			</table>
		</div>
	</div>
	<hr>
	<div class="row">
		<div class="pull-right" style="margin-right: 40px;">
			
	            <div>
	            	<a id="removeAllProduct" href="javascript:void(0)" onclick="clearAllCart(${loginUser.uid});" class="btn btn-default btn-lg">清空购物车</a>
	            	&nbsp;&nbsp;
	            	<%--<a href="${pageContext.request.contextPath}/getOrderView" class="btn  btn-danger btn-lg">添加收货地址</a>--%>
					<a href="order?method=show" class="btn  btn-danger btn-lg">添加收货地址</a>
	            	
	            </div>
	            <br><br>
	            <div style="margin-bottom: 20px;">        		  
	            	商品金额总计：<span id="total" class="text-danger"><b>￥&nbsp;&nbsp;${sum}</b></span>
	            </div>
		</div>
	</div>
</div>
	
    
<!-- 底部 -->
<%@ include file="footer.jsp"%>

</body>
</html>