<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <title>Cloud|在云中</title>
    <link href="${ctx}/static/css/bootstrap-readable.css" rel="stylesheet">
    <link href="${ctx}/static/css/cloud-global.css" rel="stylesheet">


    <!-- Le styles -->


    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="${ctx}/static/ico/favicon.png">

  </head>

  <body>
	<a style="display:none" id="context-path" href="${ctx}"></a>

	 <!-- 上导航条  begin -->
      <div id="nav-top" class="navbar navbar-inverse">
         <div class="navbar-inner">
           <div class="container">
            <!-- <a id="logo" href="#"><img src="static/img/logo.png"></a> -->
             <a class="brand" href="${ctx}">Cloud | 在云中</a>
             <div class="nav-collapse collapse" id="main-menu">
              <form class="navbar-search pull-right" action="">
                <input type="text" class="search-query span2" placeholder="Search">
              </form>

              <ul class="nav pull-right" id="main-menu-right">
                <li><a href="${ctx}/index-content">首页</a></li>
                <li><a href="${ctx}/latest">最新音乐</a></li>
                <li><a href="${ctx}/category">歌曲分类</a></li>
                <li><a href="${ctx}/popular">排行榜</a></li>
              </ul>
             </div>
           </div>
         </div>
       </div>
    <!-- 上导航条  end -->

    <div class="containter" id="content-wrapper">

    </div>


    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->

    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/jquery.rotate.js"></script>
    <script src="${ctx}/static/js/jquery.jplayer.min.js"></script>
    <script src="${ctx}/static/js/jquery.grab.js"></script>
    <script src="${ctx}/static/js/zen.js"></script>
    <script src="${ctx}/static/js/jquery-ui-slider.js"></script>
    <script src="${ctx}/static/js/jquery.form.js"></script>
    <script src="${ctx}/static/js/jquery.nicescroll.min.js"></script>


	<!-- 评论弹出框 -->
      <div class="modal hide fade" id="comment-modal">
          <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">×</button>
          <h4>发表评论</h4>
          </div>
          <div class="modal-body">
                <form class="form-horizontal">
                <fieldset>
                  <div class="control-group error">
                    <label for="input01" class="control-label">邮箱</label>
                    <div class="controls">
                      <input type="text" id="input01" >
                      <span class="help-inline">邮箱不能为空</span>
                    </div>
                  </div>

                  <div class="control-group">
                    <label for="input01" class="control-label">用户名</label>
                    <div class="controls">
                      <input type="text" id="input01" >
                    </div>
                  </div>

                  <div class="control-group">
                    <label for="textarea" class="control-label">评论内容</label>
                    <div class="controls">
                      <textarea rows="3" id="textarea" ></textarea>
                    </div>
                  </div>

                </fieldset>
              </form>
          </div>
          <div class="modal-footer">
          <a href="#" class="btn btn-mini" data-dismiss="modal">取消</a>
          <a href="#" class="btn btn-primary btn-mini">确定</a>
          </div>
      </div>

    <script type="text/javascript">
       	var ctx = $("#context-path").attr('href');
        $(function(){
        	var contentWrapper = $("#content-wrapper");
			$("#main-menu-right a").click(function(event){
				event.preventDefault();
				$("#main-menu-right li").removeClass('active');
				$(this).parent().addClass("active");
				$("#content-wrapper").load($(this).attr('href'));
        	});

			// jquery nicescroll for the body
			$("html").niceScroll({
				autohidemode:false,
				cursorborder:'1px solid #666'
			});
			setInterval(function(){
				$("html").getNiceScroll().resize();
			}, 500);
        });
    </script>

  </body>

  </body>
</html>
