package com.spring.shop.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class MyUser implements UserDetails {

    //id, username, password, e-mail, role, enabled
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username, password, email, activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.EAGER,
    mappedBy = "user")
    private Set<Support> supports = new HashSet<>();
    @OneToMany (cascade = CascadeType.ALL,
    fetch = FetchType.EAGER,
    mappedBy = "user")
    private Set<Item> items = new HashSet<>();
    private boolean enabled;

    public MyUser(){}

    public MyUser(String username, String password, String email, Set<Role> role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Support> getSupports() {
        return supports;
    }

    public void setSupports(Set<Support> supports) {
        this.supports = supports;
    }
    public void addSupport(Support support){
        supports.add(support);
    }

    @Override
    public int hashCode() {
        int res = 0;
        if (this.username!=null) res += this.username.hashCode()*17;
        if (this.email!=null) res += this.email.hashCode()*5;
        if (this.id!=0) res += this.id;
        if (this.password!=null) res += this.password.hashCode()*7;
        if (this.role!=null) res += this.role.hashCode()*8;
        return res;
    }
    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (!(o instanceof MyUser)) return false;
        MyUser user = (MyUser) o;
        boolean first = (this.username==null&&user.username==null)||(this.username!=null&&this.username.equals(user.username));
        boolean second = (this.email==null&&user.email==null)||(this.email!=null&&this.email.equals(user.email));
        boolean thirth = (this.password==null&&user.password==null)||
                (this.password!=null&&this.password.equals(user.password));
        boolean fourth = (this.role==null&&user.role==null)||(this.role!=null&&this.role.equals(user.role));
        boolean five = this.id== user.getId();
        return first&&second&&thirth&&fourth&&five;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
