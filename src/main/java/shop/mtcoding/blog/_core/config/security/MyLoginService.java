package shop.mtcoding.blog._core.config.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRepository;

//시큐리티 라이브러리적용할때 최초실행시 유저디테일 서비스가 자동으로 올라간다.
// 실행되는 조건 POST /login  x-www-form-url 키값이 같아야 로그인된다.
@RequiredArgsConstructor
@Service // 컴포넌트스캔
public class MyLoginService implements UserDetailsService {

    private final UserRepository userRepository;
    private  final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("체크됐는지 확인하기"+username);
        User user = userRepository.findByUsername(username);

        if (user == null){
            System.out.println("user는 null");
            return null;
        }else{
            System.out.println("user를 찾았다");
            session.setAttribute("sessionUser",user); // 머스태치에서만 사용
            return new MyLoginUser(user); //securityContextHolder 에저장
        }

    }
}
