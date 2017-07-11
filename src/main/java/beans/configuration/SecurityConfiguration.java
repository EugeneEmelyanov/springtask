package beans.configuration;

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
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by yauhen_yemelyanau on 7/10/17.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("mkyong").password("123456").roles();
//        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("REGISTERED_USER");
//        auth.inMemoryAuthentication().withUser("dba").password("123456").roles("BOOKING_MANAGER", "REGISTERED_USER");
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/ticket/**").access("hasRole('REGISTERED_USER')")
                .antMatchers("/event/**").access("hasRole('REGISTERED_USER')")
                .antMatchers("/auditorium/**").access("hasRole('REGISTERED_USER')")
                .antMatchers("ticket/**").access("hasRole('REGISTERED_USER')")
                .antMatchers("/ticket/{\\d}").access("hasRole('BOOKING_MANAGER') or hasRole('REGISTERED_USER')")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/home",true)
                    .failureUrl("/login?error=true")
                .and()
                    .logout()
                    .logoutSuccessUrl("/logout")
                    .permitAll(true);

        //TODO:fixme. Should be enabled however does not work for MacOs.
        http.csrf().disable();

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getEncoder());
        return authProvider;
    }

    protected PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(11);

    }
}
