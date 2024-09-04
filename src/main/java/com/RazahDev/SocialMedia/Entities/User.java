package com.RazahDev.SocialMedia.Entities;

import com.RazahDev.SocialMedia.Constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = ConstantTable.USER)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @ManyToMany
    private List<UserRole> roles;

    @OneToMany(mappedBy = "userPost")
    List<Post> postUsers;

    @OneToMany(mappedBy = "userComment")
    List<CommentUser> commentUsers;

    @OneToMany(mappedBy = "userLikes")
    List<LikesUser> likesUsers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return isEnabled;
    }

    public void setCreatedAt(Instant now) {
        createdAt = now;
    }
}
