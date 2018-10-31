<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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
								<img src="line/line2.png" width="8%" style="margin-top: 5px; position:absolute; left:46.5%">
								<hr style="width: 400px; border: solid 1px #4b4b60;">
								<div class="absolute_ascending" id="ascending_subway">
									파싱행1<br>
									99:99:99
								</div>
								<hr class="absolute_vertical" style="height: 50px; width: 0px; border: solid 1px #4b4b60;">
								<div class="absolute_descending" id="descending_subway">
									파싱행2<br>
									99:99:99
								</div>
						    </div>
						  </div>
						</div>