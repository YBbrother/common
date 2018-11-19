<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="webkit" name="renderer" />
<title>DayDayUp</title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
<link href="favicon.ico" rel="shortcut icon" />
<link
	href="${rc.contextPath}/styles/plugins/bootstrap/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${rc.contextPath}/styles/plugins/font-awesome/font-awesome.css"
	rel="stylesheet" />
<link
	href="${rc.contextPath}/styles/plugins/metisMenu/metisMenu.min.css"
	rel="stylesheet" />
<link href="${rc.contextPath}/styles/style.css" rel="stylesheet" />
</head>
<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<li class="nav-header">
						<div>
							<span><img alt="image" class="img-circle" height="60"
								style="margin-left: 35px;" width="60" src="/img/photo_s.jpg" /></span>
							<h3 class="" style="color: #ffffff">
								欢迎登录, <span>admin</span>
							</h3>
						</div>
					</li>
					
				</ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div class="gray-bg dashbard-1" id="page-wrapper">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">

						<a class="navbar-minimalize minimalize-styl-2 btn btn-default "
							href="#" title="收起菜单"><i class="fa fa-bars"></i> </a>
					</div>
					<ul class="nav navbar-top-links navbar-right">
					</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<a class="active J_menuTab" data-id="index_v1.html"
							href="javascript:;">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>
					</button>
					<ul class="dropdown-menu dropdown-menu-right" role="menu">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a class="roll-nav roll-right J_tabExit" href="/logout"><i
					class="fa fa fa-sign-out"></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" data-id="index_v1.html" style="border: 0px;"
					height="100%" name="iframe0" seamless="seamless"
					src="" width="100%"></iframe>
			</div>
			<div class="footer"></div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">
				<ul class="nav nav-tabs navs-3">

					<li class="active"><a data-toggle="tab" href="#tab-1"> <i
							class="fa fa-gear"></i> 主题
					</a></li>
					<li class=""><a data-toggle="tab" href="#tab-2"> 通知 </a></li>
					<li><a data-toggle="tab" href="#tab-3"> 项目进度 </a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab-1">
						<div class="sidebar-title">
							<h3>
								<i class="fa fa-comments-o"></i> 主题设置
							</h3>
							<small><i class="fa fa-tim"></i>
								你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。 </small>
						</div>
						<div class="skin-setttings">
							<div class="title">主题设置</div>
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input class="onoffswitch-checkbox" id="collapsemenu"
											name="collapsemenu" type="checkbox" /> <label
											class="onoffswitch-label" for="collapsemenu"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span>固定顶部</span>

								<div class="switch">
									<div class="onoffswitch">
										<input class="onoffswitch-checkbox" id="fixednavbar"
											name="fixednavbar" type="checkbox" /> <label
											class="onoffswitch-label" for="fixednavbar"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="setings-item">
								<span> 固定宽度 </span>
								<div class="switch">
									<div class="onoffswitch">
										<input class="onoffswitch-checkbox" id="boxedlayout"
											name="boxedlayout" type="checkbox" /> <label
											class="onoffswitch-label" for="boxedlayout"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a class="s-skin-0" href="#">
										默认皮肤 </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a class="s-skin-1" href="#">
										蓝色主题 </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a class="s-skin-3" href="#">
										黄色/紫色主题 </a>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 全局js -->
	<script
		src="${rc.contextPath}/scripts/plugins/jquery/jquery-3.3.1.min.js"></script>
	<script
		src="${rc.contextPath}/scripts/plugins/popper/umd/popper.min.js"></script>
	<!-- 在引入bootstrap之前必须引入jquery和popper，否则有些方法会报错 -->
	<script
		src="${rc.contextPath}/scripts/plugins/bootstrap/bootstrap.min.js"></script>
	<script
		src="${rc.contextPath}/scripts/plugins/metisMenu/metisMenu.min.js"></script>
	
	<script src="${rc.contextPath}/scripts/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- 自定义js -->
	<!-- 控制左边菜单的动作 -->
	<script src="${rc.contextPath}/scripts/appjs/app.js"></script>
	<!-- 控制右边的iframe窗口 -->
	<script src="${rc.contextPath}/scripts/appjs/contabs.js"></script>
	
	<script src="${rc.contextPath}/scripts/plugins/vue/vue.min.js"></script>
	<script src="${rc.contextPath}/scripts/plugins/webSocket/sockjs.min.js"></script>
	<script src="${rc.contextPath}/scripts/plugins/webSocket/stomp.min.js"></script>


	<script type="text/javascript">
/* 		var stompClient = null;
		$(function() {
			connect();
		});

		function connect() {
			var sock = new SockJS("/endpointChat");
			var stomp = Stomp.over(sock);
			stomp.connect('guest', 'guest', function(frame) {*/

				/**  订阅了/user/queue/notifications 发送的消息,这里雨在控制器的 convertAndSendToUser 定义的地址保持一致, 
				 *  这里多用了一个/user,并且这个user 是必须的,使用user 才会发送消息到指定的用户。 
				 *  */
			/* 	stomp
						.subscribe("/user/queue/notifications",
								handleNotification);
				stomp.subscribe('/topic/getResponse', function(response) { //订阅/topic/getResponse 目标发送的消息。这个是在控制器的@SendTo中定义的。
					toastr.options = {
						"closeButton" : true,
						"debug" : false,
						"progressBar" : true,
						"positionClass" : "toast-bottom-right",
						"onclick" : null,
						"showDuration" : "400",
						"hideDuration" : "1000",
						"timeOut" : "7000",
						"extendedTimeOut" : "1000",
						"showEasing" : "swing",
						"hideEasing" : "linear",
						"showMethod" : "fadeIn",
						"hideMethod" : "fadeOut"
					}
					toastr.info(JSON.parse(response.body).responseMessage);
				});
			});
			function handleNotification(message) {
				wrapper.notify();
				toastr.info(message.body);
			}
		}

		var wrapper = new Vue({
			el : '#wrapper',
			data : {
				total : '',
				rows : '',
			},
			methods : {
				notify : function() {
					$.getJSON('/oa/notify/message', function(r) {
						wrapper.total = r.total;
						wrapper.rows = r.rows;
					});
				},
				personal : function() {
					layer.open({
						type : 2,
						title : '个人设置',
						maxmin : true,
						shadeClose : false,
						area : [ '800px', '600px' ],
						content : '/sys/user/personal'
					});
				}
			},
			created : function() {
				this.notify()
			}
		}) */ 
	</script>


</body>
</html>