package beans.configuration;

import beans.security.Roles;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.InvalidSessionAccessDeniedHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yauhen_yemelyanau on 7/10/17.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String HAS_REGISTERED_USER_ROLE = "hasAuthority('" + Roles.REGISTERED_USER.getAuthority() + "')";
    private static final String HAS_BOOKING_MANAGER_ROLE = "hasAuthority('" + Roles.BOOKING_MANAGER.getAuthority() + "')";

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/event/**").hasAuthority(Roles.REGISTERED_USER.getAuthority())
                .antMatchers("/auditorium/**").access(HAS_REGISTERED_USER_ROLE)
                .antMatchers("/home").access(HAS_REGISTERED_USER_ROLE)
                .antMatchers("/ticket/list").access(HAS_BOOKING_MANAGER_ROLE)
                .antMatchers("/ticket/{\\d}").access(HAS_REGISTERED_USER_ROLE)
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/event/list", true)
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .permitAll(true);

        http.rememberMe()
                .key("remember-me-key")
                .tokenValiditySeconds(86400)
                .rememberMeCookieName("remember-me-cookie");

        http.sessionManagement()
                .maximumSessions(1);


        //TODO:fixme. Should be enabled however does not work for MacOs.
        http.csrf().disable();

    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Autowired
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getEncoder());
        return authProvider;
    }

    @Bean(name="passwordEncoder")
    BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }


}
