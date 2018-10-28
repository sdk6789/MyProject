<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>스트럿츠2 게시판</title>
<link rel="stylesheet" href="/StrutsBoard/board/common/css.css"
	type="text/css">
</head>

<body>
	<table width="600" border="0" cellspacing="0" cellpadding="2">
		<tr>
			<td align="center"><h2>스트럿츠2 게시판</h2></td>
		</tr>
		<tr>
			<td height="20"></td>
		</tr>
	</table>

	<table width="600" border="0" cellspacing="0" cellpadding="2">
		<tr align="center" bgcolor="#F3F3F3">
			<td width="50"><strong>번호</strong></td>
			<td width="350"><strong>제목</strong></td>
			<td width="70"><strong>글쓴이</strong></td>
			<td width="80"><strong>날짜</strong></td>
			<td width="50"><strong>조회</strong></td>
		</tr>
		<tr bgcolor="#777777">
			<td height="1" colspan="5"></td>
		</tr>

		<s:iterator value="list" status="stat">
			<s:url id="viewURL" action="viewAction">
				<s:param name="no">
					<s:property value="no" />
				</s:param>
				<s:param name="currentPage">
					<s:property value="currentPage" />
				</s:param>
			</s:url>

			<tr>
				<td><s:property value="no" /></td>
				<td align="left">
						<s:if test="re_level != 0">
							<c:forEach var="i" begin= "${re_level}" end="0">&nbsp;&nbsp;</c:forEach>→
						</s:if>
						<s:a href="%{viewURL}"><s:property value="subject" />
					</s:a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="regdate" /></td>
				<td><s:property value="readhit" /></td>
			</tr>
			
			<tr bgcolor="#777777">
				<td height="1" colspan="5"></td>
			</tr>

		</s:iterator>

		<s:if test="list.size() <= 0">

			<tr bgcolor="#FFFFFF" align="center">
				<td colspan="5">등록된 게시물이 없습니다.</td>
			</tr>
			<tr bgcolor="#777777">
				<td height="1" colspan="5"></td>
			</tr>

		</s:if>

		<tr align="center">
			<td colspan="5"><s:property value="pagingHtml" escape="false" /></td>
		</tr>

		<tr align="right">
			<td colspan="5"><input type="button" value="글쓰기" class="inputb"
				onClick="javascript:location.href='writeForm.action?currentPage=<s:property value="currentPage" />';">
			</td>
		</tr>

		<tr align="center">
			<td colspan="5">
				<form>
					<select name="searchNum">
						<option value="0">작성자</option>
						<option value="1">제목</option>
						<option value="2">내용</option>
					</select>
					<s:textfield name="searchKeyword" theme="simple" value=""
						cssStyle="width:120px" maxlength="20" />
					<input type="submit" value="검색" />
				</form>
			</td>
		</tr>
	</table>

</body>
</html>