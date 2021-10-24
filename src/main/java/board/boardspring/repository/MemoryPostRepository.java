package board.boardspring.repository;

import board.boardspring.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryPostRepository implements PostRepository {

    private static Map<Long, Post> store = new HashMap<>();
    private static long seq = 0L;

    @Override
    public Post save(Post post) {
        post.setIdx(++seq);
        store.put(post.getIdx(), post);
        return post;
    }

    @Override
    public Optional<Post> findByIdx(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }
}
