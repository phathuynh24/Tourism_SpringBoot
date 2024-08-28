package com.tourism.backend.service;

import com.tourism.backend.model.Role;
import com.tourism.backend.model.User;
import com.tourism.backend.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            // if (updatedUser.getUsername() != null) {
            //     user.setUsername(updatedUser.getUsername());
            // }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getFirstName() != null) {
                user.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null) {
                user.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getBio() != null) {
                user.setBio(updatedUser.getBio());
            }
            if (updatedUser.getProfilePicture() != null) {
                user.setProfilePicture(updatedUser.getProfilePicture());
            }
            return userRepository.save(user);
        }).orElse(null);
    }
    
    public void updateUserPassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> searchUsersByName(String name) {
        return userRepository.findByUsernameContaining(name);
    }

    public void lockUserAccount(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setAccountNonLocked(false);
            userRepository.save(user);
        }
    }

    public void unlockUserAccount(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setAccountNonLocked(true);
            userRepository.save(user);
        }
    }
}
