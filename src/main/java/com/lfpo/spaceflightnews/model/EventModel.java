package com.lfpo.spaceflightnews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity

public class EventModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "event_id", updatable = false)
    private String id;
    private String provider;

    @ManyToMany(mappedBy="events")
    @JsonIgnore
    private Set<ArticleModel> articles =  new HashSet<>();


}
