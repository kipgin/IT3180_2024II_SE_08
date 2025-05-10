package com.example.BTL_CNPM.household.model;

import jakarta.persistence.*;

@Entity
@Table(name = "households")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String ownerUsername;

    private int numOfMembers;

    @Column(nullable = false)
    private String buildingBlock;  // Tên hoặc ký hiệu tòa (ví dụ: A, B, C)

    @Column(nullable = false)
    private int floor;             // Số tầng (int để tiện tìm kiếm/sắp xếp)

    @Column(nullable = false)
    private String roomNumber;     // Số phòng (ví dụ: 301, 12A)

    public Household() {

    }

    public Household(Integer id, String ownerUsername, String buildingBlock, int floor, String roomNumber) {
        this.id = id;
        this.ownerUsername = ownerUsername;
        this.buildingBlock = buildingBlock;
        this.floor = floor;
        this.roomNumber = roomNumber;
    }

    // Getters and Setters

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(int numOfMembers) {
        this.numOfMembers = numOfMembers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getBuildingBlock() {
        return buildingBlock;
    }

    public void setBuildingBlock(String buildingBlock) {
        this.buildingBlock = buildingBlock;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
