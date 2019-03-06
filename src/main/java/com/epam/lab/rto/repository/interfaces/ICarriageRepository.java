package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.Carriage;

import java.util.List;

public interface ICarriageRepository {

    List<Carriage> getAllCarriages();

    Carriage getCarriageById(long id);

    Carriage getCarriageByName(String carriage);
}