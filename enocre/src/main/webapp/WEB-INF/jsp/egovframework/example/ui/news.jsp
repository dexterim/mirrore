<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
						<!-- news_tiles(ui/news.jsp) -->
						
						<!-- 따로 뺄 것 : news.js -->
						<script>
						$(document).ready(function () {
							var news_title="";
							
							/* model_userNews 받아오기  */
						    <c:forEach items="${userNews}" var="select_news">
						    	news_title += "<c:out value='${select_news}' /> | ";
							</c:forEach>
							
							/* news 필터링 */
							news_title = news_title.replace(/b/gi,"");
							news_title = news_title.replace(/(\/)/gi,"");
							news_title = news_title.replace(/</gi,"");
							news_title = news_title.replace(/>/gi,"");
							news_title = news_title.replace(/<br>/gi, "\n");
							news_title = news_title.replace(/<b>/gi, "");
							news_title = news_title.replace(/<\/b>/gi, "");
							news_title = news_title.replace(/&gt;/gi, "");
							news_title = news_title.replace(/&lt/gi, "");
							news_title = news_title.replace(/&quot;/gi, "\"");
							news_title = news_title.replace(/&amp;/gi, "&");
							news_title = news_title.replace(/&#034;/gi, "\"");
							news_title = news_title.replace(/\|/gi, ",");
							news_title = news_title.substring(0, news_title.length - 3);
							var news_array = news_title.split(' , ');
							
							/* description만 파싱해서 넣어주기 */
							var jobj = [];
							var news_description = "";
							for(var i=0;i<news_array.length;i++){
								//console.log(news_array[i]);
								jobj[i] = JSON.parse(news_array[i]);
								jobj[i] = jobj[i].description;
								news_description += jobj[i];
							}
							/* news_title 아이디를 가진 객체에 news 텍스트 추가하기 */
							document.getElementById("news_title").innerHTML=news_description;
						});
						</script>
						<div class="col-lg-6">
							<div class="card" >
							   	<div class="card-header ">
							   		<h2 class="card-title">
							       		<marquee  style="display:none;" class="tilesNews" id="news_title" width="600" height="80"></marquee>
							    	</h2>
							    </div>
							    <div class="card-body " style="color:#fff;">
								    <div id="rank">
								        <ul id="banner">      
										</ul>
								    </div>
							  	</div>
							</div>
						</div>