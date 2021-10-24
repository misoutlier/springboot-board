package board.boardspring.service;

import board.boardspring.domain.Post;
import board.boardspring.repository.MemoryPostRepository;
import board.boardspring.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long registerPost(Post post) {
        postRepository.save(post);
        return post.getIdx();
    }

    public Optional<Post> findOne(Long myIdx) {
        return postRepository.findByIdx(myIdx);
    }

    public List<Post> findPosts() {
        return postRepository.findAll();
    }
}
