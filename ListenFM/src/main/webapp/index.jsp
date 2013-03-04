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
         <div id="nav-inner" class="navbar-inner">
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
    <script src="${ctx}/static/js/moment.min.js"></script>


	<!-- 评论弹出框 -->
      <div class="modal hide fade" id="comment-modal">
      	  <a href="" style="display:none" id="comment-area-id"></a>
          <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">×</button>
          <h4>发表评论</h4>
          </div>
          <div class="modal-body">
                <form class="form-horizontal" id="comment-form" action="${ctx}/comment/add" method="post">
                      <input type="hidden" id="comment-input-track-id" name="track.id">
                <fieldset>
                  <div class="control-group error">
                    <label for="input01" class="control-label">邮箱</label>
                    <div class="controls">
                      <input type="text" id="input01" name="email">
                      <span class="help-inline">邮箱不能为空</span>
                    </div>
                  </div>

                  <div class="control-group">
                    <label for="input02" class="control-label">用户名</label>
                    <div class="controls">
                      <input type="text" id="input02" name="name" >
                    </div>
                  </div>

                  <div class="control-group">
                    <label for="textarea" class="control-label">评论内容</label>
                    <div class="controls">
                      <textarea rows="3" id="textarea" name="content"></textarea>
                    </div>
                  </div>

                </fieldset>
              </form>
          </div>
          <div class="modal-footer">
          <a href="javascript:" class="btn btn-primary btn-mini" onclick="postComment();">确定</a>
          <a href="javascript:" class="btn btn-mini" data-dismiss="modal" id="dismiss-comment-modal-btn">取消</a>
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


			/**
			* 歌曲列表。
				1. 展开时的css切换；
				2. 歌曲详情的ajax load；
			*/
			$('.track-list').live('show hidden',function(event){
                $(event.target).parent().toggleClass('white-panel').toggleClass('accordion-group');
            });

			$('*[data-toggle="tooltip"]').tooltip();

            $('.list-track-name a').live('click',function(event){
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

			// jquery nicescroll for the body
			/*$("html").niceScroll({
				autohidemode:false,
				cursoropacitymax:0.8,
				cursorborder:'1px solid #999'
			});
			setInterval(function(){
				$("html").getNiceScroll().resize();
			}, 500);*/
        });

        /**
         	发表评论
        */
        function postComment(){
        	var trackId = $('#comment-input-track-id').val();
			$("#comment-form").ajaxSubmit({
				resetForm:true,
				beforeSubmit:function(){
					// TODO 校验
					//$('#upload-track-form input').attr('disabled','disabled');
					//$('#btn-add-track').addClass('disabled');
					//$('#uploading-tip').text("上传中...").show();
				},
				success:function(data){
					/*$('#upload-track-form input').removeAttr('disabled');
					$('#upload-track-form .file-name').text('');
					$('#btn-add-track').removeClass('disabled');
					$('#uploading-tip').text("上传成功！").show();*/

					var c = eval(data).result;
					$('#dismiss-comment-modal-btn').click();
					var a = $('#'+$('#comment-area-id').attr('href'));

					//已有评论列表
					if(a.children('ul.comment-list').length > 0){
						var n = $(
								'<li class="comment" style="display:none;">'+
						          '<img alt="" src="http://www.gravatar.com/avatar/'+c.emailHash+'/?s=48">'+
						          '<span class="comment-nick">'+c.name+'</span>'+
						          '<span class="comment-post-time">'+moment(c.postTime).format('YYYY/MM/DD HH:mm')+'</span>'+
						          '<div class="comment-content">'+c.content+'</div>'+
						        '</li>'
								);
						a.children('ul.comment-list').prepend(n);
						n.slideDown('1000');
					}
					//没有评论
					else{
						var ele = $(
						'<div class="comment-title">'+
						'<h6>最新评论</h6>'+
						'<button class="btn btn-mini" data-target="#comment-modal" data-toggle="modal" onclick="prepareCommentForm('+trackId+');">发表评论</button>'+
						'</div>'+
						'<ul class="comment-list">'+
						'<li class="comment">'+
				          '<img alt="" src="http://www.gravatar.com/avatar/'+c.emailHash+'/?s=48">'+
				          '<span class="comment-nick">'+c.name+'</span>'+
				          '<span class="comment-post-time">'+moment(c.postTime).format('YYYY/MM/DD HH:mm')+'</span>'+
				          '<div class="comment-content">'+c.content+'</div>'+
				        '</li>'+
						'</ul>'
						).hide();
						a.html('').prepend(ele);
						ele.fadeIn('slow');
					}
				}
			});
        }

        function prepareCommentForm(trackId){
        	$('#comment-input-track-id').val(trackId);
        	$('#comment-area-id').attr('href','comment-area-'+trackId);
        }
    </script>

  </body>

  </body>
</html>
