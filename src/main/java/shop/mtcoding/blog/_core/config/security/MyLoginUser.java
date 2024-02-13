package shop.mtcoding.blog._core.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.mtcoding.blog.user.User;

import java.util.Collection;

//세션에 담길 유저 오브젝트(객체)
//세션에 담기전에 매서드호출
@RequiredArgsConstructor

public class MyLoginUser implements UserDetails {

    private final User user;

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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


    //권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
