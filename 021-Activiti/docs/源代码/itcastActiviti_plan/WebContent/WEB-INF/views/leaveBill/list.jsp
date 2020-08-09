<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/js/commons.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假管理</title>
</head>
<body>
 	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
		                <td width="94%" valign="bottom"><span class="STYLE1">请假申请列表列表</span></td>
		              </tr>
		            </table></td>
		            <td><div align="right"><span class="STYLE1">
		              </span></div></td>
		          </tr>
		        </table></td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		        <td height="20" bgcolor="#FFFFFF" class="STYLE10" colspan="8"><div align="left">
					<a href="${pageContext.request.contextPath }/leaveBillAction_input.action">添加请假申请</a>
				</div></td>
		  </tr> 
		  <tr>
		    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
		      <tr>
		        <td width="5%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">ID</span></div></td>
		        <td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假人</span></div></td>
		        <td width="5%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假天数</span></div></td>
		        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假事由</span></div></td>
		        <td width="20%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假备注</span></div></td>
		        <td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假时间</span></div></td>
		        <td width="5%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">请假状态</span></div></td>
		        <td width="25%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">操作</span></div></td>
		      </tr>
		        <tr>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">18</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">范冰冰</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">3</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">病假</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">发烧</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">2014-01-05 16:26:13</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19">
			        	<div align="center">
			 					初始录入
		            	</div>
		            </td>
			        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
			        	<a href="${pageContext.request.contextPath }/leaveBillAction_input.action?id=1" >修改</a>
						<a href="leaveBillAction_delete.action?id=1" >删除</a>
						<a href="${pageContext.request.contextPath }/workflowAction_startProcess.action?id=1" >申请请假</a>
					</div></td>
			    </tr> 
			     <tr>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">19</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">范冰冰</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">4</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">事假</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">旅游</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">2014-01-08 10:11:11</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19">
			        	<div align="center">
			 					审批中
		            	</div>
		            </td>
			        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
			        	
			        </div></td>
			    </tr> 
			    <tr>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center">20</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">范冰冰</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">5</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">事假</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">拍戏</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">2014-01-12 13:22:11</div></td>
			        <td height="20" bgcolor="#FFFFFF" class="STYLE19">
			        	<div align="center">
			 					审核通过
		            	</div>
		            </td>
			        <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE21">
			        	<a href="${pageContext.request.contextPath }/leaveBillAction_input.action?id=1" >修改</a>
						<a href="leaveBillAction_delete.action?id=1" >删除</a>
						<a href="${pageContext.request.contextPath }/workflowAction_viewHisComment.action?id=1" >查看审核记录</a>
			        </div></td>
			    </tr> 
		      
		    </table></td>
		  </tr>
	</table>
</body>
</html>