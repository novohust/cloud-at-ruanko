<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<link href="${ctx}/static/css/index-content.css" rel="stylesheet">
          <!-- image slide show  BEGIN -->
          <div class="white-panel">
            <div id="intro-carousel" class="carousel slide">
                <ol class="carousel-indicators">
                  <li data-target="#intro-carousel" data-slide-to="0" class=""></li>
                  <li data-target="#intro-carousel" data-slide-to="1" class=""></li>
                  <li data-target="#intro-carousel" data-slide-to="2" class="active"></li>
                </ol>
                <div class="carousel-inner">
                  <div class="item">
                    <img src="http://placehold.us/960x250" alt="">
                    <div class="carousel-caption">
                      <h4>First Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                  <div class="item">
                    <img src="http://placehold.us/960x250" alt="">
                    <div class="carousel-caption">
                      <h4>Second Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                  <div class="item active">
                    <img src="http://placehold.us/960x250" alt="">
                    <div class="carousel-caption">
                      <h4>Third Thumbnail label</h4>
                      <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
                    </div>
                  </div>
                </div>
                <a class="left carousel-control" href="#intro-carousel" data-slide="prev">‹</a>
                <a class="right carousel-control" href="#intro-carousel" data-slide="next">›</a>
              </div>
            </div>
          <!-- image slide show  END-->

          <div id="new-songs-panel" class="white-panel">
              <div id="title">
                  <h5>最新音乐</h5>
                  <a href="${ctx}/new">More...</a>
              </div>

              <c:forEach var="track" items="${popularTracks}">
              	 <div class="popular-track" style="">
	                <a href="javascript:void(0)">
	                     <img  src="${ctx}${track.albumImgUrl}"/>
	                </a>
	                <img class="gloss" src="${ctx}/static/img/sheen3.png"/>
	                <a title="播放" href="javascript:void(0);" class="play">播放</a>
	                <label class="album-name">${track.album}</label>
	                <label class="artist">${track.artist}</label>
	              </div>
               </c:forEach>
          </div>
