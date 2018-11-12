 <%@ page language="java" contentType="text/html; charset=UTF-8" %> 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" ></script>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://paulkr.github.io/quietflow.js/lib/quietflow.min.js"></script>
	<script src="https://cdn.ckeditor.com/4.9.2/basic/ckeditor.js"></script>
	<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
	
	<!-- java_script_error -->
    <script src="js/plugins/perfect-scrollbar.jquery.min.js"></script>
    
    <!-- Chart JS -->
    <script src="js/plugins/chartjs.min.js"></script>
    
    <!-- Notifications Plugin    -->
    <script src="js/plugins/bootstrap-notify.js"></script>
    
    <!-- Control Center for Black Dashboard: parallax effects, scripts for the example pages etc -->
    <script src="js/black-dashboard.min.js?v=1.0.0" type="text/javascript"></script>
    
    <!-- Black Dashboard DEMO methods, don't include it in your project! -->
    <script src="demo/demo.js"></script>
    
    <!-- web_socket -->
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <!-- stomp -->
    <script type="text/javascript" src="js/stomp.min.js"></script>
    
    <script>
    // enable infomation db select
	$(function() {
		//console.log("tilesWatchOnOffStr:"+ tilesWatchOnOffStr +"pathStr : "+pathStr);
		/* tilesWatch.tilesWatchDisplay(tilesWatchOnOffStr); */
		var query = new URLSearchParams(location.search);
		console.log("queryString :" + query.get('key'));
		step.scrolling(1);
		
		//connect websocket
		send_message();
	});
	var tilesWatchOnOffStr = "<c:out value="${param.tilesWatch}"/>";
	var userId = "";
    var id_session = {"id_session_value" : userId};
	var count = $('#rank li').length;
    var height = $('#rank li').height();
    
    //websocket
    var wsUri = "ws://172.20.10.5:8081/enocre/websocket/echo.do";
    var output;
    function init() {
    	console.log("socket_init");
        output = document.getElementById("output");
    }
    function send_message() {
    	console.log("socket_send_message = 소켓을 생성함");
        websocket = new WebSocket(wsUri);
        websocket.onopen = function(evt) {
            onOpen(evt)
        };
        websocket.onmessage = function(evt) {
            onMessage(evt)
        };
        websocket.onerror = function(evt) {
            onError(evt)
        };
    }
    function onOpen(evt) {
    	console.log("socket_onOpen");
        writeToScreen("Connected to Endpoint!");
        doSend(evt.data);
    }
    //서버에서 메시지를 받았을 때 처리하는 메서드
    function onMessage(evt) {
    	console.log("socket_onMessage");
        writeToScreen(evt.data);
    }
    function onError(evt) {
    	console.log("socket_error");
        writeToScreen('ERROR: ' + evt.data);
    }
    function doSend(message) {
    	console.log("socket_doSend");
        writeToScreen("Message Sent: " + message);
        websocket.send(message);
        //websocket.close();
    }
    function writeToScreen(message) {
        var pre = document.createElement("p");
        pre.style.wordWrap = "break-word";
        pre.innerHTML = message;
        //output.appendChild(pre);
        if(message == "java_client") {
        	setting.enableSetting();
        } else if(message.indexOf("logout") != -1) {
        	console.log("logout");
        	var mirror_id = message.slice(message.length-11, message.length);
        	console.log("mirror_id = "+mirror_id);
        	logout.enableLogoutSetting();
        	$("#mirror_id_invalidate").submit();
        } else if(message.indexOf("login") != -1) {
        	var message_login = message.slice(message.length-5, message.length);
            var message_id = message.slice(0, message.length-6);
            
            if(message_login == "login") {
            	console.log("login된 아이디: "+message_id);
            	userId = message_id;
            	memberInfo.getMemberInfo("login", userId);
            }
        } else if(message.indexOf("update_member") != -1) {
        	console.log("member data 변경");
            
        	if(message.indexOf("update_member_weather_loc") != -1) {
        		console.log("update_member_weather_loc 변경");
        		var message_id = message.slice(0, message.length-26);
        		userId = message_id;
            	memberInfo.getMemberInfo("update_member_weather_loc", userId);
            	
        	}else if(message.indexOf("update_member_subway_loc") != -1) {
        		console.log("update_member_subway_loc 변경");
        		var message_id = message.slice(0, message.length-25);
        		userId = message_id;
            	memberInfo.getMemberInfo("update_member_subway_loc", userId);
        	}
        }
    }

    window.addEventListener("load", init, false);
    
    //memberInfo select
    var memberInfo = {
    		getMemberInfo : function(updateType, userId) {
    			console.log("위젯에 띄울 멤버 데이터 요청");
    			id_session = {"id_session_value" : userId};
    			$.ajax({
    				type		: "post",
    				url			: "get_member_info.do",
    				data		: JSON.stringify(id_session),
    				contentType	: "application/json",
    				success		: function(data){
    					var jobj_parse = JSON.parse(data);
    					if(jobj_parse.result === "success") {
    						var json_member = jobj_parse.memberInfoList;
    						$.each(json_member, function(i, item) {
    							var message_name = item.name;
    							
    							var message_weather_loc = item.weatherLoc;
    							if(message_weather_loc == "서울시") {
    								message_weather_loc = "seoul";
    								
    							}else if(message_weather_loc == "경기도"){
    								message_weather_loc = "Gyeonggi-do";
    								
    							}else if(message_weather_loc == "강원도"){
    								message_weather_loc = "chuncheon";
    								
    							}else if(message_weather_loc == "충청도"){
    								message_weather_loc = "daejeon";
    								
    							}else if(message_weather_loc == "전라도"){
    								message_weather_loc = "Jeonju";
    								
    							}else if(message_weather_loc == "경상도"){
    								message_weather_loc = "busan";
    								
    							}
    							var message_subway_loc = item.subwayLoc;
    							console.log("subway_loc: "+message_subway_loc);	
    							
    							if(updateType == "login") {
    								welcomeUser.sayHello(message_name);
        			            	setTimeout(function() { 
        			            		setting.enableSetting();
        			            		}, 2000);
        			           
    							} else if(updateType == "update_member_weather_loc") {
    								
    								get_weather_api.myWeather(message_weather_loc);
    								setting.enableSetting();
    								
    							} else if((updateType == "update_member_subway_loc")) {
    								
    								console.log("subway_loc: "+message_subway_loc);	
    							}
    							
   						});
   					}
   				}});
    		}
    }
    //weather_data_update
    var get_weather_api = {
			myWeather : function(my_weather_loc) {
				var myWeather = "";
				var apiURI = "http://api.openweathermap.org/data/2.5/weather?q="+my_weather_loc+"&appid="+"6c360890e6d16d5945e3ae0ec8784697";
			
		        $.ajax({
		            url: apiURI,
		            dataType: "json",
		            type: "GET",
		            async: "false",
		            success: function(resp) {
		            	// $("#myW").html(imgURL);
						
		            	console.log(resp);
		                $("#myWeatherNowTemp").html((resp.main.temp- 273.15).toFixed(1)+" °C");
		                $("#myWeatherNowHum").html(resp.main.humidity+" %");
		                $("#myWeatherMain").html(resp.main.humidity+" %");
		                /* console.log("현재온도 : "+ (resp.main.temp- 273.15) );
		                console.log("현재습도 : "+ resp.main.humidity);
		                console.log("날씨 : "+ resp.weather[0].main );
		                console.log("상세날씨설명 : "+ resp.weather[0].description );
		                console.log("날씨 이미지 : "+ resp.weather[0].icon );
		                console.log("바람   : "+ resp.wind.speed );
		                console.log("나라   : "+ resp.sys.country );
		                console.log("도시이름  : "+ resp.name );
		                console.log("구름  : "+ (resp.clouds.all) +"%" ); */
		                
		                myWeather = resp.weather[0].main.toLowerCase();
		                console.log("myWeather1:"+myWeather);
		                getWeatherIcon(myWeather);
		            }
		        })
			}
	}
    var setting = {
    		enableSetting : function() {
    			console.log("ajax 통신");
    			$.ajax({
    				type		: "post",
    				url			: "setting.do",
    				data		: JSON.stringify(id_session),
    				contentType	: "application/json",
    				success		: function(data){
    					var jobj_parse = JSON.parse(data);
    					if(jobj_parse.result === "success") {
    						var json_setting = jobj_parse.memberSettingList;
    						$.each(json_setting, function(i, item) {
    							tilesWatch.tilesWatchDisplay(item.weather);
    							tilesNews.tilesNewsDisplay(item.news);
    							tilesSubway.tilesSubwayDisplay(item.subway);
    							tilesCalendar.tilesCalendarDisplay(item.calendar);
    							tilesMemo.tilesMemoDisplay(item.memo);
   						});
   					}
   				}});
    		}
    }
    var logout = {
    		enableLogoutSetting : function() {
    			tilesWatch.tilesWatchDisplay(0);
				tilesNews.tilesNewsDisplay(0);
				tilesSubway.tilesSubwayDisplay(0);
				tilesCalendar.tilesCalendarDisplay(0);
				tilesMemo.tilesMemoDisplay(0);
    		}
    }
    
    //news scrolling method
	var step = {
			scrolling : function(index) {
				$('#rank ul li').delay(2000).animate({
		            top: -height * index
		        }, 500, function() {
		        	step.scrolling((index + 1) % count);
		        });
			}
	}
    
    //welcome_user
    var welcomeUser = {
    		sayHello : function(user_id) {
    			console.log("name: "+user_id);
    			var welcomeUserText = "Hello, "+user_id;
    			var welcomeTextAnimation = $('#animationSandbox');
				$('#welcomeUser').text(welcomeUserText);
				
				welcomeTextAnimation.addClass('animated zoomIn');
				welcomeTextAnimation.css('display','block');
				
			    setTimeout(function () {
			    	welcomeTextAnimation.fadeOut();
			    }, 2000 );
			    welcomeTextAnimation.removeClass('animated zoomIn');
    		}
    }
    
    //enable controller method
	var tilesWatch = {
		tilesWatchDisplay : function(opt) {
			if (opt==1) {
				$('.tilesWatch').css('display','block');
			}else if(opt==0){ //0일경우 display='none'보이지않음
				$('.tilesWatch').css('display','none');			
			} else {
				$('.tilesWatch').css('display','block');
			}
		},
		tilesWatchDisplayNone : function() {
			$('.tilesWatch').css('display','none');
		},
		tilesWatchDisplayblock : function() {
			$('.tilesWatch').css('display','block');
		}
	}
	var tilesNews = {
		tilesNewsDisplay : function(opt) {
			if (opt==1) {
				$('.tilesNews').css('display','block');
			}else if(opt==0){ //0일경우 display='none'보이지않음
				$('.tilesNews').css('display','none');			
			} else {
				$('.tilesNews').css('display','block');
			}
		}
	}
	var tilesCalendar = {
		tilesCalendarDisplay : function(opt) {
			if (opt==1) {
				$('.tilesCalendar').css('display','block');
			}else if(opt==0){ //0일경우 display='none'보이지않음
				$('.tilesCalendar').css('display','none');			
			} else {
				$('.tilesCalendar').css('display','block');
			}
		}
	}
	var tilesMemo = {
		tilesMemoDisplay : function(opt) {
			if (opt==1) {
				$('.tilesMemo').css('display','block');
			}else if(opt==0){ //0일경우 display='none'보이지않음
				$('.tilesMemo').css('display','none');			
			} else {
				$('.tilesMemo').css('display','block');
			}
		}
	}
    var tilesSubway = {
	   		tilesSubwayDisplay : function(opt) {
	   			if (opt==1) {
	   				$('.tilesSubway').css('display','block');
	   			}else if(opt==0){ //0일경우 display='none'보이지않음
	   				$('.tilesSubway').css('display','none');			
	   			} else {
	   				$('.tilesSubway').css('display','block');
	   			}
	   		}
    }
    </script>
<form id="mirror_id_invalidate" action="newsWeb.do" method="get">
        <input type="hidden" name="key" value="218M10N0001">
    </form>