/*
 *
 * ZEN - HTML5-CSS3 Audio Player
 * by @simurai (simurai.com)
 *
 * Most of this code by: @maboa (happyworm.com)
 * and: @quinnirill (niiden.com/jussi/)
 *
 */

var zen_player;
function initZenPlayer(readyFunc) {

			var status = "stop";
			var dragging = false;

			// init

			var player = $("#zen .player");

			player.jPlayer({
				preload:'auto',
				solution:"flash,html",
				swfPath : $('#context-path').attr('href') + "/static/js",
				supplied : "mp3",
				ready:readyFunc
			});

			// preload, update, end

			player.bind($.jPlayer.event.play,function(event){
				// 重置buffer
				if(status != "play"){
					status = "play";
					// 点击"播放"按钮的动画
					$("#zen").addClass("play");
				}
			});

			// 播放已缓存的文件会触发一个progress事件，进度为100(flash),html5则会一直触发
			player.bind($.jPlayer.event.progress, function(event) {
				var sp = getSeekPercent(event);
				if(isNaN(sp))
					return;
				//console.log("seekable percent:"+sp);
				if (sp != 0) {
					displayBuffered(sp);
					// 缓冲完毕，去掉loaded样式
					if (sp >= 99) {
						 $('#zen .buffer').addClass("loaded");
					}
				}
			});

			// may be NaN
			function getSeekPercent(event){
				var sp;
				var audio = $('#zen audio').get(0);
				if(audio){	// html5
					if ((audio.buffered == undefined) || (audio.buffered.length == 0)){
						return;
					}
					sp = parseInt(((audio.buffered.end(0) / audio.duration) * 100), 10);
				}else{	// flash
					sp = event.jPlayer.status.seekPercent;
				}
				return sp;
			}

//			 player.bind($.jPlayer.event.loadeddata, function(event) {
//				 console.log("loaded event fireed!");
//				 $('#zen .buffer').addClass("loaded");
//			 });

			player.bind($.jPlayer.event.timeupdate, function(event) {
				var pc = event.jPlayer.status.currentPercentAbsolute;
				var sp = getSeekPercent(event);
				// 有时播放进度条会超前于缓冲进度条，当缓冲事件触发不及时时
				if(!isNaN(sp) && sp < pc)
					displayBuffered(pc);
				if (!dragging) {
					displayProgress(pc);
				}
			});

			player.bind($.jPlayer.event.ended, function(event) {
				$('#zen .circle').removeClass("rotate");
				$("#zen").removeClass("play");
				$('#zen .progress').css({
					rotate : '0deg'
				});
				status = "stop";
				next({'animate':true});
			});

			// play/pause
			// $("#zen .button").click(onClick);

			$("#zen .button").bind('mousedown', function() {
				// not sure if this can be done in a simpler way.
				// when you click on the edge of the play button, but button
				// scales down and doesn't drigger the click,
				// so mouseleave is added to still catch it.
				$(this).bind('mouseleave', function() {
					$(this).unbind('mouseleave');
					onClick();
				});
			});

			$("#zen .button").bind('mouseup', function() {
				$(this).unbind('mouseleave');
				onClick();
			});

			function onClick() {
				if (status != "play") {
					status = "play";
					$("#zen").addClass("play");
					player.jPlayer("play");
				} else {
					$('#zen .circle').removeClass("rotate");
					$("#zen").removeClass("play");
					status = "pause";
					player.jPlayer("pause");
				}
			}

			// draggin

			var clickControl = $('#zen .drag');

			clickControl.grab({
				onstart : function() {
					dragging = true;
					$('#zen .button').css("pointer-events", "none");

				},
				onmove : function(event) {
					var pc = getArcPc(event.position.x, event.position.y);
//					player.jPlayer("playHead", pc).jPlayer("play");
					displayProgress(pc);
				},
				onfinish : function(event) {
					dragging = false;
					var pc = getArcPc(event.position.x, event.position.y);
					displayProgress(pc);
					player.jPlayer("playHead", pc);
					$('#zen .button').css("pointer-events", "auto");
				}
			});

			// functions

			function displayProgress(pc) {
				var degs = pc * 3.6 + "deg";
				$('#zen .progress').css({
					rotate : degs
				});
			}

			function displayBuffered(pc) {
				var degs = pc * 3.6;
				$('#zen .buffer').stop().animate({
					rotate : degs + 'deg'
				},'slow');
			}

			function getArcPc(pageX, pageY) {
				var self = clickControl, offset = self.offset(), x = pageX
						- offset.left - self.width() / 2, y = pageY
						- offset.top - self.height() / 2, a = Math.atan2(y, x);

				if (a > -1 * Math.PI && a < -0.5 * Math.PI) {
					a = 2 * Math.PI + a;
				}

				// a is now value between -0.5PI and 1.5PI
				// ready to be normalized and applied
				var pc = (a + Math.PI / 2) / 2 * Math.PI * 10;

				return pc;
			}

			zen_player = player;
}