package com.elife.MiniProject.dao.entities;

import com.elife.MiniProject.dao.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
 
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstname;
  private String lastname;
  @Column(unique = true,nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  @Column(unique = true)
  private String photo;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;
  @Override
  
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
 }


  @Override
  public String getPassword() {
    return password;
 }
 
  @Override
  public String getUsername() {
    return email;
 }
 
  @Override
  public boolean isAccountNonExpired() {
    return true;
 }
 
  @Override
  public boolean isAccountNonLocked() {
    return true;
 }
 
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
 }
 
  @Override
  public boolean isEnabled() {
    return true;
 }
 
}
