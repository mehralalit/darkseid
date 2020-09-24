package edu.immune.boardgames.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LudoController {
	
	@RequestMapping(value = "board", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.GET)
	public String board() {
		return "board";
	}

}
