# Spring Boot - Security - Basic

Create spring boot maven project, with dependencies:
- Spring Web
- Lombok
- Spring Security
- Sring Boot DevTools

Create Product Controller
Add Class Product (entity)

Add configuration SecurityConfig class, add beans:

```java
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception{

        return httpSecurity.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/api/v1/product/*").hasRole("USER")       
            .requestMatchers( HttpMethod.POST,"/api/v1/product/*").hasRole("ADMIN")
            .requestMatchers( HttpMethod.DELETE,"/api/v1/product/*").hasRole("ADMIN")
            .and()
            .httpBasic()
            .and()
            .build();
    }

    @Bean
    UserDetailsService userDetailsService (){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User
            .withUsername("user")
            .password(passwordEncoder().encode("123456"))
            .roles("USER")
            .build());
        userDetailsManager.createUser(User.withUsername("admin")
        .password(passwordEncoder().encode("123456a"))
        .roles("ADMIN","USER")
        .build());
        return userDetailsManager;
    }
    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager( HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder())
        .and()
        .build();
    }

```


