package itforum.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	public DataSource dataSource;
	
	protected void configure(HttpSecurity http) throws Exception{
		http
			.formLogin()
			    .loginPage("/login")
			.and()
			.httpBasic()
				.realmName("ITForum")
			.and()
			.logout()
				.logoutSuccessUrl("/")
			.and()	
			.rememberMe()
				.tokenValiditySeconds(10)
			.and()
			.authorizeRequests() 
					.antMatchers("/rules").hasAnyAuthority("USER", "ADMIN")
					.anyRequest().permitAll()
			.and()		
				.sessionManagement().sessionFixation().none()
			.and()
				.csrf().disable();
		} 
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(
					"SELECT nick, password, enabled FROM user WHERE nick=?")
			.authoritiesByUsernameQuery(
					"SELECT nick, role FROM user WHERE nick=?");
	}
}
