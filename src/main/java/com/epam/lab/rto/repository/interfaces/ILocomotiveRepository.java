package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.Locomotive;
import java.util.List;

public interface ILocomotiveRepository {

    List<Locomotive> getAllLocomotives();

    Locomotive getLocomotiveById(long id);

    Locomotive getLocomotiveByName(String name);
}