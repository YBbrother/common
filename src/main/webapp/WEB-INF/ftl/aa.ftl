<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>User List</title>
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
</head>
 
<body>
<table width="1500" height="600" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="160" height="65" align="center" valign="middle"><span class="STYLE15">ID</span></td>
        <td width="160" height="65" align="center" valign="middle"><span class="STYLE15">Username</span></td>
        <td width="160" height="65" align="center" valign="middle"><span class="STYLE15">Password</span></td>
      </tr>
      <tr>
        <td width="160" height="65" align="center" valign="middle"><span class="STYLE15">dwdw</span></td>
        <td width="160" height="65" align="center" valign="middle"><span class="STYLE15">rrrrr</span></td>
        <td width="160" height="65" align="center" valign="middle"><span class="STYLE15">hhhhhh</span></td>
      </tr>
      <#list persons as person>
      	<tr>
      		<td>${person.id}</td>
      		<td>${person.name}</td>
      		<td>${person.description}</td>
      	</tr>
      </#list>
    </table>
</body>
</html>