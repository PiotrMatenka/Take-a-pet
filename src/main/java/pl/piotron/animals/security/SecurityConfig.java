package pl.piotron.animals.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import pl.piotron.animals.repositories.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserRepository userRepository;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsServiceBean());
        }

        @Bean
        public PasswordEncoder passwordEncoder()
        {
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            return passwordEncoder;
        }

        @Override
        public UserDetailsService userDetailsServiceBean() throws Exception {
            return new CustomUserDetails(userRepository);
        }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
                // ignoring the "/", "/index.html", "/app/**", "/register",
                // "/favicon.ico"
                .antMatchers("/", "/index.html", "/h2-console/**", "#!/confirmAccount");
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic().and()
                    .logout().logoutUrl("/user-logout")
                    .deleteCookies("user", "JSESSIONID")
                    .invalidateHttpSession(true)
                    .and()
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                    .authorizeRequests()
                    .antMatchers("/","/api/**","/home", "/index.html", "/user-login",
                            "/app/components/**", "/node_modules/**", "/img/**", "/css/**",
                            "/app/services/**", "/upload-dir/**", "/upload/**", "/ads/**", "/h2-console/**",
                            "/confirmAccount", "/resetPassword")
                    .permitAll()
                    .antMatchers("/#!/ads/new", "/#!/ads/edit", "/#!/accountView/**", "/#!/changePassword").fullyAuthenticated()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/user-login").permitAll()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

            ;
        }
}


