package com.lfpo.spaceflightnews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity

public class LaunchModel implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "launch_id", updatable = false)
    private String id;
    private String provider;


    @ManyToMany(mappedBy="launches")
    @JsonIgnore
    private Set<ArticleModel> articles = new HashSet<>();


}
