<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>


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
                            	<!-- ajax load track.jsp -->
                            </div>
                          </div>
                        </div>
                        </c:forEach>


