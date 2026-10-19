package sfera.crmsfera.mapper;

import sfera.crmsfera.dto.request.ReqCategory;
import sfera.crmsfera.dto.response.ResCategory;
import sfera.crmsfera.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public ReqCategory toReq(Category category) {
        if (category == null) return null;

        return ReqCategory.builder()
                .imgUrl(String.valueOf(category.getId()))
                .name(category.getName())
                .imgUrl(category.getImgUrl())
                .build();
    }

    public ResCategory toRes(Category category) {
        return ResCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imgUrl(category.getImgUrl())
                .duration(category.getDuration())
                .questionLimit(category.getQuestionLimit())
                .build();
    }


//    public Category toEntity(ReqCategory req) {
//        if (req == null) return null;
//
//        Category category = new Category();
//        category.setId(req.getId());
//        category.setName(req.getName());
//        category.setImgUrl(req.getImgUrl());
//        return category;
//    }


}
