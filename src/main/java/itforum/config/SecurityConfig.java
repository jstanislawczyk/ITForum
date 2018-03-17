package itforum.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	public DataSource dataSource;
	
	protected void configure(HttpSecurity http) throws Exception{
		http
			.formLogin()
			    .loginPage("/login")
			    .failureUrl("/login?failure")
			.and()
			.httpBasic()
				.realmName("ITForum")
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
			.and()	
			.rememberMe()
				.tokenValiditySeconds(604800)
				.key("itforum")
			.and()
			.authorizeRequests() 
					.antMatchers("/admin","/admin/addNewCategory").hasAuthority("ADMIN")
					.antMatchers("/admin/ban/{userNick}").hasAuthority("ADMIN")
					.antMatchers("/admin/deleteComment/{commentId}").hasAuthority("ADMIN")
					.antMatchers("/admin/listOfUsers").hasAuthority("ADMIN")
					.antMatchers("/post/createNewPost").hasAnyAuthority("USER","ADMIN")
					.antMatchers("/profile/delete").hasAnyAuthority("USER","ADMIN")
					.antMatchers("/profile/").anonymous()
					.antMatchers("/login").anonymous()
					.antMatchers("/register").anonymous()
					.anyRequest().permitAll()
			.and()		
				.sessionManagement().sessionFixation().none()
			.and()
				.csrf();
		} 
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder())
			.usersByUsernameQuery(
					"SELECT nick, password, enabled FROM user WHERE nick=?")
			.authoritiesByUsernameQuery(
					"SELECT nick, role FROM user WHERE nick=?");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
