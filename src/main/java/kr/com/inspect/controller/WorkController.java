package kr.com.inspect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkController {

	@GetMapping("/getWorkList")
	public String Workpage() {
		return "workList/getWorkList";
	}
}
