package kg.tezal.tezal_back.config;


import kg.tezal.tezal_back.service.security.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "kg.tezal.tezal_back")
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private JwtAuthenticationTokenFilter jwtAuthFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/api/authenticate").permitAll()
                    .antMatchers("/api/**").permitAll()
                    .and()
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    @Order(2)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("customUserDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        public void configureGlobalSecurity(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(authenticationProvider());
        }

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder(8);
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService);
            authenticationProvider.setPasswordEncoder(getPasswordEncoder());
            return authenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
//                    .antMatchers(HttpMethod.POST, "/organization/forOrgAdmin/**").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/forOrgAdmin/**").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/forOrgAdmin/**").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/forOrgAdmin/**").hasRole("MANAGER")
//
//                    .antMatchers(HttpMethod.POST, "/organization/order/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/order/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/order/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/order/forOrgAdmin").hasRole("MANAGER")
//
//                    .antMatchers(HttpMethod.POST, "/organization/rate/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/rate/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/rate/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/rate/list/forOrgAdmin").hasRole("MANAGER")
//
//                    .antMatchers(HttpMethod.POST, "/organization/purchase/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/purchase/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/purchase/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/purchase/forOrgAdmin").hasRole("MANAGER")
//
//                    .antMatchers(HttpMethod.POST, "/organization/sale/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/sale/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/sale/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/sale/forOrgAdmin").hasRole("MANAGER")
//
//
//                    .antMatchers(HttpMethod.GET, "/organization/report").hasRole("MANAGER")
//
//
//                    .antMatchers(HttpMethod.POST, "/organization/event/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/event/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/event/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/event/list/forOrgAdmin").hasRole("MANAGER")
//
//                    .antMatchers(HttpMethod.POST, "/organization/manager/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/manager/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/manager/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/manager/list/forOrgAdmin").hasRole("MANAGER")
//
//                    .antMatchers(HttpMethod.POST, "/client/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/client/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/client/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/client/list/forOrgAdmin").hasRole("MANAGER")
//
//
//                    .antMatchers(HttpMethod.POST, "/organization/filial/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/organization/filial/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/organization/filial/list/forOrgAdmin").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/organization/filial/list/forOrgAdmin").hasRole("MANAGER")
//
//                    .antMatchers(HttpMethod.POST, "/bonus/forOrgAdmin/**").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.PUT, "/bonus/forOrgAdmin/**").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.DELETE, "/bonus/forOrgAdmin/**").hasRole("MANAGER")
//                    .antMatchers(HttpMethod.GET, "/bonus/forOrgAdmin/**").hasRole("MANAGER")

//                    .antMatchers(HttpMethod.GET, "/organization/{id}/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.GET, "/organization/{id}/**").access("hasRole('ROLE_ADMIN') or @customUserDetailsService.isOrganizationMember(authentication,#id)")
//                    .antMatchers(HttpMethod.POST, "/organization/{id}/**").access("hasRole('ROLE_ADMIN') or @customUserDetailsService.isOrganizationMember(authentication,#id)")
//                    .antMatchers(HttpMethod.PUT, "/organization/{id}/**").access("hasRole('ROLE_ADMIN') or @customUserDetailsService.isOrganizationMember(authentication,#id)")
//                    .antMatchers(HttpMethod.DELETE, "/organization/{id}/**").access("hasRole('ROLE_ADMIN') or @customUserDetailsService.isOrganizationMember(authentication,#id)")
//                    .antMatchers(HttpMethod.POST, "/organization/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.PUT, "/organization/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.DELETE, "/organization/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.GET, "/organization/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST, "/cashier/**").hasRole("CASHIER")
//                    .antMatchers(HttpMethod.PUT, "/cashier/**").hasRole("CASHIER")
//                    .antMatchers(HttpMethod.DELETE, "/cashier/**").hasRole("CASHIER")
//                    .antMatchers(HttpMethod.GET, "/cashier/**").hasRole("CASHIER")

                    .antMatchers("/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll()
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403");
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/resources/**", "/static/**", "/assets/**", "/css/**", "/js/**");
        }
    }

}