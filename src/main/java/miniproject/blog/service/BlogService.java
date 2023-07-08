package miniproject.blog.service;

import lombok.RequiredArgsConstructor;
import miniproject.blog.domain.Article;
import miniproject.blog.dto.AddArticleRequest;
import miniproject.blog.dto.UpdateArticleRequest;
import miniproject.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " +id));
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }

    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        article.update(request.getTitle(),request.getContent());

        return article;
    }





}
