package mapper;

import dto.request.ReqNews;
import dto.response.ResNews;
import entity.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsMapper {

    public News toEntity(ReqNews req) {
        return News.builder()
                .name(req.getTitle())
                .description(req.getDescription())
                .imgUrl(req.getImgUrl())
                .date(req.getDate())
                .build();
    }

    public ResNews toDto(News news) {
        ResNews res = new ResNews();
        res.setId(news.getId());
        res.setTitle(news.getName());
        res.setDescription(news.getDescription());
        res.setImgUrl(news.getImgUrl());
        res.setDate(String.valueOf(news.getDate()));
        return res;
    }

}
