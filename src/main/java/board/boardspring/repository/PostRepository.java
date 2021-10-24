package board.boardspring.repository;

import board.boardspring.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findByIdx(Long id);
    List<Post> findAll();
}
