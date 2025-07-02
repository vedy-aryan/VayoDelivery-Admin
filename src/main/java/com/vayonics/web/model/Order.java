package com.vayonics.web.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String userId;
    private String foodItem;
    private int price;
    private double lat;
    private double lng;
    private long timestamp;
    private String status; // waiting_for_drone, drone_001{DRONE_ID}, Order Completed
}
