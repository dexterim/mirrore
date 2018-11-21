<%@ page language="java" contentType="text/html; charset=UTF-8" %>

						<!-- content_tiles(ui/content.jsp) -->
						<script>
						/* setTimeout(welcomeUser.checkState(), 5000);
						var welcomeUser = {
							checkState : function() {
								$.ajax({
									url: 'checkState.do',
									success: function (data) {
										if (data == 'login') {
											$('#welcomeUser').val('hello');
											location.href('enocreWeb.do')
										} else {
											
										}
									}
								});
							}
						};
						function checkData(){
						$.ajax({ 
						        url: 'CheckIfDataExists/' + 
						                         new Date().getTime(),
						        success: function (response) {
						            if (response == 'True') {                     
						                $('.DataDiv')
						                      .load('GetFreshData/' + new Date()
						                          .getTime(), { "Id": $("#RowID").val() });
						            } else {
						                $('.OutOfWindow').html('No Data Found');
						                setTimeout(function () { checkData() }, 5000);
						            }
						        }, 
						        complete: function () { 
						           // clearTimeout(sTimeOut); 
						        } 
						    }); 
						} */
						
						/* 따로 뺄 것 : welcome_text_animation.js*/
						/* $(document).ready(function () {
							var welcomeUserText = "Hello, <c:out value='${userName}'/>";
							var welcomeText = $('#animationSandbox');
							$('#welcomeUser').text(welcomeUserText);
							welcomeText.addClass('animated zoomIn');
						    setTimeout(function () {
						    	welcomeText.fadeOut();
						    }, 2000 );
						});
						 */
						</script>
						<div class="col-lg-4" style="position:absolute;right:400px">
						   <div class="card">
						     <div class="card-header">
						       <h4 class="card-title"></h4>
						     </div>
						     <div class="card-body">
								<div class="wrap">
									<div id="animationSandbox" style="display: block;" class="">
										<div id="welcomeUser" style="color:#fff; font-size: 4.2em"></div>
								        <div id="output"></div>
								        <div id="voice"></div>
									</div>
								</div>
						     </div>
						   </div>
						 </div>