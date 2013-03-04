<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<div class="containter" id="content-wrapper">
          <div id="category-wrapper">
                  <ul class="nav nav-pills">
                  		<c:forEach var="cate" items="${allCate}" varStatus="idxStatus">
                  			<li <c:if test="${idxStatus.index == 0}">class="active"</c:if>  >
	                        	<a href="javascript:" onclick="getLatestTracksByCate(event,${cate.id});">${cate.name}</a>
	                        </li>
                  		</c:forEach>
                  </ul>
          </div>

          <div id="new-songs-cate">
                  <h5>最新音乐</h5>
                  <button class="btn btn-mini  btn-info"><i class="icon-play icon-white"></i>&nbsp;播放所有歌曲</button>
                  <button class="btn btn-mini"><i class="icon-plus"></i>&nbsp;加入播放列表</button>

                  <div id="latest-track-list" class="accordion track-list">
                  	<jsp:include page="/track/latest/${allCate[0].id}" flush="true" />
          		  </div>


          </div>
          <script type="text/javascript">
	        function getLatestTracksByCate(event,cateId){
	        	if($(event.target).parent().is(".active"))
	        		return;
	        	$('#category-wrapper li').removeClass("active");
	        	$(event.target).parent().addClass("active");
	        	$("#latest-track-list").load(ctx+"/track/latest/"+cateId);
	        }
	    </script>