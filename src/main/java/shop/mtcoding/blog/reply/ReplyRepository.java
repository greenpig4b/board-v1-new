package shop.mtcoding.blog.reply;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReplyRepository {
    private final EntityManager em;
    //join subquery order by 서버 부하가 크다

    @Transactional
    public void save(ReplyRequest.WriteDTO writeDTO, int userId) {
        Query query = em.createNativeQuery("insert into reply_tb(comment,board_id,user_id,created_at) values(?,?,?,now())");
        query.setParameter(1,writeDTO.getComment());
        query.setParameter(2,writeDTO.getBoardId());
        query.setParameter(3,userId);

        query.executeUpdate();
    }

//    @Transactional
//    public void deleteById(int id){
//        Query query = em.createNativeQuery("delete from board_tb where id =?");
//        query.setParameter(1,id);
//        query.executeUpdate();
//    }
//
//    @Transactional
//    public void update(BoardRequest.UpdateDTO requestDTO, int id) {
//        Query query = em.createNativeQuery("update board_tb set title = ?,content = ? where id = ?");
//        query.setParameter(1,requestDTO.getTitle());
//        query.setParameter(2,requestDTO.getContent());
//        query.setParameter(3,id);
//        query.executeUpdate();
//    }
}
