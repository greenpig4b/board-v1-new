package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;


//댓글쓰기 , 댓글삭제 , 댓글목록
//where 절은 주소 나머지 body안에
@RequiredArgsConstructor
@Controller
public class ReplyController {

    private final HttpSession session;
    private final ReplyRepository replyRepository;

    @PostMapping("/reply/save")
    public String write(ReplyRequest.WriteDTO requestDTO){
        System.out.println(requestDTO);

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null){
            return "redirect:/loginForm";
        }
        //유효성검사 (각자)

        //핵심코드
        replyRepository.save(requestDTO,sessionUser.getId());

        return "redirect:/board/"+requestDTO.getBoardId();

    }

}
