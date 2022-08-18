package com.lfpo.spaceflightnews.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lfpo.spaceflightnews.model.EventModel;
import com.lfpo.spaceflightnews.model.LaunchModel;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {

    private Long id;
    private Boolean featured;

    @NotEmpty
    @NotNull
    private String title;
    @NotEmpty
    @NotNull
    @URL
    private String url;
    @URL
    private String imageUrl;
    private String newsSite;

    @NotEmpty
    @NotNull
    private String summary;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime publishedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;


    Set<LaunchModel> launches = Collections.emptySet();
    Set<EventModel> events = Collections.emptySet();

}
