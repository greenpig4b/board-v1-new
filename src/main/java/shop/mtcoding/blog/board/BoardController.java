package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRepository;
import shop.mtcoding.blog.user.UserRequest;

import java.util.List;

@RequiredArgsConstructor //final 붙은애들생성자만들어줌
@Controller
public class BoardController {
    //세션에 접근하는방법
    //1. 생성자주입

    private final HttpSession session;
    private final BoardRepository boardRepository;

    //RequestBody를 쓰면 오브젝트를 json으로 받아옴





    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id, BoardRequest.UpdateDTO requestDTO){
        //로그인되있어야함(부가로직)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null){
            return "redirect:/loginForm";
        }

        //권한체크(부가로직)
        Board board = boardRepository.fintdById(id);
        if (board.getUserId() != sessionUser.getId()){
            return "error/403";
        }

        //핵심로직
        //update board_tb set title = ?,content = ? where id = ?;
        boardRepository.update(requestDTO,id);

        return "redirect:/board/"+id;
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id,HttpServletRequest request){
        //인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null){
            return "redirect:/loginForm";
        }

        //권한체크
        //모델위임 (id로 board조회)
        Board board = boardRepository.fintdById(id);
        if (board.getUserId() != sessionUser.getId()){
            return "error/403";
        }

        //가방에 담기
        request.setAttribute("board",board);

        return "board/updateForm";
    }


    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id,HttpServletRequest request){

        // 인증안되면 나가게
        User sessionuser = (User) session.getAttribute("sessionUser");
        if (sessionuser == null){
            return "redirect/loginForm";
        }

        // 권한이없으면 나가
        Board board = boardRepository.fintdById(id);
        if (board.getUserId() != sessionuser.getId()){
            request.setAttribute("status",403);
            request.setAttribute("msg","권한이없습니다");
            return "error/40x";
        }

        //삭제
        boardRepository.deleteById(id);

        return "redirect:/";
    }
    @GetMapping({ "/", "/board" })
    public String index(HttpServletRequest request) {

        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("boardList", boardList);

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
    User sessionuser  = (User)session.getAttribute("sessionUser");
        if (sessionuser == null){
            return "redirect:/loginForm";
        }

    return "board/saveForm";

    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO,HttpServletRequest request){
        // 1. 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null){
            return "redirect:loginForm";
        }

        // 2. 바디데이터 확인 및 유효성검사
        System.out.println(requestDTO);

        if (requestDTO.getTitle().length() > 30){
            request.setAttribute("status",400);
            request.setAttribute("msg","title의 길이가 30자를 초과해서는 안됩니다.");
            return "error/40x"; //BadRequest
        }
        // 3. 모델 위임
        //insert into board_tb(title,content,user_id) values(?,?,?,now());

        boardRepository.save(requestDTO,sessionUser.getId());

        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request) {
        //System.out.println("id : "+id);
        // 1.모델진입 - 상세보기 데이터가져오기
        BoardResponse.DetailDTO responseDTO = boardRepository.findByIdWithUser(id);


        // 2.페이지 주입여부 체크(board의 user_id와 session user_id 비교)
        User sessionUser = (User) session.getAttribute("sessionUser");
        boolean pageOwner = false;

        try{
            if (responseDTO.getUserId() == sessionUser.getId()) {
                pageOwner = true;
            }
            if (sessionUser == null){
                pageOwner = false;
            }
        }catch (Exception e){

        }

        request.setAttribute("pageOwner",pageOwner);
        request.setAttribute("board", responseDTO);
        return "board/detail";
    }
}
