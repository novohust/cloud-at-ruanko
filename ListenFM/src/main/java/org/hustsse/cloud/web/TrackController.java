package org.hustsse.cloud.web;

import java.util.List;

import org.hustsse.cloud.entity.Comment;
import org.hustsse.cloud.entity.Track;
import org.hustsse.cloud.service.CommentService;
import org.hustsse.cloud.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/track")
public class TrackController {

	@Autowired
	private TrackService trackService;
	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/random/{num}")
	@ResponseBody
	public AjaxResult getRandomTracks(@PathVariable int num,@RequestParam(required=false) Long channelId) {
		return new AjaxResult();
	}

	@Value("#{mvcProp.upload_mp3}")
	Resource uploadMp3Dir;
	@Value("#{mvcProp.upload_lyrics}")
	Resource uploadImgDir;

	@RequestMapping(value = "/add")
	@ResponseBody
	public AjaxResult addTrack(@ModelAttribute("track") Track track, @RequestParam(value = "mp3File") MultipartFile mp3File,
			@RequestParam(value = "imgFile") MultipartFile imgFile) {

		return new AjaxResult();
	}

	@RequestMapping(value = "/{id}")
	public String trackDetail(ModelMap map,@PathVariable(value="id") Long id) {
		Track t = trackService.findTrackById(id);
		List<Comment> latestComments = commentService.findCommentByTrackId(id,1,10);
		map.put("track", t);
		map.put("latestComments", latestComments);
		return "track";
	}

	@RequestMapping(value = "/latest/{cateId}")
	public String latestTrackList(ModelMap map,@PathVariable Long cateId) {
		map.put("latestTracks", trackService.findLatestTracks(cateId,10));
		return "latest-track-list";
	}


}
