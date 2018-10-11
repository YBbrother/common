<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="/common/scripts/jquery/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="/common/scripts/layer/layer.js"></script>
	</head>
	<body>
		<form action="" id="editForm" >
			<table style="width: 100%; height: 100%"  border="0">
			      <tr>
			        <td>任务Id</td>
			        <td><input type="text" id="jobId" name="jobId" value="${scheduleJob.jobId}" style="background-color: #efefef" readonly /></td>
			      </tr>
			      <tr>
			        <td>任务名称</td>
			        <td><input type="text" id="jobName" name="jobName" value="${scheduleJob.jobName}" /></td>
			      </tr>
			      <tr>
			        <td>任务分组</td>
			        <td><input type="text" id="jobGroup" name="jobGroup" value="${scheduleJob.jobGroup}" /></td>
			      </tr>
			      <tr>
			        <td>任务状态</td>
			        <td><input type="text" id="jobStatus" name="jobStatus" value="${scheduleJob.jobStatus}" /></td>
			      </tr>
			      <tr>
			        <td>cron表达式</td>
			        <td><input type="text" id="cronExpression" name="cronExpression" value="${scheduleJob.cronExpression}" /></td>
			      </tr>
			      <tr>
			        <td>描述</td>
			        <td><input type="text" id="description" name="description" value="${scheduleJob.description}" /></td>
			      </tr>
			      <tr>
			        <td>调用方法路径</td>
			        <td><input type="text" id="beanClass" name="beanClass" value="${scheduleJob.beanClass}" /></td>
			      </tr>
			      <tr>
			        <td>任务是否有状态</td>
			        <td><input type="text" id="isConcurrent" name="isConcurrent" value="${scheduleJob.isConcurrent}" /></td>
			      </tr>
			      <tr>
			        <td>spring的Id</td>
			        <td><input type="text" id="springId" name="springId" value="${scheduleJob.springId}" /></td>
			      </tr>
			      <tr>
			        <td>调用方法名</td>
			        <td><input type="text" id="methodName" name="methodName" value="${scheduleJob.methodName}" /></td>
			      </tr>
			      <tr>
			        <td></td>
			        <td>
			        	<input type="button" onclick="doRevise()" value="确认"/>
			        	<input type="button" onclick="doClose()" value="关闭"/>
			        </td>
			      </tr>
		    </table>
	    </form>
	</body>
	
	<script type="text/javascript">
		
		function doRevise() {
			$.ajax({
				cache : true,
				type : "POST",
				url : "/common/quartz/revise.do",
				data : $('#editForm').serialize(),
				dataType: "json",
				async : false,
				error : function(request) {
					laryer.alert("Connection error");
				},
				success:function(data) {
					if (data.code == "success") {
						layer.msg(data.message, {icon:1,time:500}, function(){doClose();});
						// layer.msg('提示信息', 图标类型, 自动关闭时间, msg关闭后执行的回调)
					} else {
						layer.msg(data.message, {icon:2,time:500}, function(){doClose();});
					}
				}
			});
		}
		
		function doClose() {
			window.parent.location.reload(); //刷新父页面
			var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
			parent.layer.close(index);
		}
		
	</script>
	
</html>