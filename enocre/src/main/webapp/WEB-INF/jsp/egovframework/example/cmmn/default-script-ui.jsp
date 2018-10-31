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
		step.scrolling(1);
		
		//connect websocket
		send_message();
	});
	var tilesWatchOnOffStr = "<c:out value="${param.tilesWatch}"/>";
    var id_session = {"id_session_value" : "miri@gmail.com"};
	var count = $('#rank li').length;
    var height = $('#rank li').height();
    
    //websocket
    var wsUri = "ws://172.18.92.153:8081/enocre/websocket/echo.do";
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
        } else {
        	var message_login = message.slice(message.length-5, message.length);
            var message_id = message.slice(0, message.length-6);
            
            if(message_login == "login") {
            	console.log(message_id);
            	welcomeUser.sayHello(message_id);
            	
            	setTimeout(function() { 
            		setting.enableSetting();
            		}, 2000);
            }
        }
    }

    window.addEventListener("load", init, false);
    
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
    			var welcomeUserText = "Hello, "+user_id;
    			var welcomeTextAnimation = $('#animationSandbox');
				$('#welcomeUser').text(welcomeUserText);
				welcomeTextAnimation.addClass('animated zoomIn');
			    setTimeout(function () {
			    	welcomeTextAnimation.fadeOut();
			    }, 2000 );
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
