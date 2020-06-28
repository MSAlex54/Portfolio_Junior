package ru.my_prtfolio.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.my_prtfolio.spring.persist.repo.UserRepository;

import javax.servlet.http.HttpServletResponse;

@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserAuthService(userRepository);
    }

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth,
                              UserDetailsService userDetailsService,
                              PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);
        auth.inMemoryAuthentication()
                .withUser("root1")
                .password(passwordEncoder.encode("root"))
                .roles("SUPERADMIN");
        auth.inMemoryAuthentication()
                .withUser("root2")
                .password(passwordEncoder.encode("root"))
                .roles("ADMIN");
        auth.inMemoryAuthentication()
                .withUser("root3")
                .password(passwordEncoder.encode("root"))
                .roles("GUEST");
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasAnyRole("ADMIN", "GUEST","SUPERADMIN")
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint((req, resp, exception) -> {
                        resp.setContentType("application/json");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().println("{ \"error\": \"" + exception.getMessage() + "\" }");
                    })
                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    @Order(2)
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/user/list").hasAnyRole("ADMIN","SUPERADMIN")
                    .antMatchers("/user/*").hasAnyRole("ADMIN","SUPERADMIN")
                    .antMatchers("/product/list").hasRole("GUEST")
                    .antMatchers("/product/*").hasAnyRole("MANAGER","ADMIN","SUPERADMIN")

                    .antMatchers("/*").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/home")
                    .permitAll()
                    .and()
                    .logout()
//                    .logoutSuccessUrl("/")
            ;
        }
    }
}
