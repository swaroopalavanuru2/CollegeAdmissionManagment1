package com.mouritech.collegeadmissionmanagment.collegeadmissionmanagment.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mouritech.collegeadmissionmanagment.collegeadmissionmanagment.serviceimpl.CustomAdminService;

@Configuration
public class StudentSecurity {
	
	
	//@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        //http.authorizeHttpRequests().requestMatchers("/sendCredentails").permitAll();
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/authicate", "/sendCredentails").permitAll());
        //http.authorizeHttpRequests().requestMatchers("/updateStudentDetails").permitAll(); 
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.authorizeHttpRequests().requestMatchers("/getAllStudents").permitAll(); 
        //http.authorizeHttpRequests().requestMatchers("/getAllStudentsByPagination").permitAll(); 
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/sendStudentDetails").hasAuthority("STUDENT")).
                authorizeHttpRequests(requests -> requests.requestMatchers("/getAllStudentsByPagination").hasAuthority("ADMIN")).
                authorizeHttpRequests(requests -> requests.requestMatchers("/updateStudentDetails").hasAuthority("STUDENT").
                anyRequest().authenticated());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
        http.csrf(csrf -> csrf.disable());
		return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
		
	}

    @Bean
    UserDetailsService userDetailService()
	{
		return new CustomAdminService();
		
   	}

    @Bean
    AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
		
	}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
		
	}
    
}
