package org.hustsse.listenfm.web;

import java.util.List;

import org.hustsse.listenfm.entity.Channel;
import org.hustsse.listenfm.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

	@Autowired
	ChannelService channelService;

	@ModelAttribute("channels")
	public List<Channel> getAllChannels(){
		return channelService.getAllBusfmChannels();
	}

	@RequestMapping(value="/")
	public String index() {
		return "index";
	}

}
