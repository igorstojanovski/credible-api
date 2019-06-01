package com.credible.api.model;

import com.credible.api.model.json.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ViewEvent {
    private static final String VERSION = "1.0.0";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonSerialize(using = UserSerializer.class, as = Long.class)
    private User owner;
    @JsonSerialize(using = UserSerializer.class, as = Long.class)
    private User visitor;
    private Date timestamp;
}
