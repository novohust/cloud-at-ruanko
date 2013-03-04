<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<div class="containter" id="content-wrapper">
          <div id="category-wrapper">
                  <ul class="nav nav-pills">
                  		<c:forEach var="cate" items="${allCate}" varStatus="idxStatus">
                  			<li <c:if test="${idxStatus.index == 0}">class="active"</c:if>  >
	                        	<a href="javascript:" onclick="getLatestTracksByCate(${cate.id});">${cate.name}</a>
	                        </li>
                  		</c:forEach>
                  </ul>
          </div>

          <div id="new-songs-cate" >
                  <h5>最新音乐</h5>
                  <button class="btn btn-mini  btn-info"><i class="icon-play icon-white"></i>&nbsp;播放所有歌曲</button>
                  <button class="btn btn-mini"><i class="icon-plus"></i>&nbsp;加入播放列表</button>

                  <div id="accordion2" class="accordion track-list">
                  <c:forEach var="track" items="${latestTracks}" varStatus="trackStatus">
                        <div class="accordion-group">
                          <div class="accordion-heading">
                            <ul class="list-ul">
                              <li class="list-no">${trackStatus.count}</li>
                              <li class="list-track-name">
                                <a href="#collapse-${trackStatus.index}" data-toggle="collapse" track-id="${track.id}">
                                  ${track.name}
                                </a>
                              </li>
                              <li class="list-artist">${track.artist}</li>
                              <li class="list-album">《${track.album}》</li>
                              <li class="list-uploadtime"><fmt:formatDate value="${track.uploadTime}"  pattern="yyyy/MM/dd HH:mm"/></li>
                              <li class="list-action">
                                  <a href="javascript:" title="播放"><i class="icon-play"></i></a>
                                  <a href="javascript:" title="加入播放列表"><i class="icon-plus" ></i></a>
                                  <a href="javascript:" title="下载"><i class="icon-download" ></i></a>
                              </li>
                            </ul>
                          </div>
                          <div class="accordion-body collapse" id="collapse-${trackStatus.index}" >
                            <div class="accordion-inner">
                            </div>
                          </div>
                        </div>
                        </c:forEach>
          </div>


          </div>
          <script type="text/javascript">
	        $(function(){
	            $('#accordion2').on('show hidden',function(event){
	                $(event.target).parent().toggleClass('white-panel').toggleClass('accordion-group');
	            });
	            $('a[title]').tooltip();

	            $('.list-track-name a').click(function(event){
	            	event.stopPropagation();
	            	event.preventDefault();
	            	var collapseBody = $($(this).attr('href'));
	            	if(collapseBody.height()>0){
	            		collapseBody.collapse('hide');
	            	}else{
		            	collapseBody.find('.accordion-inner').load(ctx+'/track/'+$(this).attr('track-id'),{},function(){
		            		collapseBody.collapse('show');
		            	});
	            	}
	            });
	        });
	    </script>