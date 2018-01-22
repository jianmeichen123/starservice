package com.galaxy.im.business.idea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.galaxy.im.business.idea.service.IIdeaService;

@Controller
public class IdeaController {

	@Autowired
	IIdeaService service;
}
