<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	var apiURI = "http://api.openweathermap.org/data/2.5/weather?q="+"seoul"+"&appid="+"6c360890e6d16d5945e3ae0ec8784697";
	var imgURL =""
	$(function() {
/* 		setInterval(
				function(){
				 reloadWeb.uploadFile();
			}
				,10000); */

		var textElem = document.getElementById("clocktext");
		var targetWidth = 0.2;  // Proportion of full screen width
		var curFontSize = 20;  // Do not change
		
		function updateClock() {
			var d = new Date();
			var s = "";
			s += ((d.getHours() + 11) % 12 + 1) + ":";
			s += (10 > d.getMinutes() ? "0" : "") + d.getMinutes() + ":";
			s += (10 > d.getSeconds() ? "0" : "") + d.getSeconds() + "\u00A0";
			s += d.getHours() >= 12 ? "pm" : "am";
			textElem.textContent = s;
			setTimeout(updateClock, 1000 - d.getTime() % 1000 + 20);
		}
		
		function updateTextSize() {
			for (var i = 0; 3 > i; i++) {  // Iterate for better better convergence
				curFontSize *= targetWidth / (textElem.offsetWidth / textElem.parentNode.offsetWidth);
				textElem.style.fontSize = curFontSize + "pt";
			}
		}
		
		updateClock();
		updateTextSize();
		window.addEventListener("resize", updateTextSize);
			
	});

	var imageData = {
			display : function(enocreList) {
				if(enocreList.length > 0){
					$.each(enocreList, function(i, item) {
						var imageStr = "<tr><td></td>"+item.id+"<td>"+item.imageName+
						"</td><td>"+item.imagePath+"</td><td>"+item.imageUrl+"</td><td><img src="
						+item.imageUrl+" style='height:100px'></td></tr>";
						$("#imageUploadRow").append(imageStr);
					})
				} else {
					$("#imageUploadRow").append("<tr><td>데이터 없음</td></tr>");
				}
			}
	}
	var reloadWeb = {
			uploadFile : function(){
				$("#imageUploadRow").children().remove();
				
				$.ajax({
					url			: "enocreWeb2.do",
					success		: function(data){
						var jobj = JSON.parse(data);
						
						if(jobj.result == "success"){
							imageData.display(jobj.enocreList);
						}
					}
				})
			}
	};
	</script>

<div class="sidebar" data-color="black">
	<span id="clocktext" style="font-kerning:none; color:white; margin:50px; margin-top:100px; background-color:black;"></span>
</div>
