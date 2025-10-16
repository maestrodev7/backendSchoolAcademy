package com.example.school.infrastructure.mappers;

import com.example.school.domain.entities.Series;
import com.example.school.infrastructure.models.SeriesModel;

public class SeriesMapper {

    public static Series toDomain(SeriesModel model) {
        if (model == null) {
            return null;
        }

        Series series = new Series();
        series.setId(model.getId());
        series.setName(model.getName());
        series.setCode(model.getCode());

        return series;
    }

    public static SeriesModel toModel(Series entity) {
        if (entity == null) {
            return null;
        }

        SeriesModel model = new SeriesModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setCode(entity.getCode());

        return model;
    }
}
