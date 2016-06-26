package pl.dreamteam.cc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by abu on 08.06.2016.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class ServiceUser implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    String login;
    String haslo;
}
