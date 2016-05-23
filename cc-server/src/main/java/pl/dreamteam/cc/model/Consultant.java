package pl.dreamteam.cc.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Consultant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String skypeID;

    private boolean loggedIn;

    private boolean talking;
}