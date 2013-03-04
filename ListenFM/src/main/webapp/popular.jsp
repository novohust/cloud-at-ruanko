<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <title>Listen</title>
    <link href="${ctx}/static/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/css/zen.css" rel="stylesheet">
    <link href="${ctx}/static/css/listen-global.css" rel="stylesheet">

    <!-- Le styles -->


    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="${ctx}/static/ico/favicon.png">

  </head>

  <body>

	<div id="header-wrapper">
	    <div class="header">
	    </div>
	    <div id="popup-wrapper">
	    	<div id="popup">
	    	<!-- 上传表单 -->
	    	<form class="form-horizontal" style="margin:10px 0;" name="track"
	    	action="${ctx}/track/add" method="post" enctype="multipart/form-data" id="upload-track-form">
		        <fieldset>
		       	 <div class="control-group">
		            <label class="control-label" for="input-track-name">歌曲名称</label>
		            <div class="controls">
		              <input type="text" class="input-large" id="input-track-name" name="name">
		            </div>
		          </div>
		          <div class="control-group">
		            <label class="control-label">音频文件</label>
		            <div class="controls">
		              <a href="javascript:void(0)" class="btn btn-small btn-file">选择文件<input type="file" name="mp3File"></a>
		              <span class="file-name"></span>
		              <p class="help-block" style="color:#ddd;font-size:11px;">请选择音频文件，目前仅支持mp3格式.</p>
		            </div>
		          </div>
		          <div class="control-group">
		            <label class="control-label">所属频道</label>
		            <div class="controls">
		              <select id="select01" name="channel.id">
		                <c:forEach var="channel" items="${channels}">
		                	<option value="${channel.id}">${channel.name}</option>
		                </c:forEach>
		              </select>
		            </div>
		          </div>
		          <div class="control-group">
		            <label class="control-label" for="input-singer">歌手</label>
		            <div class="controls">
		              <input type="text" class="input-large" id="input-singer" name="singer">
		            </div>
		          </div>
		          <div class="control-group">
		            <label class="control-label" for="input-album">专辑名</label>
		            <div class="controls">
		              <input type="text" class="input-large" id="input-album" name="albumName">
		            </div>
		          </div>
		          <div class="control-group">
		            <label class="control-label">专辑图片</label>
		            <div class="controls">
		              <a href="javascript:void(0)" class="btn btn-small btn-file">选择文件<input type="file" name="imgFile"></a>
		              <span class="file-name"></span>
		              <p class="help-block" style="color:#ddd;font-size:11px;">图片最佳尺寸为300*300像素，请尽量保证图片为正方形.</p>
		            </div>
		          </div>
		          <div class="form-actions" style="background:none;border-top:none;text-align:center;padding-left:0;position:relative;">
		            <button id="btn-add-track" class="btn btn-success">GO !</button>
		            <label onmouseenter="event.stopPropagation();" style="display:none;" id="uploading-tip">上传中...</label>
		          </div>
		        </fieldset>
		      </form>
	    	</div>
	    	<div id="popup-handler" class="shadowed">
	    		<i class="icon-chevron-down icon-white"></i>
	    	</div>
	    </div>
	</div>

    <div class="container" id="wrapper">
      <div id="logo-wrapper">
	      <div class="logo center">
	        <a href="javascript:void(0)"><img src="${ctx}/static/img/headphones.png"></a>
	        <span class="logo-text">Listen</span>
	      </div>

	      <ul id="channel-menu" class="nav nav-pills">
                  <li id="fat-menu" class="dropdown">
                    <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" id="cur-channel-a">随机 <b class="caret"></b></a>
                    <ul class="dropdown-menu" style="right:0;left:inherit;min-width:78px;">
                      <li>
                      <a href="javascript:void(0)" value="-1" id="random-channel">随机</a></li>
                      	<c:forEach var="channel" items="${channels}">
	                      <li><a class="channel-menu"  href="javascript:void(0)" value="${channel.id}">${channel.name}</a></li>
		                </c:forEach>
                    </ul>
                  </li>
           </ul>

	      <div class="logo-shadow"></div>
      </div>
      <div class="songs">
        <!-- song info -->
        <div id="song-info">
          <div><span id="song-name">最炫民族风</span></div>
          <div class="song-singer">歌手：<span id="singer">周杰伦</span></div>
          <div id="song-album-wrapper"><a target="_blank" style="display:inline-block;" id="song-album-a" href="javascript:void(0)">苍茫的草原是我的</a><span id="song-album-span" style="display:none"></span></div>
          <div id="publisher-wrapper">©<span id="release-date">2011-5-12</span><span id="publisher">新蜂音乐</span></div>
        </div>

        <!-- 历史记录 -->
        <div class="history-bar">
          <div id="history-inner">
          </div>
        </div>

        <!-- 当前听的歌 -->
        <div id="cur-song-wrapper" class="current-song current-song-loading">
          <img id="loading" src="${ctx}/static/img/loading.gif"/>
          <img id="cur-song-img" style="display:none"/>
        </div>
      </div>

      <div class="player center">
        <div id="player-inner">
          <a href="javascript:void(0)" id="next" title="下一首">
            <img  src="${ctx}/static/img/media_next.png">
          </a>
          <div id="zen">
            <span class="player"></span>
            <span class="circle"></span>
            <span class="progress"></span>
            <span class="buffer"></span>
            <span class="drag"></span>
            <div class="button">
            <span class="icon play"></span>
            <span class="icon pause"></span>
            </div>
          </div>
          <a href="javascript:void(0)" id="volume-icon" title="音量">
            <img  src="${ctx}/static/img/media-volume-2.png">
          </a>
          <div id="volume">
            <span id="volume-tooltip" style="display: none; left: 4px;">1</span> <!-- Tooltip -->
            <div id="volume-slider" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all">
              <div class="ui-slider-range ui-widget-header ui-slider-range-min" style="width: 0%;"></div>
              <a href="javascript:void(0)" class="ui-slider-handle ui-state-default ui-corner-all" style="left: 0%;"></a>
            </div> <!-- the Slider -->
          </div>
      </div>

      </div>

    </div>


   <!-- Footer ================================================== -->
    <!--<footer>
      <div id="footer-inner">
        <p id="logo-foot">
          <span><strong>X</strong>browser</span>
          Free Your Hands.
        </p>

        <ul id="footer-nav">
          <li><a href="javascript:void(0)">关于</a></li>
          <li><a href="javascript:void(0)">联系我们</a></li>
          <li><a href="javascript:void(0)">盖娅</a></li>
          <li><a href="javascript:void(0)">倚天</a></li>
        </ul>

        <p>Copyright &copy; 2009-2012 Alibaba. All rights reserved &copy; Alibaba-inc.com.</p>
      </div>
    </footer>-->



	<a style="display:none" id="next-n-songs-url" href="${ctx}/track/random/"></a>
	<a style="display:none" id="context-path" href="${ctx}"></a>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->

    <script src="${ctx}/static/js/jquery.js"></script>
    <script src="${ctx}/static/js/bootstrap-tooltip.js"></script>
    <script src="${ctx}/static/js/bootstrap-dropdown.js"></script>
    <script src="${ctx}/static/js/jquery.rotate.js"></script>
    <script src="${ctx}/static/js/jquery.jplayer.min.js"></script>
    <script src="${ctx}/static/js/jquery.grab.js"></script>
    <script src="${ctx}/static/js/zen.js"></script>
    <script src="${ctx}/static/js/jquery-ui-slider.js"></script>
    <script src="${ctx}/static/js/jquery.form.js"></script>

    <script type="text/javascript">

      // 加载当前歌曲的图片
      function loadCurTrackImg(img){
        if(img === $('#cur-song-img').attr('src'))
          return;
        $('#loading').css('display','inline-block');
        $('#cur-song-wrapper').toggleClass('current-song-loading',true);
        $('#cur-song-img').hide().attr('src',img);
      }

      //

      String.prototype.startWith=function(str){
    	  var reg=new RegExp("^"+str);
    	  return reg.test(this);
      }

      var songs;
	  var curSong;
	  var length = 10;
      var aSongListendWidth = 140; //width:100px,padding:40px
      var contextPath;
      /*
      	从songs里消费一首歌，更新ui。
      */
      function next(options){
    	  //disable掉下一首按钮
		  $('#next').attr('disabled',true);
    	  options = options?options:{};
    	  var next = songs.shift();
    	  // 如果没有，重新请求一批
    	  if(!next){
    		  getNextNSongs(length);
    		  return;
    	  }
    	  // 设置歌曲文字信息
    	  $('#song-name').text(next.name);
    	  $('#singer').text(next.singer);
		  if(next.albumDoubanInfo){
	    	  $('#song-album-a').attr('href',next.albumDoubanInfo.link).text(next.albumName).attr('display','inline-block').show();
	    	  $('#song-album-span').hide();

	    	  $('#publisher-wrapper').show();
	    	  $('#release-date').text(next.albumDoubanInfo.releaseDate);
	    	  $('#publisher').text(next.albumDoubanInfo.publisher);
		  }else{
			  $('#song-album-span').text(next.albumName).show();
			  $('#song-album-a').hide();
	    	  $('#publisher-wrapper').hide();
		  }
    	  $('#song-info').show();
    	  // 加载下一张图片
    	  loadCurTrackImg(next.img);
    	  // 重置进度条
    	  $('#zen .buffer').stop().css({
			rotate : '0deg'
		  }).removeClass('loaded');
    	  // 播放歌曲
    	  var url = next.mp3.startWith('http')?next.mp3:contextPath + '/songs' + next.mp3;
    	  zen_player.jPlayer("clearMedia");
    	  zen_player.jPlayer("setMedia", {'mp3' : url});
    	  zen_player.jPlayer('play');
    	  //zen_player.jPlayer("setMedia", {mp3 : 'http://ftp.luoo.net/radio/radio21/03.mp3'}).jPlayer('play');
    	  // 如果是最初始状态，不需要动画，直接返回。
    	  if(curSong == undefined){
	    	  curSong = next;
	    	  $('#next').attr('disabled',false);
			  return;
    	  }

		  // 在history bar中添加一首历史歌曲
		  var historyInner = $('#history-inner');
          buildListenedDom(curSong).appendTo(historyInner).fadeIn();
		  // history bar左移
          historyInner.animate({'margin-left':(parseFloat(historyInner.css('margin-left'))-aSongListendWidth)+'px'},options.animate?'medium':0,function(){
            var historyList = historyInner.find(".song-listened");
            // 最多只有4首，多出来的得删掉
            if(historyList.length > 4){
              $(historyList.first()).remove();
              historyInner.css({'margin-left':(parseFloat(historyInner.css('margin-left'))+aSongListendWidth)+'px'});
            }
            // 动画结束，enable下一首按钮
            $('#next').attr('disabled',false);
          });

		  curSong = next;

		  // update web storage
			if(window.localStorage){
				var state = $.parseJSON(window.localStorage.getItem(stateKey));
				if(!state || state.length == 0){
					window.localStorage.setItem(stateKey,JSON.stringify([curSong]));
					return;
				}
				if(state.length == 5){
					state.shift();
				}
				state.push(curSong);
				window.localStorage.setItem(stateKey,JSON.stringify(state));
			}
      }

      function buildListenedDom(listened){
    	// 专辑图片加载完了再显示，否则显示一个背景图
    	  var div = $('<div class="song-listened" style="display:none">'
            		+'<a href="javascript:void(0)">'
            		+'<img src="'+contextPath+'static/img/music_holder.png"/>'
            		+'</a>'
            		+'<span class="gloss" title="曲目：'+listened.name+'<br/>歌手：'+listened.singer+'<br/>专辑：'+listened.albumName+'"/>'
            		+'</div>')
  		  $('<img style="display:none"/>').appendTo(div.find('a')).bind('load',function(){
  			$(this).prev().hide();
  			$(this).fadeIn();
  		  }).attr('src',listened.img);
    	  div.find(".gloss").tooltip();
    	  return div;
      }

      // 换一批歌曲，并自动切歌
      var curChannel;
      function getNextNSongs(n){
		$.get($('#next-n-songs-url').attr('href') +''+n,(curChannel && curChannel > 0)?{'channelId':curChannel}:'',function(data){
			if(!data.errorMsg){
				songs = data.result;
				next({'animate':true});
			}
		});
      }

      // restore from the web storage
      var stateKey = 'listen-state';
      var channelKey = 'channel-listened';
      function restore(){
		if(!window.localStorage || !window.localStorage.getItem(stateKey))
			return false;
		var state = $.parseJSON(window.localStorage.getItem(stateKey));
		// 每次切换到下一首歌时更新webstorage到最后
		var c = state.pop();
		var restored = true;
		if(c){
			songs = [c];
			next({animate:true});
			$(state).each(function(i,n){
				var historyInner = $('#history-inner');
				buildListenedDom(n).appendTo(historyInner).show() ;
				historyInner.css({'margin-left':(parseFloat(historyInner.css('margin-left'))-aSongListendWidth)+'px'});
			});
			var channelLastTime = parseInt(window.localStorage.getItem(channelKey));
			if(isNaN(channelLastTime)){
				return;
			}
			curChannel = channelLastTime;
			var li = $('#channel-menu .dropdown-menu li a[value='+channelLastTime+']');
			$('#cur-channel-a').html(li.text()+' <b class="caret"></b>');
		}else{
			restored = false;
		}
		return restored;
      }

      $(function(){
    	contextPath = $('#context-path').attr('href');
    	curChannel = $('#random-channel').attr('value');


    	initZenPlayer(function(){
    		// 还原现场
			if(restore())
				return;
    		// 初始化，拿n首歌曲
            getNextNSongs(length);
    	});

        $('#cur-song-img').bind('load',function(){
          $('#loading').hide();
          $('#cur-song-wrapper').toggleClass('current-song-loading',false);
          $('#cur-song-img').fadeIn();
        });

        $('.gloss').tooltip().live({
          'mouseenter':function(){
            $(this).prev().fadeTo('fast',1);
          },'mouseleave':function(){
            $(this).prev().fadeTo('fast',0.75);
          }
        });

        $('#player-inner>a')
        	.tooltip()
        	.bind({
          'mouseenter':function(){
            $(this).fadeTo('fast',1);
          },'mouseleave':function(){
            $(this).fadeTo('fast',0.75);
          }});


        //下一首
        $('#next').click(function(){
        	if(!$(this).attr('disabled'))
        		next({'animate':true});
        });

        // volume slider
        var slider = $('#volume-slider'),tooltip = $('#volume-tooltip').hide();
        function sliderFunc(event, ui) {
            var value = slider.slider('value'),volume=$("#volume-icon").find('img');
            tooltip.css('left', value).text(ui.value);
            console.log(value/100.0);
            zen_player.jPlayer('volume',value/100.0);
            if(value <= 5) {
                //volume.attr('src','http://cdn1.iconfinder.com/data/icons/defaulticon/icons/png/48x48/media-volume-0.png');
            }
            else if (value <= 25) {
                volume.attr('src',contextPath+'/static/img/media-volume-1.png');
            }
            else if (value <= 75) {
                volume.attr('src',contextPath+'/static/img/media-volume-2.png');
            }
            else {
                volume.attr('src',contextPath+'/static/img/media-volume-3.png');
            };
        }

        slider.slider({
            range: "min",
            min: 1,
            value: 35,
            start: function(event,ui) {
              tooltip.fadeIn('fast');
            },
            slide: sliderFunc,
            stop: function(event,ui) {
              sliderFunc(event,ui);
              tooltip.fadeOut('fast');
            }
        });

        // popup init
        $('#popup-handler').click(function(){
        	var top = parseInt($('#popup-wrapper').css('top'));
        	var height = parseInt($('#popup').css('height')) - 3;
        	$('#popup-wrapper').animate({'top':(top<0?0: (-1 * height) + 'px')},'fast',function(){
        		$('#popup').toggleClass('shadowed');
        	});
        	$('#popup-handler').find('i').toggleClass('icon-chevron-down').toggleClass('icon-chevron-up');
        });

        // upload init

        $('#upload-track-form input[type=file]').bind('change',function(event){
        	var fileName = $(this).val().split("\\").pop();
        	var s = $(this).parent().next().text(fileName);
        	s.css({'display':'inline-block'}).attr("data-original-title",fileName).tooltip({'placement':'right'});
        });

        $('#btn-add-track').click(function(){
			$('#upload-track-form').ajaxForm({
				resetForm:true,
				beforeSubmit:function(){
					// TODO 校验
					$('#upload-track-form input').attr('disabled','disabled');
					$('#btn-add-track').addClass('disabled');
					$('#uploading-tip').text("上传中...").show();
				},
				success:function(data){
					$('#upload-track-form input').removeAttr('disabled');
					$('#upload-track-form .file-name').text('');
					$('#btn-add-track').removeClass('disabled');
					$('#uploading-tip').text("上传成功！").show();
				}
			});
        });

        // channel menu init
        $('#channel-menu .dropdown-menu li a').click(function(){
        	$('#cur-channel-a').html($(this).text()+' <b class="caret"></b>');
        	var channel = $(this).attr('value');
        	if(channel == curChannel)
        		return;
        	if(window.localStorage){
        		window.localStorage.setItem(channelKey,channel);
        	}
        	curChannel = channel;
        	getNextNSongs(length);
        });

      });
    </script>

  </body>
</html>
