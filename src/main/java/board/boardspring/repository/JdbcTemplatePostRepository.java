package board.boardspring.repository;

import board.boardspring.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplatePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatePostRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Post save(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("post").usingGeneratedKeyColumns("idx");

        Map<String, Object> params = new HashMap<>();
        params.put("title", post.getTitle());
        params.put("writer", post.getWriter());
        params.put("password", post.getPassword());
        params.put("contents", post.getContents());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        post.setIdx(key.longValue());
        return post;
    }

    @Override
    public Optional<Post> findByIdx(Long idx) {
        List<Post> result = jdbcTemplate.query(
                "select * from post where idx = ?", postRowMapper(), idx
        );
        return result.stream().findAny();
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query(
                "select * from post", postRowMapper()
        );
    }

    private RowMapper<Post> postRowMapper() {
        return (resultSet, rowNum) -> {
            Post post = new Post();
            post.setIdx(resultSet.getLong("idx"));
            post.setTitle(resultSet.getString("title"));
            post.setWriter(resultSet.getString("writer"));
            post.setPassword(resultSet.getString("password"));
            return post;
        };
    }
}
