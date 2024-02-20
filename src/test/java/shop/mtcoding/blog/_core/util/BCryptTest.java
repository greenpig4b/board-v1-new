package shop.mtcoding.blog._core.util;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
    @Test
    public void gensalt_test(){
        String salt = BCrypt.gensalt();
        System.out.println("솔트값 : "+salt);
        // $2a$10$7iaJPL7O75Bj6nAmf2Q/nO
        // $2a$10$YjQq9WD/FsELutzA4/KG4u
    }



    @Test
    public void hashPw_test(){
        String rawPassword ="1234";
        String encPassword = BCrypt.hashpw(rawPassword,BCrypt.gensalt());
        System.out.println(encPassword);
    }
}
