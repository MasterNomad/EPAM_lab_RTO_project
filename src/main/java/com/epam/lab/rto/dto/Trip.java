package com.epam.lab.rto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trip {

    private long id;
    private Route route;
    private LocalDateTime departure;
    private BigDecimal price;

    public Trip() {
    }

}
