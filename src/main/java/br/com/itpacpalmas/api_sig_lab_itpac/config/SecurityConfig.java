package br.com.itpacpalmas.api_sig_lab_itpac.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.itpacpalmas.api_sig_lab_itpac.security.jwt.JwtConfigurer;
import br.com.itpacpalmas.api_sig_lab_itpac.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final HttpMethod HttpMethod = null;
	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				//metodos get em agendamento e status sao permitidos a todos
				.antMatchers(HttpMethod.GET, "/api/periodo/Agendamentos/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/status/**").permitAll()
				//POST DE AGENDAMENTO PARA ALUNO E PROFESSOR
				.antMatchers(HttpMethod.POST,"/api/periodo/Agendamentos/aluno").hasAnyRole("ALUNO")
				.antMatchers(HttpMethod.POST,"/api/periodo/Agendamentos/professor").hasAnyRole("PROFESSOR")
				//metodos de manual e agendamento E permitido ao tecnico 
				.antMatchers("/api/manual/**").hasAnyRole("TECNICO")
				.antMatchers("/api/periodo/Agendamentos**").hasAnyRole("TECNICO")
				//metodos get permitidos a todos altenticados
				.antMatchers(HttpMethod.GET, "/api/**").authenticated()
				//login e esqueceu a senha permitido a todos 
				.antMatchers("/api/forgotpass/**").permitAll()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/api/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.apply(new JwtConfigurer(tokenProvider));

	}

}
