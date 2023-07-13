package miniproject.blog.service;

import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import miniproject.blog.domain.Article;
import miniproject.blog.dto.AddArticleRequest;
import miniproject.blog.dto.UpdateArticleRequest;
import miniproject.blog.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String userName){
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " +id));
    }

    public void delete(long id){
        Article article = blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);

        blogRepository.deleteById(id);
    }




    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        authorizeArticleAuthor(article);
        article.update(request.getTitle(),request.getContent());

        return article;
    }




    private void authorizeArticleAuthor(Article article) {

        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (!article.getAuthor().equals(userName)){
            throw new IllegalArgumentException("not authorized");
        }
    }



}
