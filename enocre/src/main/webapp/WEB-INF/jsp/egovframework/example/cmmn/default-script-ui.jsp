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
    
    <!-- TTS Speech Service -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/materialize/0.95.1/js/materialize.min.js"></script>
    
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
		logout.enableLogoutSetting();
		//connect websocket
		send_message();
	});
	var tilesWatchOnOffStr = "<c:out value="${param.tilesWatch}"/>";
	var userId = "";
    var id_session = {"id_session_value" : userId};
	var count = $('#rank li').length;
    var height = $('#rank li').height();
    
    //websocket
    var wsUri = "ws://172.18.81.57:8081/enocre/websocket/echo.do";
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
        if(message.indexOf("java_client") != -1) {
        	setting.enableSetting();
        } else if(message.indexOf("key_motion") != -1){
        	message_motion = message.slice(0, message.length-11);
        	$('#alarmGame').html(message_motion);
        } else if(message.indexOf("logout") != -1) {
        	console.log("logout");
        	logout.enableLogoutSetting();
        } else if(message.indexOf("mirror_login") != -1) {
        	var message_login = message.slice(message.length-12, message.length);
            var message_id = message.slice(0, message.length-13);
            
            console.log("login된 아이디: "+message_id);
            console.log("login된 key: "+message_login);
            
            if(message_login == "mirror_login") {
            	console.log("login된 아이디: "+message_id);
            	userId = message_id;
            	memberInfo.getMemberInfo(message_login, userId);
            }
        } else if(message.indexOf("update_member") != -1) {
        	console.log("member data 변경");
            
        	if(message.indexOf("update_member_weather_loc") != -1) {
        		
        		console.log("update_member_weather_loc 변경");
        		var message_id = message.slice(0, message.length-26);
        		userId = message_id;
            	memberInfo.getMemberInfo("update_member_weather_loc", userId);
            	
        	}
        	if(message.indexOf("update_member_subway_loc") != -1) {
        		var message_id = message.slice(0, message.length-25);
        		userId = message_id;
        		console.log("update_member_subway_loc 변경"+message_id);
            	memberInfo.getMemberInfo("update_member_subway_loc", userId);
        	}
        } else if(message.indexOf("speakText") != -1) {
        	var speakStr = message.slice(10, message.length);
        	console.log(speakStr);
    		tilesSpeech.tilesSpeechFunc(speakStr);
    		
        } else if(message.indexOf("memo_update") != -1) {
            var message_id = message.slice(12, message.length);
            selectMemo.showMemo(message_id);
            
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
    						console.log(json_member);
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
    							
    							if(updateType == "mirror_login") {
    								welcomeUser.sayHello(message_name);
    								//사용자 메모 데이터
    								console.log("사용자 메모 요청 :"+userId);
    								selectMemo.showMemo(userId);
    								console.log("사용자 지하철 요청 :"+message_subway_loc);
    								subway_task.getSubway(message_subway_loc);
        			            	setTimeout(function() { 
        			            		setting.enableSetting();
        			            		}, 2000);
        			           
    							}else if(updateType.indexOf("weather_loc") != -1) {
    								
    								get_weather_api.myWeather(message_weather_loc);
    								setting.enableSetting();
    								
    							}else if(updateType.indexOf("subway_loc")!=-1) {
    								console.log("subway_loc: "+message_subway_loc);
        							subway_task.getSubway(message_subway_loc);
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
    var selectMemo = {
    		showMemo : function(userId) {
    			id_session = {"id_session_value" : userId};
    			$.ajax({
    				type		: "post",
    				url			: "selectMemo.do",
    				data		: JSON.stringify(id_session),
    				contentType	: "application/json",
    				success		: function(data){
    					var jobj_parse = JSON.parse(data);
    					if(jobj_parse.result === "success") {
    						var json_memo = jobj_parse.memoList;
    						var memo_str = "";
    						$.each(json_memo, function(i, item) {
    							
    							memo_str +="<div class='alert alert-info' style='background:#27293d;'>";
    							memo_str +="<button type='button' aria-hidden='true' class='close' data-dismiss='alert' aria-label='Close'>";
    							memo_str +="<i class='tim-icons icon-simple-remove'></i></button><b>";
    							memo_str +=item.title;
    							memo_str +="</b><span>";
    							memo_str +=item.content;
    							memo_str +="</span></div>";
    						});
    						$('#memo_area').html(memo_str);
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
    
    var subway_task = {
			getSubway : function(subway_loc) {
				console.log("사용자 지하철 요청 세부"+subway_loc);
				var subwayCode = subway_loc;
				subwayCode *= 1;
				
				var preSubwayCode = subwayCode-1;
				var nextSubwayCode = subwayCode+1;
				if(subwayCode <1000){
					preSubwayCode = "0"+preSubwayCode;
					nextSubwayCode = "0"+nextSubwayCode;
					subwayCode = "0"+subwayCode;
				}
				
				var now = new Date();
				if(now.getDay() == 0){
					day = "3";
				} else {
					day = "1";
				}
				console.log("사용자 지하철 요청 데이터 설정"+subwayCode+":"+day);
				subway_task.ascending(subwayCode,day);
				subway_task.descending(subwayCode,day);
				subway_task.preSubwayCodefunc(preSubwayCode,day);
				subway_task.nextSubwayCodefunc(nextSubwayCode,day);
			},
    		ascending : function(subwayCode,day) {
    			$.ajax({
    				url : "http://openAPI.seoul.go.kr:8088/744c6b546a79616e37334d46797069/xml/SearchArrivalTimeOfLine2SubwayByIDService/1/1/"+subwayCode+"/1/"+day+"/",	
    				dataType : "xml",
    				success : function(data) {
    					var $data = $(data).find("SearchArrivalTimeOfLine2SubwayByIDService>row");
    					//데이터를 테이블 구조에 저장. html의 table태그, class는 table로 하여 부트스트랩 적용
    					if ($data.length > 0) {
    						$.each($data, function(i, o) {

    							//오픈 API에 정의된 변수와 내가 정의한 변수 데이터 파싱
    							var $STATION_CD = $(o).find("STATION_CD").text(); // 선택 역 코드
    							var $SUBWAYNAME = $(o).find("SUBWAYNAME").text(); // 선택 역 명
    							var $LEFTTIME = $(o).find("LEFTTIME").text(); //출발 시간
    							var $DESTSTATION_NAME = $(o).find("DESTSTATION_NAME").text();// 도착 역 명
    							
    							console.log("STATION_CD 선택역코드 : "+ $STATION_CD +"\n");
    							console.log("SUBWAYNAME 역이름 : "+ $SUBWAYNAME+"\n");
    							console.log( $DESTSTATION_NAME +"행");
    							console.log("LEFTTIME 출발시간 : "+ $LEFTTIME+"\n");
    							
    							$("#subway_loc").html($SUBWAYNAME);		
    							$("#ascending_subway").html($DESTSTATION_NAME+"행\n"+$LEFTTIME);

    						});// end of each 
    					}
    				},
    				//에러 발생시 
    				error : function() {
    					alert("에러~");
    				}
    			});
    		},
    		descending : function(subwayCode,day){
    			$.ajax({
    				url : "http://openAPI.seoul.go.kr:8088/744c6b546a79616e37334d46797069/xml/SearchArrivalTimeOfLine2SubwayByIDService/1/1/"+subwayCode+"/2/"+day+"/",	
    				
    				dataType : "xml",
    				success : function(data) {
    					var $data = $(data)
    							.find("SearchArrivalTimeOfLine2SubwayByIDService>row");
    					//데이터를 테이블 구조에 저장. html의 table태그, class는 table로 하여 부트스트랩 적용
    					if ($data.length > 0) {
    						$.each($data, function(i, o) {

    							//오픈 API에 정의된 변수와 내가 정의한 변수 데이터 파싱
    							var $STATION_CD = $(o).find("STATION_CD").text(); // 선택 역 코드
    							var $SUBWAYNAME = $(o).find("SUBWAYNAME").text(); // 선택 역 명
    							var $LEFTTIME = $(o).find("LEFTTIME").text(); //출발 시간
    							var $DESTSTATION_NAME = $(o).find("DESTSTATION_NAME").text();// 도착 역 명
    							
    							console.log("STATION_CD 선택역코드 : "+ $STATION_CD +"\n");
    							console.log("SUBWAYNAME 역이름 : "+ $SUBWAYNAME+"\n");
    							console.log( $DESTSTATION_NAME +"행");
    							console.log("LEFTTIME 출발시간 : "+ $LEFTTIME+"\n");
    							
    							//document.getElementById("subway_loc").innerHTML=$SUBWAYNAME;		
    							$("#descending_subway").html($DESTSTATION_NAME+"행\n"+$LEFTTIME);
    							

    						});// end of each 
    					}
    				},
    				//에러 발생시 
    				error : function() {
    					alert("에러~");
    				}
    			});
    		},
    		preSubwayCodefunc : function(preSubwayCode,day) {
    			$.ajax({
    				url : "http://openAPI.seoul.go.kr:8088/744c6b546a79616e37334d46797069/xml/SearchArrivalTimeOfLine2SubwayByIDService/1/1/"+preSubwayCode+"/1/"+day+"/",	
    				
    				dataType : "xml",
    				success : function(data) {
    					var $data = $(data)
    							.find("SearchArrivalTimeOfLine2SubwayByIDService>row");
    					//데이터를 테이블 구조에 저장. html의 table태그, class는 table로 하여 부트스트랩 적용
    					if ($data.length > 0) {
    						$.each($data, function(i, o) {

    							//오픈 API에 정의된 변수와 내가 정의한 변수 데이터 파싱
    							var $SUBWAYNAME = $(o).find("SUBWAYNAME").text(); // 선택 역 명
    							
    							console.log("SUBWAYNAME 역이름 : "+ $SUBWAYNAME+"\n");
    							
    							//document.getElementById("subway_loc").innerHTML=$SUBWAYNAME;		
    							$("#pre_subway_loc").html($SUBWAYNAME);
    								

    						});// end of each 
    					}
    				},
    				//에러 발생시 
    				error : function() {
    					alert("에러~");
    				}
    			});
    		},
    		nextSubwayCodefunc :function(nextSubwayCode,day) {
    			$.ajax({
    				url : "http://openAPI.seoul.go.kr:8088/744c6b546a79616e37334d46797069/xml/SearchArrivalTimeOfLine2SubwayByIDService/1/1/"+nextSubwayCode+"/1/"+day+"/",	
    				
    				dataType : "xml",
    				success : function(data) {
    					var $data = $(data)
    							.find("SearchArrivalTimeOfLine2SubwayByIDService>row");
    					//데이터를 테이블 구조에 저장. html의 table태그, class는 table로 하여 부트스트랩 적용
    					if ($data.length > 0) {
    						
    						$.each($data, function(i, o) {

    							//오픈 API에 정의된 변수와 내가 정의한 변수 데이터 파싱
    							var $SUBWAYNAME = $(o).find("SUBWAYNAME").text(); // 선택 역 명
    							
    							//<tbody><tr><td>태그안에 파싱하여 추출된 데이터 넣기
    							
    							console.log("SUBWAYNAME 역이름 : "+ $SUBWAYNAME+"\n");
    							
    							//document.getElementById("subway_loc").innerHTML=$SUBWAYNAME;		
    							$("#next_subway_loc").html($SUBWAYNAME);
    								

    						});// end of each 
    					}
    				},
    				//에러 발생시 
    				error : function() {
    					alert("에러~");
    				}
    			});
    		}
			
    	}
    
    var alarmGame = {
    		init : function(){
	    		//요소 생성 부분
	    		var motionsLength = 3;
	    		var motions = new Array("down", "up", "left", "right");
	    		var correctMotion = new Array(null, null, null);
	
	    		for (var i = 0; i<correctMotion.length; i++) {
	    			correctMotion[i] = alarmGame.randomItem(motions);
	    			$("#alarmGame").append("<div class='alarmGame' style='color:#fff'>"+correctMotion[i]+"</div>");
	    			console.log("정답 :"+correctMotion[i]);
	    		}
    		},
    		ramdomItem : function(motionText){
    			return motionText[Math.floor(Math.random() * motionText.length)];
    		},
    //게임 부분
    //nowPosition의 값이 0에서 부터 2가되기 전까지 돌아 간다!!!!!!!!
    		 isCorrectMotion : function(myMotion) {
    			 var nowPostion = 0;
    			 var correctMotionArray = $(".alarmGame").val();
    			 var correctMotion = jQuery.makeArray(correctMotionArray);
    			 console.log('correctMotion :'+correctMotion);
    			 
    			if (myMotion == correctMotion[nowPosition]) {
    				nowPosition++;// final 변수에 

    				$("#alarmGame").append('정답!<br />');
    				//nowPosition번재에 해당하는 이미지를 삭제하는 문구 추가
    				//..
    			} else {
    				//모션 재입력 문구 
    				$("#alarmGame").append('삐! 모션이 틀렸습니다! 다시 해보세요<br />');
    			}
    			if (nowPosition == 2) {
    				// 이 함수를 종료하고 모션 전체 성공메세지 띄우기
    				
    			}
    		}
    }
    var tilesSpeech = {
    		tilesSpeechFunc : function(str) {
    		   
    		        console.log("음성 인식 확인: "+str);
    		        var text = str;
    		        var msg = new SpeechSynthesisUtterance();
    		        var voices = window.speechSynthesis.getVoices();
    		        msg.voice = voices[13];
    		        msg.rate = 1;
    		        msg.pitch = 1;
    		        msg.text = text;

    		        msg.onend = function(e) {
    		          console.log('Finished in ' + event.elapsedTime + ' seconds.');
    		        };
    		        speechSynthesis.speak(msg);

    		  // 음성종류의 배열(voices)의 방번호 

    		  // 0: Microsoft Heami Desktop - Korean (default)
    		  // 1: Microsoft Zira Desktop - English (United States)
    		  // 2: Google Deutsch
    		  // 3: Google US English
    		  // 4:Google UK English Female
    		  // 5: Google UK English Male
    		  // 6: Google español
    		  // 7: Google español de Estados Unidos
    		  // 8: Google français
    		  // 9: Google हिन्दी
    		  // 10: Google Bahasa Indonesia
    		  // 11: Google italiano
    		  // 12: Google 日本語
    		  // 13: Google 한국의
    		  // 14: Google Nederlands
    		  // 15: Google polski
    		  // 16: Google português do Brasil
    		  // 17: Google русский
    		  // 18: Google 普通话（中国大陆）
    		  // 19: Google 粤語（香港）
    		  // 20: Google 國語（臺灣）

    		
    		}
    }
    
    </script>
