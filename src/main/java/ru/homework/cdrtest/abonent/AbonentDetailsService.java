package ru.homework.cdrtest.abonent;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.homework.cdrtest.role.RoleEntity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbonentDetailsService implements UserDetailsService {

    private final AbonentRepository abonentRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        AbonentEntity user =
                abonentRepository.findAbonentByPhoneNumber(phoneNumber).
                        orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(user.getId().toString(), user.getPhoneNumber(), mapRolesToAuthorities(user.getRoleEntities()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<RoleEntity> roleEntities) {
        return roleEntities.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
