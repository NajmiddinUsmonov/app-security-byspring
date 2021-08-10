package uz.usmonov.firstsecuretyapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//@PreAuthorize ishlashi uchun shu anotatsiyani qoyamiz
///metodlari bor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("director1").password(passwordEncoder().encode("director1")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT","ADD_ALL_PRODUCT","EDIT_ALL_PRODUCT","DELETE_PRODUCT","READ_ONE_PRODUCT")
                .and()
                .withUser("director2").password(passwordEncoder().encode("director2")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT","ADD_ALL_PRODUCT","EDIT_ALL_PRODUCT","READ_ONE_PRODUCT")
                .and()
                .withUser("worker").password(passwordEncoder().encode("worker")).roles("WORKER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable()//=>Unless you put disable(), you cannot affect to data in database!
                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE,"/api/product/*").hasAuthority("DELET E_PRODUCT")
//                .antMatchers("/api/product/**").hasAnyAuthority("READ_ALL_PRODUCT","ADD_ALL_PRODUCT","EDIT_ALL_PRODUCT","DELETE_PRODUCT","READ_ONE_PRODUCT")
//                .antMatchers(HttpMethod.GET,"/api/product/**").hasAnyRole("DIRECTOR","MANAGER","WORKER")
//                .antMatchers(HttpMethod.GET,"/api/product").hasAnyRole("MANAGER","DIRECTOR")
//                .antMatchers("/api/product/**").hasRole("DIRECTOR")//eng kam kirishi mumkin bolgan foydalanuvchi oxirida yoziladi
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
