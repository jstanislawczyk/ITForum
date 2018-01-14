package itforum.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rules")
public class RulesController {
	
	@RequestMapping(method = GET)
	public String rules(){
		return "rulesView";
	}
}
