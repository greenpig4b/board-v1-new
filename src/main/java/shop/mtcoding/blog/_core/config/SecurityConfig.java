package shop.mtcoding.blog._core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration //컴퍼넌트 스캔 - 설정파일
// 컨트롤러 ,레파지토리 ,컨피규레이션 ,컴퍼넌트
public class SecurityConfig {

    @Bean //디폴트값
     public BCryptPasswordEncoder encoder(){
         return new BCryptPasswordEncoder(); // 어떤해쉬로 비교할지 알게됨, IOC등록
     }

    //간단하게 할수없는 요청은 ignore로 예외처리
    @Bean
    public WebSecurityCustomizer ignore(){ //정적파일,데이터에서만 사용
        return w -> w.ignoring().requestMatchers("/board/*","/static/**","/h2-console/**");
    }

    @Bean //IOC 메모리에 올려준다.
    //시큐리티
    SecurityFilterChain configure(HttpSecurity http) throws Exception{
        //로그인이 필요한페이지 설정

        http.csrf(c ->c.disable() );

        http.authorizeHttpRequests(a -> {
           a.requestMatchers(RegexRequestMatcher.regexMatcher("/board/\\d+")).permitAll()
                    .requestMatchers("/user/**","/board/**").authenticated()
                    .anyRequest().permitAll();
        });

        //설정한 페이지가 로그인폼으로이동
        http.formLogin(f -> {
            f.loginPage("/loginForm").loginProcessingUrl("/login").defaultSuccessUrl("/").failureUrl("/loginForm");
        });

        return http.build();
    }
}
