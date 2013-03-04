package org.hustsse.cloud.web;

import java.util.Date;
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
@RequestMapping(value = "/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/add")
	@ResponseBody
	public AjaxResult postComment(Comment comment) {
		comment.setPostTime(new Date());
		commentService.addComment(comment);
		return new AjaxResult(comment);
	}

}
