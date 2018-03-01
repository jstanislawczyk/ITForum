package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminPanelController {

	@RequestMapping(method = GET)
	public String adminControlPanel(){
		return "adminPage";
	}
}
