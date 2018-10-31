<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles"  prefix="tiles"%>
<!DOCTYPE html>
<html>
	<head>
  		<tiles:insertAttribute name="header"/>
  	</head>
  	<body>
  		<div class="wrapper">
  			<div class="main-panel ps ps--active-y">
	  			
	  			<div class="content" style="padding: 10px 10px 30px 30px">
	  				<div class="row" style="height: 20vh;">
	  					<tiles:insertAttribute name="watch"/>
	  					<tiles:insertAttribute name="news"/>
					</div>
					<div class="row" style="height: 30vh;">
						<tiles:insertAttribute name="subway"/>
						<tiles:insertAttribute name="content"/>
					</div>
					<div class="row" >
						<tiles:insertAttribute name="calendar"/>
						<tiles:insertAttribute name="memo"/>
  						
  					</div>
  				</div>
  			</div>
  		</div>
  	</body>
    <tiles:insertAttribute name="script"/>
    
</html>