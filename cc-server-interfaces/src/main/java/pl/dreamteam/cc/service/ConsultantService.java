package pl.dreamteam.cc.service;

import pl.dreamteam.cc.dto.Status;

/**
 * Created by abu on 03.06.2016.
 */
public interface ConsultantService {
    void setStatus(String consultantId, Status status);
}
