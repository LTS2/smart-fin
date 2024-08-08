package com.ysmeta.smartfin.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Controller
@Slf4j
public class HomeController {

	@GetMapping("/")
	public String home() {
		log.info(">>>>> HomeController.String.executed()");
		return "index.html";
	}

	// @GetMapping("/login")
	// public String a() {
	// 	log.info(">>>>> HomeController.String.executed()");
	// 	return "index";
	// }
}
