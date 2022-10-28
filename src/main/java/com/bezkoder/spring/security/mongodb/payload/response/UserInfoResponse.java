package com.bezkoder.spring.security.mongodb.payload.response;

import java.util.Date;
import java.util.List;

public class UserInfoResponse {
  private String id;
  private String username;
  private String email;
  private String lastname;
  private String firstname;
  private String phone;
  private String image;
  private String country;
  private String dateOfBirth;
  private List<String> roles;

  public UserInfoResponse(String id, String username, String email, String firstname, String lastname, String phone, String image, String country, String dateOfBirth, List<String> roles) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.phone = phone;
    this.image = image;
    this.country = country;
    this.dateOfBirth =dateOfBirth;
    this.roles = roles;
  }



    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
