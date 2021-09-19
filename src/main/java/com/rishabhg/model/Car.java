package com.rishabhg.model;

/**
 * Model object to represent a car.
 */
public class Car {
  private String registrationNumber;
  private int ownerAge;

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public int getOwnerAge() {
    return ownerAge;
  }

  public Car(final String registrationNumber, final int ownerAge) {
    this.registrationNumber = registrationNumber;
    this.ownerAge = ownerAge;
  }
}
