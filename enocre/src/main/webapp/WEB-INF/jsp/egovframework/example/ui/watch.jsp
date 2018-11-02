<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
						<!-- watch_tiles(ui/watch.jsp) -->
						<link href="weather-icons-master/css/weather-icons.min.css" rel="stylesheet" />
						
						<div class="col-lg-4">
						  <div class="card" data-color="#000">
						    <div class="card-header">
						      	<h3 class="card-title" style="margin-bottom:0px;">
						      		<span id="clocktext" style="font-kerning:none; color:white; margin:50px; margin-left:0px; margin-bottom:0px;font-size:50px"></span>
						      		<i class="tilesWatch wi" id="myWeatherNowIcon" style="font-size: 50px; display:none;"></i>
								</h3>
						      	<h5 class="card-category">
						      		<span class="tilesWatch" id="myWeatherNowHum" style="font-size:2.75em; font-kerning:none; color:white; margin:0px; padding:0px; display:none;"></span> 
									<br>
									<span class="tilesWatch" id="myWeatherNowTemp" style="font-size:2.75em; font-kerning:none; color:white; margin:0px; padding:0px; display:none;"></span>
							  	</h5>
						    </div>
						    
						    	
							<br>
							<!-- 따로 뺄 것: clock.js -->
							<script type="text/javascript">
								var textElem = document.getElementById("clocktext");
								var targetWidth = 0.3;  // Proportion of full screen width
								var curFontSize = 50;  // Do not change
								
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
									}
								}
								
								updateClock();
								updateTextSize();
								window.addEventListener("resize", updateTextSize);
								
							</script>
							
							<!-- 따로 뺄 것 : weather.js -->
							
							<script type="text/javascript">
								
								var weather_loc = "seoul";
								console.log(weather_loc);
								var apiURI = "http://api.openweathermap.org/data/2.5/weather?q="+weather_loc+"&appid="+"6c360890e6d16d5945e3ae0ec8784697";
							
								$(document).ready( function() {
									var myWeather = "";
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
							});
								
								
							function getWeatherIcon(myWeather) {
								var weathers = {
										 "thunderstorm" : "wi-owm-200 ", 
										 "thunderstorm" : "wi-owm-201 ", 
										 "thunderstorm" : "wi-owm-202 ", 
										 "lightning" : "wi-owm-210 ", 
										 "lightning" : "wi-owm-211 ", 
										 "lightning" : "wi-owm-212 ", 
										 "lightning" : "wi-owm-221 ", 
										 "thunderstorm" : "wi-owm-230 ", 
										 "thunderstorm" : "wi-owm-231 ", 
										 "thunderstorm" : "wi-owm-232 ", 
										 "sprinkle" : "wi-owm-300 ", 
										 "sprinkle" : "wi-owm-301 ", 
										 "rain" : "wi-owm-302 ", 
										 "rain-mix" : "wi-owm-310 ", 
										 "rain" : "wi-owm-311 ", 
										 "rain" : "wi-owm-312 ", 
										 "showers" : "wi-owm-313 ", 
										 "rain" : "wi-owm-314 ", 
										 "sprinkle" : "wi-owm-321 ", 
										 "sprinkle" : "wi-owm-500 ", 
										 "rain" : "wi-owm-501 ", 
										 "rain" : "wi-owm-502 ", 
										 "rain" : "wi-owm-503 ", 
										 "rain" : "wi-owm-504 ", 
										 "rain-mix" : "wi-owm-511 ", 
										 "showers" : "wi-owm-520 ", 
										 "showers" : "wi-owm-521 ", 
										 "showers" : "wi-owm-522 ", 
										 "storm-showers" : "wi-owm-531 ", 
										 "snow" : "wi-owm-600 ", 
										 "snow" : "wi-owm-601 ", 
										 "sleet" : "wi-owm-602 ", 
										 "rain-mix" : "wi-owm-611 ", 
										 "rain-mix" : "wi-owm-612 ", 
										 "rain-mix" : "wi-owm-615 ", 
										 "rain-mix" : "wi-owm-616 ", 
										 "rain-mix" : "wi-owm-620 ", 
										 "snow" : "wi-owm-621 ", 
										 "snow" : "wi-owm-622 ", 
										 "showers" : "wi-owm-701 ", 
										 "smoke" : "wi-owm-711 ", 
										 "day-haze" : "wi-owm-721 ", 
										 "haze" : "wi-owm-803 ", 
										 "dust" : "wi-owm-731 ", 
										 "fog" : "wi-owm-741 ", 
										 "dust" : "wi-owm-761 ", 
										 "dust" : "wi-owm-762 ", 
										 "cloudy-gusts" : "wi-owm-771 ", 
										 "tornado" : "wi-owm-781 ", 
										 "day-sunny" : "wi-owm-800 ",
										 "clear" : "wi-owm-800 ",
										 "clouds" : "wi-owm-804 ",
										 "cloudy-gusts" : "wi-owm-801 ", 
										 "cloudy-gusts" : "wi-owm-802 ", 
										 "cloudy-gusts" : "wi-owm-803 ", 
										 "cloudy" : "wi-owm-804 ", 
										 "tornado" : "wi-owm-900 ", 
										 "storm-showers" : "wi-owm-901 ", 
										 "hurricane" : "wi-owm-902 ", 
										 "snowflake-cold" : "wi-owm-903 ", 
										 "hot" : "wi-owm-904 ", 
										 "windy" : "wi-owm-905 ", 
										 "hail" : "wi-owm-906 ", 
										 "strong-wind" : "wi-owm-957 ", 
										 "mist" : "wi-owm-803 "
									}
								console.log(weathers+"weathers");
								console.log(weathers[myWeather]+"두번째");
								$('#myWeatherNowIcon').addClass(weathers[myWeather]);
								}
							</script>
						  </div>
						</div>