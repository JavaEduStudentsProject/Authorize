package com.bezkoder.spring.security.mongodb.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document(collection = "users")
public class User {
  @Id
  @Generated
  private String id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @DBRef
  private Set<Role> roles = new HashSet<>();

  private String lastname;
  private String firstname;
  private String phone;
  private String image;
  private String country;
  private String dateOfBirth;

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
            "id='" + id + '\'' +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", roles=" + roles +
            ", lastname='" + lastname + '\'' +
            ", firstname='" + firstname + '\'' +
            ", phone='" + phone + '\'' +
            ", image='" + image + '\'' +
            ", country='" + country + '\'' +
            ", dateOfBirth='" + dateOfBirth + '\'' +
            '}';
  }
}
