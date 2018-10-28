<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>비밀번호 오류</title>
	<link rel="stylesheet" href="/StrutsBoard/board/common/css.css" type="text/css">
	<script type="text/javascript">
		function ErrorMessage() {
			alert("비밀번호가 틀립니다.");
			history.back(-1);
		}
	</script>
</head>

 <body>
  	<script>ErrorMessage()</script>
  </body>
</html>