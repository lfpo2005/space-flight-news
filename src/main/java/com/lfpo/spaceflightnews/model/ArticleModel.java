package com.lfpo.spaceflightnews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Boolean featured;
    private String title;
    private String url;
    private String imageUrl;
    private String newsSite;

    @Column(length = 4000)
    private String summary;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime publishedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="articles_launches")
    private Set<LaunchModel> launches = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="articles_events")
    private Set<EventModel> events =  new HashSet<>();

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public ArticleModel(Long id) {
        this.id = id;
    }

}

