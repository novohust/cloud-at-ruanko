package org.hustsse.cloud.web;

import java.util.List;

import org.hustsse.cloud.entity.Category;
import org.hustsse.cloud.entity.Track;
import org.hustsse.cloud.service.CategoryService;
import org.hustsse.cloud.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

	@Autowired
	TrackService trackService;
	@Autowired
	CategoryService categoryService;

//	@ModelAttribute("channels")
//	public List<Channel> getAllChannels(){
//		return channelService.getAllBusfmChannels();
//	}

	@RequestMapping(value="/")
	public String index() {
		return "index";
	}

	@RequestMapping(value="/index-content")
	public String indexContent(ModelMap map) {
		List<Track> popularTracks = trackService.findLatestTracks(10);
		map.put("popularTracks", popularTracks);
		return "index-content";
	}

	@RequestMapping(value="/category")
	public String category(ModelMap map) {
		List<Category> allCate = categoryService.findAllCategories();
		map.put("allCate", allCate);
		return "category";
	}

	@RequestMapping(value="/latest")
	public String latest(ModelMap map) {
		List<Category> allCate = categoryService.findAllCategories();
		map.put("allCate", allCate);
		map.put("latestTracks", trackService.findLatestTracks(allCate.get(0).getId(),10));
		return "latest";
	}
}
