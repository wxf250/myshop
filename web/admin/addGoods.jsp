<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/DatePicker.js"></script>
<title>商品添加页面</title>
</head>
<body>
	<div class="row" style="margin-left: 20px;">
		<form action="${pageContext.request.contextPath }/admin/upload" method="post" enctype="multipart/form-data">
			<div>
				<h3>新增商品</h3>
			</div>
			<hr />
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group form-inline">
						<label>名称:</label>
						<input type="text" name="pname" class="form-control" />
					</div>
					
					<div class="form-group form-inline">
						<label>分类:</label>
						<select name="tid" class="form-control">
							<option value="3">------</option>
							<option value="4">电脑</option>
							<option value="5">笔记本</option>
							<option value="6">平板</option>
							<option value="7">小米手机</option>
							<option value="8">红米</option>
						</select>
					</div>
					<div class="form-group form-inline">
						<label>时间:</label>
						<input type="text" name="time" readonly="readonly" class="form-control" onclick="setday(this)" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group form-inline">
						<label>价格:</label>
						<input type="text" name="pprice" class="form-control" />
					</div>
					<div class="form-group form-inline">
						<label>评分:</label>
						<input type="text" name="pstate" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-10">
					<div class="form-group form-inline">
						<label>商品图片</label>
						<input type="file" name="pimage" />
					</div>
					<div class="form-group ">
						<label>商品简介</label>
						<textarea  name="pinfo" class="form-control" rows="5"></textarea>
					</div>
					<div class="form-group form-inline">
						<input type="submit" value="添加" class="btn btn-primary" />
						<input type="reset" value="重置" class="btn btn-default" />
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>