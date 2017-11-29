package com.galaxy.im.business.meeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxy.im.business.meeting.service.IMeetingSchedulingService;

@Controller
@RequestMapping("/schedule")
public class MeetingSchedulingController {
	
	@Autowired
	IMeetingSchedulingService service;

}
