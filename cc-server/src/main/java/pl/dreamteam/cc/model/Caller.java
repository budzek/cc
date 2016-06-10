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
public class Caller implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    private String skypeId;
    /** processGlowny executionId **/
    private String processInstanceId;

    /** current executionId **/
    private String currentExecutionId;


    private String currentActivitiId;

//    /**
//    private String executionId;
}
