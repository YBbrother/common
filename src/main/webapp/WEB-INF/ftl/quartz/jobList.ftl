<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Job List</title>
		<style type="text/css">
			<!--
			.STYLE1 {
			    font-family: Arial, Helvetica, sans-serif;
			    font-weight: bold;
			    font-size: 36px;
			    color: #FF0000;
			}
			.STYLE13 {font-size: 24}
			.STYLE15 {font-family: Arial, Helvetica, sans-serif; font-size: 24px; }
			-->
		</style>
	
		<!-- <link type="text/css" href="styles/layui.css" rel="stylesheet">
		<script type="text/javascript" src="scripts/lay/layui.js"></script> -->
		
		<script type="text/javascript" src="/common/scripts/jquery/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="/common/scripts/layer/layer.js"></script>
	</head>
 
<body>
	<div>
		<button onclick="addTask()">增加任务</button>
		<button onclick="start()">启动</button>
	</div>
	
	<table width="100%" height="100" border="1" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">任务ID</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">创建时间</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">更新时间</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">任务名称</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">任务分组</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">任务状态</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">cron表达式</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">描述</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">执行Bean</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">任务是否有状态</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">SpringId</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">任务调用方法名</span></td>
	        <td width="160" height="10" align="center" valign="middle"><span class="STYLE15">操作</span></td>
	      </tr>
	      <#list taskList as task>
	      	<tr>
	      		<td align="center">${task.jobId}</td>
	      		<td align="center">${task.createTime}</td>
	      		<td align="center">${task.updateTime}</td>
	      		<td align="center">${task.jobName}</td>
	      		<td align="center">${task.jobGroup}</td>
	      		<td align="center">${task.jobStatus}</td>
	      		<td align="center">${task.cronExpression}</td>
	      		<td align="center">${task.description}</td>
	      		<td align="center">${task.beanClass}</td>
	      		<td align="center">${task.isConcurrent}</td>
	      		<td align="center">${task.springId}</td>
	      		<td align="center">${task.methodName}</td>
	      		<td align="center"><span style="background-color: #00FFFF" onclick="edit(this)">修改&nbsp;</span>|<span style="background-color: #98FB98" onclick="remove(this)">&nbsp;删除</span></td>
	      	</tr>
	      </#list>
	    </table>
	</body>
	
	<script type="text/javascript">
			
		var prefix = "/common/quartz";
		
		function addTask() {
			// layer.msg('hello');
			layer.open({
			  type: 2,
			  title: '增加任务',
			  maxmin: true,
			  area: ['380px', '70%'],
			  content : prefix + '/addTask.do'  // url
			}); 
		}
		
		function edit(tid) {
			var id = $(tid).parent().parent().find("td:eq(0)").text();
			layer.open({
			  type: 2,
			  title: '修改任务',
			  maxmin: true,
			  area: ['380px', '70%'],
			  content : prefix + '/edit.do?id=' + id  // url
			});
		}
		
		function remove(tid) {
			var jobId = $(tid).parent().parent().find("td:eq(0)").text();
			layer.confirm('确定要删除选中的记录？', {
				btn : [ '确定', '取消' ]
			}, function() {
				$.ajax({
					url : prefix + '/remove.do',
					type : "post",
					data : {
						'jobId' : jobId
					},
					dataType: "json",
					success:function(data) {
						if (data.code == "success") {
							layer.msg(data.message, {icon:1,time:500}, function(){ window.parent.location.reload(); });
							// layer.msg('提示信息', 图标类型, 自动关闭时间, msg关闭后执行的回调)
						} else {
							layer.msg(data.message, {icon:2,time:500}, function(){ window.parent.location.reload(); });
						}
					}
				});
			})
		}
		
		function start() {
			var jobId = '1001';
			layer.confirm('确定启动吗？', {
				btn : [ '确定', '取消' ]
			}, function() {
				$.ajax({
					url : prefix + '/startJob.do',
					type : "post",
					data : {
						'jobId' : jobId
					},
					dataType: "json",
					success:function(data) {
						if (data.code == "success") {
							layer.msg(data.message, {icon:1,time:500});
							// layer.msg('提示信息', 图标类型, 自动关闭时间, msg关闭后执行的回调)
						} else {
							layer.msg(data.message, {icon:2,time:500});
						}
					}
				});
			})
		}
		
	</script>

</html>