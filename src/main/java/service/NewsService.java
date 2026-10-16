package service;

import configuration.TrackAction;
import dto.ApiResponse;
import dto.request.ReqNews;
import dto.request.ReqNewsDTO;
import dto.response.ResNews;
import entity.News;
import entity.enums.ActionType;
import exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.NewsMapper;
import org.springframework.stereotype.Service;
import repository.NewsRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @TrackAction(
            type = ActionType.NEWS_CREATED,
            description = "Yangilik yaratildi"
    )
    public ApiResponse<String> add(ReqNews req) {

        News news = newsMapper.toEntity(req);

        newsRepository.save(news);

        return ApiResponse.success(null, "Success");
    }

    public ApiResponse<List<ResNews>> getAll() {
        List<ResNews> list = newsRepository.findAll()
                .stream()
                .map(newsMapper::toDto)
                .toList();
        return ApiResponse.success(list, "Success");
    }

    public ApiResponse<ResNews> getOne(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found"));

        return ApiResponse.success(newsMapper.toDto(news), "Success");
    }

    public ApiResponse<String> delete(Long id) {
        News news = newsRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Not found")
        );

        news.setActive(false);
        newsRepository.save(news);
        return ApiResponse.success(null,"Deleted");
    }


    public ApiResponse<String> updateNews(ReqNewsDTO reqNewsDTO){
        News news = newsRepository.findById(reqNewsDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("News not found")
        );

        news.setName(reqNewsDTO.getTitle());
        news.setImgUrl(reqNewsDTO.getImgUrl());
        news.setDescription(reqNewsDTO.getDescription());
        news.setDate(LocalDate.parse(reqNewsDTO.getDate()));
        newsRepository.save(news);
        return ApiResponse.success(null, "News successfully updated");
    }

}
