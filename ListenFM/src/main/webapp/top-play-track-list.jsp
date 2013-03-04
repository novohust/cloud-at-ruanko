<%@page import="org.hustsse.cloud.entity.Track"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

				<%
					// 得到最多下载次数
					List<Track> tracks = (List<Track>)request.getAttribute("topPlayTrackList");
					if(tracks == null || tracks.size() == 0)
						return;
					request.setAttribute("maxPlayTimes", tracks.get(0).getPlayTimes());
				%>

                   <c:forEach var="track" items="${topPlayTrackList}" varStatus="trackStatus">
                        <div class="accordion-group">
                          <div class="accordion-heading">
                            <ul class="list-ul">
                              <li class="list-no">${trackStatus.count}</li>
                              <li class="list-track-name">
                                <a href="#top-play-collapse-${trackStatus.index}" data-toggle="collapse" track-id="${track.id}">
                                  ${track.name}
                                </a>
                              </li>
                              <li class="list-artist">${track.artist}</li>
                              <li class="list-album">《${track.album}》</li>
                              <li class="list-action">
                                  <a href="javascript:" data-toggle="tooltip" title="播放"><i class="icon-play"></i></a>
                                  <a href="javascript:" data-toggle="tooltip" title="加入播放列表"><i class="icon-plus" ></i></a>
                                  <a href="javascript:" data-toggle="tooltip" title="下载"><i class="icon-download" ></i></a>
                              </li>
                              <li class="list-count">
                                <div class="count-bar"  title="${track.playTimes}次试听" data-placement="right" data-toggle="tooltip"
                                style="width:<fmt:formatNumber value="${track.playTimes/maxPlayTimes}" type="number" pattern="0.00%"/>"
                                >${track.playTimes}</div>
                              </li>
                            </ul>
                          </div>
                          <div class="accordion-body collapse" id="top-play-collapse-${trackStatus.index}" >
                            <div class="accordion-inner">
                            	<!-- ajax load track.jsp -->
                            </div>
                          </div>
                        </div>
                        </c:forEach>
			<script type="text/javascript" src="${ctx}/static/js/track-list-common.js"></script>

