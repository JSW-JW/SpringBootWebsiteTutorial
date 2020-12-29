package com.example.spring_boot_tutorial.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // temporarily disabled for convenience
                .authorizeRequests()
                    .antMatchers("/", "/account/register", "/css/**").permitAll()
                    .anyRequest().authenticated() // 위의 url을 제외하고 모든 요청은 authenticated된 경우에만 허용.
                    .and()
                .formLogin()
                    .loginPage("/account/login")
                    .permitAll()
                    .and()
                .logout()
                .logoutUrl("/perform_logout")
                    .permitAll();
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enabled " // 인증 처리(authentication)
                        + "from user "
                        + "where username = ?")
                .authoritiesByUsernameQuery("select u.username, r.name " // 권한 처리(authorization)
                        + "from user_role ur inner join user u on ur.user_id = u.id "
                        + "inner join role r on ur.role_id = r.id "
                        + "where u.username = ?");
    }

    @Bean // maintained by Spring Container if annotated with @Configuration & @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}