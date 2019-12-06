package org.taganhorn.FoodCountry.entities.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    @NotBlank
    @JsonIgnore
    private String password;

    @Size(max = 100)
    @NotBlank
    @Column(unique = true)
    private  String email;

    private String avatarUrl;

    @ElementCollection
    @CollectionTable(name = "users_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_role")
    private List<String> roles = Collections.emptyList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<RefreshTokenEntity> tokens;

    public UserEntity() {
    }

    public UserEntity(String name, String password, String email, String avatarUrl, List<String> role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.roles = role;
    }

    public UserEntity(Integer id, String name, String password, String email, String avatarUrl, List<String> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<RefreshTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens(List<RefreshTokenEntity> tokens) {
        this.tokens = tokens;
    }
}
