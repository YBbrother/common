<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Job List</title>
<style type="text/css">
<!--
.STYLE1 {
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 36px;
	color: #FF0000;
}

.STYLE13 {
	font-size: 24
}

.STYLE15 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
}
-->
</style>
<!-- 引入静态资源文件 -->
<#include "include/header.ftl">
</head>

<body>
	<table>
		<tr> <!-- 只能输中文 -->
			<td>姓名（中文）<font style="color: red;">*</font></td>
			<td style="text-align: left;"><input type="text"
				class="ajaxtype ainput" name="user.cn_name" id="cn_name"
				onpaste="return false" ondragenter="return false"
				onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')" /></td>
		</tr>
		<tr> <!-- 只能输英文 -->
			<td>姓名（英文）<font style="color: red;">*</font></td>

			<td style="text-align: left;"><input type="text"
				class="ajaxtype ainput" name="user.en_name" id="en_name"
				onkeyup="this.value=checkEn(this.value)" /></td>
		</tr>
	</table>
	
	<script type="text/javascript">
		function checkEn(str) {
			var temp = ""
			for (var i = 0; i < str.length; i++)
				if (str.charCodeAt(i) > 0 && str.charCodeAt(i) < 255)
					temp += str.charAt(i)
			return temp
		}
	</script>

</body>

</html>