package shop.mtcoding.blog._core.handler;

import jakarta.persistence.NoResultException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blog._core.util.Script;

@ControllerAdvice // 모든에러 위임해주는 컨트롤러 :비정상 에러 컨트롤러(view == 파일리턴)
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class) // 모든 Exception
    public @ResponseBody String error1(Exception e){
        return Script.back(e.getMessage());
    }
}
