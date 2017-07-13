package beans.services;

import beans.models.User;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by yauhen_yemelyanau on 7/11/17.
 */
@Component("userDetailsService")
public class SpringAdvancedUserDetailsService implements UserDetailsService {

    @Autowired()
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        return new UserPrincipal(user);

    }

    private class UserPrincipal implements UserDetails {

        private User user;

        public UserPrincipal(final User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getGrantedAuthorities();
        }

        @Override
        public String getPassword() {

            return Objects.isNull(user) ? null : user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
