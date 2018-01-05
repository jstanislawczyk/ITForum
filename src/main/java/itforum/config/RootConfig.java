package itforum.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={"itforum"},  excludeFilters={ @Filter(type=FilterType.ANNOTATION) })
@EnableWebMvc 
public class RootConfig {

}
