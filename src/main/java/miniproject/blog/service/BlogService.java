package miniproject.blog.service;

import lombok.RequiredArgsConstructor;
import miniproject.blog.domain.Article;
import miniproject.blog.dto.AddArticleRequest;
import miniproject.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }


}
