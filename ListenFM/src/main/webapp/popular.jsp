<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<style type="text/css">
        .list-ul .list-action{
          width: 60px;
        }
        .list-ul .list-count{
          margin-right: 0;
          padding: 0 0 0 50px;
          width: 200px;
        }

      .count-bar{
          -moz-box-sizing: border-box;
          background-color: #0E90D2;
          background-image: linear-gradient(center top , #149BDF, #0480BE);
          background-repeat: repeat-x;
          border-radius: 0 9px 9px 0;
          box-shadow: 0 -1px 0 rgba(0, 0, 0, 0.15) inset;
          color: #FFFFFF;
          display: inline-block;
          font-size: 12px;
          height: 18px;
          line-height: 18px;
          padding-left: 3px;
          text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
          transition: width 0.6s ease 0s;
          width: 100%;
        }
    </style>
<h5>下载排行</h5>
  <button class="btn btn-mini  btn-info"><i class="icon-play icon-white"></i>&nbsp;播放所有歌曲</button>
  <button class="btn btn-mini"><i class="icon-plus"></i>&nbsp;加入播放列表</button>

  <div id="" class="accordion track-list">
  	<jsp:include page="/track/top-download-track-list" flush="true" />
  </div>

<!-- 来个分割线？？ -->
<h5>试听排行</h5>
  <button class="btn btn-mini  btn-info"><i class="icon-play icon-white"></i>&nbsp;播放所有歌曲</button>
  <button class="btn btn-mini"><i class="icon-plus"></i>&nbsp;加入播放列表</button>

  <div id="" class="accordion track-list">
  	<jsp:include page="/track/top-play-track-list" flush="true" />
  </div>