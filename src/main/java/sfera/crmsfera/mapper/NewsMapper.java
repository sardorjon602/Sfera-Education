package sfera.crmsfera.mapper;

import sfera.crmsfera.dto.request.ReqNews;
import sfera.crmsfera.dto.response.ResNews;
import sfera.crmsfera.entity.News;
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
