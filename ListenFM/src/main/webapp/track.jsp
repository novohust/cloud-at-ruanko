<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<div class="track-detail-wrapper">
    <div class="detail-left">
      <img src="${ctx}${track.albumImgUrl}">
      <ul class="album-info">
        <li><label>专辑名称&nbsp;:</label><span class="album-attr-value" title="${track.album}">《${track.album}》</span></li>
        <li><label>歌手&nbsp;:</label><span class="album-attr-value" title="${track.artist}">${track.artist}</span></li>
        <li><label>发行时间&nbsp;:</label><span class="album-attr-value" title='<fmt:formatDate value="${track.releaseDate}"  pattern="yyyy-MM-dd"/>'><fmt:formatDate value="${track.releaseDate}"  pattern="yyyy-MM-dd"/></span></li>
      </ul>
    </div>

    <div class="detail-right" id="comment-area-${track.id}">
      	<c:choose>
      		<c:when test="${latestComments==null||fn:length(latestComments)==0}">
				<div class="comment-tips-wrapper">
                  <div class="comment-tips">这首歌怎么样？快来评论一下吧！</div>
                  <button class="btn btn-primary btn-mini" onclick="prepareCommentForm(${track.id});" data-toggle="modal" data-target="#comment-modal">发表评论</button>
                </div>
      		</c:when>

      		<c:otherwise>
		      <div class="comment-title">
		        <h6>最新评论</h6>
		        <button class="btn btn-mini" onclick="prepareCommentForm(${track.id});" data-toggle="modal" data-target="#comment-modal">发表评论</button>
		      </div>
		      <ul class="comment-list">
		      	<c:forEach var="comment" items="${latestComments}">
			        <li class="comment">
			          <img src="http://www.gravatar.com/avatar/${comment.emailHash}/?s=55" alt="">
			          <span class="comment-nick">${comment.name}</span>
			          <span class="comment-post-time"><fmt:formatDate value="${comment.postTime}"  pattern="yyyy/MM/dd HH:mm"/></span>
			          <div class="comment-content">${comment.content}</div>
			        </li>
		        </c:forEach>
      		</c:otherwise>
      	</c:choose>
      </ul>
    </div>
</div>