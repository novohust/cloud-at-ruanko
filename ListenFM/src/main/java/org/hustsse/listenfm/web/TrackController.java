package org.hustsse.listenfm.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hustsse.listenfm.entity.Track;
import org.hustsse.listenfm.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/track")
public class TrackController {

	@Autowired
	private TrackService trackService;

	@RequestMapping(value = "/random/{num}")
	@ResponseBody
	public AjaxResult getRandomTracks(@PathVariable int num,@RequestParam(required=false) Long channelId) {
		List<Track> tracks = trackService.findRandomTracks(num,channelId);
		return new AjaxResult(tracks);
	}

	@Value("#{mvcProp.upload_mp3}")
	Resource uploadMp3Dir;
	@Value("#{mvcProp.upload_img}")
	Resource uploadImgDir;

	@RequestMapping(value = "/add")
	@ResponseBody
	public AjaxResult addTrack(@ModelAttribute("track") Track track, @RequestParam(value = "mp3File") MultipartFile mp3File,
			@RequestParam(value = "imgFile") MultipartFile imgFile) {

		File mp3Dir;
		File imgDir;
		AjaxResult error = new AjaxResult(null,"上传失败！");
		try {
			mp3Dir = uploadMp3Dir.getFile();
			imgDir = uploadImgDir.getFile();
		} catch (IOException e1) {
			e1.printStackTrace();
			return error ;
		}
		if (!mp3Dir.exists())
			mp3Dir.mkdirs();
		if (!imgDir.exists())
			imgDir.mkdirs();
		long time = System.currentTimeMillis();
		String mp3FileName = time + "_" + mp3File.getOriginalFilename();
		String imgFileName = time + "_" + imgFile.getOriginalFilename();

		File mp3 = new File(mp3Dir, mp3FileName);

		String mp3RelativePath = ((ServletContextResource)uploadMp3Dir).getPathWithinContext() + "/" + mp3FileName;
		File img = new File(imgDir, imgFileName);
		String imgRelativePath = ((ServletContextResource)uploadImgDir).getPathWithinContext() + "/" + imgFileName;
		track.setMp3(mp3RelativePath);
		track.setImg(imgRelativePath);

		try {
			mp3File.transferTo(mp3);
			imgFile.transferTo(img);
		} catch (Exception e) {
			e.printStackTrace();
			mp3.deleteOnExit();
			img.deleteOnExit();
			return error;
		}

		try {
			trackService.save(track);
		} catch (Exception e) {
			e.printStackTrace();
			mp3.deleteOnExit();
			img.deleteOnExit();
			return error;
		}

		return new AjaxResult();
	}

}
