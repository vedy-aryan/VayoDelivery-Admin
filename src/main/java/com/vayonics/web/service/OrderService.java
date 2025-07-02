package com.vayonics.web.service;

import com.google.firebase.database.*;
import com.vayonics.web.model.Drone;
import com.vayonics.web.model.Order;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    private final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
    private final DatabaseReference dronesRef = FirebaseDatabase.getInstance().getReference("drones");

    public Map<String, Order> getWaitingOrders() {
        CompletableFuture<Map<String, Order>> future = new CompletableFuture<>();

        ordersRef.orderByChild("status").equalTo("waiting_for_drone")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot snapshot) {
                        Map<String, Order> result = new HashMap<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Order order = child.getValue(Order.class);
                            result.put(child.getKey(), order);
                        }
                        future.complete(result);
                    }

                    public void onCancelled(DatabaseError error) {
                        future.completeExceptionally(error.toException());
                    }
                });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public void assignDroneToOrder(String orderId, String droneId) {
        ordersRef.child(orderId).child("status").setValueAsync(droneId);
        // 2. Mark drone as assigned
        dronesRef.child(droneId).child("status").setValueAsync("assigned");
    }

    public Map<String, Drone> getAllDrones() {
        CompletableFuture<Map<String, Drone>> future = new CompletableFuture<>();

        dronesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Drone> result = new HashMap<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Drone drone = child.getValue(Drone.class);
                    if (drone != null && "available".equalsIgnoreCase(drone.getStatus())) {
                        result.put(child.getKey(), drone);
                    }
                }
                future.complete(result);
            }

            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }


    //    ORDER COMPLETED (FUTURE)
    public void completeOrder(String orderId, String droneId) {
        ordersRef.child(orderId).child("status").setValueAsync("Order Completed");
        dronesRef.child(droneId).child("status").setValueAsync("available");
    }


    public Map<String, Order> getAllOrders() {
        CompletableFuture<Map<String, Order>> future = new CompletableFuture<>();

        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Order> result = new HashMap<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    result.put(child.getKey(), order);
                }
                future.complete(result);
            }

            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }


}
