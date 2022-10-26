package com.bezkoder.spring.security.mongodb.payload.request;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {

  public String getId() {
    return id;
  }

  @Id
  private String id;

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> roles;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  private String lastname;
  private String firstname;
  private String phone;
  private String image;
//  private Data birthDate;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

  public void setRole(Set<String> roles) {
    this.roles = roles;
  }


  public String getLastname() {
    return lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getPhone() {
    return phone;
  }

  public String getImage() {
    return image;
  }

//  public Data getBirthDate() {
//    return birthDate;
//  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setImage(String image) {
    this.image = image;
  }

//  public void setBirthDate(Data birthDate) {
//    this.birthDate = birthDate;
//  }
}
