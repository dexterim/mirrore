<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<script>
<!--$(function(){$('#subway_loc').text('2호선 왕십리');});-->
$(document).ready(function(){
      console.log('지하철 데이터 호출');
        subway_task.getSubway();
});
var subway_task = {
      getSubway : function() {
         $.ajax({
               type:"GET",
               url:"http://openapi.seoul.go.kr:8088/515047645379616e39385a67724e65/json/SearchArrivalInfoByIDService/1/5/1003/1/3",
               success:function(json){
                  var json_string = JSON.stringify(json);
                  console.log("json_string: "+json_string)
                   var list = JSON.parse(json);
                   console.log(list[''])
                   
                   console.log('subway_contentStr'+contentStr);
                   $("#ascending_subway").append(json);
                   $("#descending_subway").append(contentStr);
               }
           
           });
      }
}

</script>
						<!-- subway_tiles(ui/subway.jsp) -->
						<div class="col-lg-4" style="position:absolute;left:0px">
						  <div class="card tilesSubway" style="display:none;">
						    <div class="card-header ">
						      <h5 class="card-category"></h5>
						      <h3 class="card-title"></h3>
						    </div>
						    <div class="card-body ">
						    	<div class="text-center css01">
								  	<img src="line/web_subway_line.png" alt="subway" width="100%" id="subway_line">
								</div>
								<div class="absolute_main" id="subway_loc">
									2호선 왕십리
								</div>
								<div class="absolute_pre" id="pre_subway_loc">
									상왕십리
								</div>
								<div class="absolute_next" id="next_subway_loc">
									한양대
								</div>
								<!-- <img src="line/line2.png" width="8%" style="margin-top: 5px; position:absolute; left:46.5%">-->
								<hr style="width: 400px; border: solid 1px #4b4b60;">
								<div class="absolute_ascending" id="ascending_subway">
									성수행<br>
									4시 40분<br>
									4시 43분
								</div>
								<hr class="absolute_vertical" style="height: 50px; width: 0px; border: solid 1px #4b4b60;">
								<div class="absolute_descending" id="descending_subway">
									성수행<br>
									4시 48분<br>
									4시 54분
								</div>
						    </div>
						  </div>
						</div>