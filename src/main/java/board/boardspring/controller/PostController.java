package board.boardspring.controller;

import board.boardspring.domain.Post;
import board.boardspring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/new")
    public String createForm() {
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String registerPost(PostForm form) {
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setWriter(form.getWriter());
        post.setPassword(form.getPassword());
        post.setContents(form.getContents());

        postService.registerPost(post);
        return "redirect:/";
    }

    @GetMapping("/posts")
    public String findPosts(Model model) {
        model.addAttribute("posts", postService.findPosts());
        return "posts/postList";
    }

}
