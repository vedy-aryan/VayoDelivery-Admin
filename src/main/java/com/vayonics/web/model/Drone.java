package com.vayonics.web.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drone {
    private double currentlatitude;
    private double currentlongitude;
    private String status;
}

