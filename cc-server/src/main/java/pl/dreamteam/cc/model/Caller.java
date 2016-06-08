package pl.dreamteam.cc.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by abu on 08.06.2016.
 */
@Data
@Builder
public class Caller implements Serializable{
    private String skypeId;
}
