<#setting classic_compatible=true>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>登录</title>
		<meta content="" name="" />
		<title>DayDayUp</title>
		<#include "include/header.ftl">
		<!--[if lt IE 9]>
		    <meta http-equiv="refresh" content="0;ie.html"/>
		    <![endif]-->
		<link rel="stylesheet" href="${basepath}/styles/login/reset.css"/>
        <link rel="stylesheet" href="${basepath}/styles/login/supersized.css"/>
        <link rel="stylesheet" href="${basepath}/styles/login/style.css"/>
	</head>
	<body id="body">
		 <div class="page-container">
            <form id="_form" action="" method="post">
                <input type="text" name="account" class="username" placeholder="Account">
                <input type="password" name="password" class="password" placeholder="Password">
                <div style="text-align: left; margin-left: 10px;">
	                <label><input type="checkbox" checked="checked"  id="rememberMe"style="width: 10px; height: 10px;">记住我</label>
                </div>
                <button type="button" id="login">登录</button>
                <button type="button" id="register" class="register">Register</button>
                <div class="error"><span>+</span></div>
            </form>
        </div>
        <!-- Javascript -->
        <script type="text/javascript" src="${basepath}/scripts/plugins/jquery/jquery1.8.3.min.js"></script>
		<script  src="${basepath}/scripts/login/MD5.js"></script>
        <script  src="${basepath}/scripts/login/supersized.3.2.7.min.js"></script>
        <script  src="${basepath}/scripts/login/supersized-init.js"></script>
		
        <script type="text/javascript">
			jQuery(document).ready(function() {
				try{
					var _href = window.location.href+"";
					if(_href && _href.indexOf('?kickout')!=-1){
						layer.msg('您已经被踢出，请重新登录！');
					}
				}catch(e){
					
				}
				//回车事件绑定
				document.onkeydown=function(event){
					var e = event || window.event || arguments.callee.caller.arguments[0];
					if(e && e.keyCode==13){ 
						$('#login').click();
					}
				}; 
			
				//登录操作
			    $('#login').click(function(){
			        var username = $('.username').val();
			        var password = $('.password').val();
			        if(username == '') {
			            $('.error').fadeOut('fast', function(){
			                $('.error').css('top', '27px').show();
			            });
			            $('.error').fadeIn('fast', function(){
			                $('.username').focus();
			            });
			            return false;
			        }
			        if(password == '') {
			            $('.error').fadeOut('fast', function(){
			                $('.error').css('top', '96px').show();
			            });
			            $(this).find('.error').fadeIn('fast', function(){
			                $('.password').focus();
			            });
			            return false;
			        }
			        var password = MD5(username +"#" + password),
			        	data = {password:password,username:username,rememberMe:$("#rememberMe").is(':checked')};
			        var load = layer.load();
			        
			        $.ajax({
			        	type:"post",
			        	url:"${basePath}/login/submitLogin.do",
			        	data:data,
			        	dataType:"json",
			        	beforeSend:function(){
			        		layer.msg('开始登录，请注意后台控制台。');
			        	},
			        	success:function(result){
				        	layer.close(load);
				    		if(result && result.status != 200){
				    			layer.msg(result.message,function(){});
				    			$('.password').val('');
				    			return;
				    		}else{
				    			layer.msg('登录成功！');
				    			setTimeout(function(){
				    				//登录返回
					    			window.location.href= result.back_url || "${basePath}/";
				    			},1000)
				    		}
			        	},
			        	error:function(e){
			        		console.log(e,e.message);
			        		layer.msg('请看后台Java控制台，是否报错，确定已经配置数据库和Redis',new Function());
			        	}
			        });
			    });
			    $('.page-container form .username, .page-container form .password').keyup(function(){
			        $(this).parent().find('.error').fadeOut('fast');
			    });
			    //注册
			    $("#register").click(function(){
			    	window.location.href="register.do";
			    });
			});
        </script>
			
	</body>
</html>