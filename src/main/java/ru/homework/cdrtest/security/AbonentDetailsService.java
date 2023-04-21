package ru.homework.cdrtest.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.homework.cdrtest.entity.Abonent;
import ru.homework.cdrtest.entity.Role;
import ru.homework.cdrtest.repository.AbonentRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AbonentDetailsService implements UserDetailsService {

    private final AbonentRepository abonentRepository;

    public AbonentDetailsService(AbonentRepository abonentRepository) {
        this.abonentRepository = abonentRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Abonent user =
                abonentRepository.findByUsername(username).
                        orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
