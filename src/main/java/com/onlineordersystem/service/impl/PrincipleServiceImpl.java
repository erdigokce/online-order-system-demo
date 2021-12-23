package com.onlineordersystem.service.impl;

import com.onlineordersystem.OosRuntimeException;
import com.onlineordersystem.domain.base.Principle;
import com.onlineordersystem.error.RegisterError;
import com.onlineordersystem.model.PrincipleDTO;
import com.onlineordersystem.repository.PrincipleRepository;
import com.onlineordersystem.security.Authority;
import com.onlineordersystem.service.PrincipleService;
import java.util.Optional;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class PrincipleServiceImpl implements PrincipleService {

    private final PrincipleRepository principleRepository;
    private final ModelMapper mapper;
    @Setter // Optional caching feature
    private UserCache userCache = new NullUserCache(); // Default disabled caching

    @Autowired
    public PrincipleServiceImpl(PrincipleRepository principleRepository, ModelMapper mapper) {
        this.principleRepository = principleRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void createUser(UserDetails user) {
        Principle principle = new Principle();
        principle.setEmail(user.getUsername());
        principle.setPassword(user.getPassword());
        principle.setEmailConfirmed(false);
        GrantedAuthority grantedAuthority = user.getAuthorities().stream().findFirst().orElseThrow(() -> new OosRuntimeException(RegisterError.ROLE_NOT_FOUND));
        Authority authority = Authority.valueOf(grantedAuthority.getAuthority());
        principle.setAuthority(authority);
        principleRepository.save(principle);
        this.userCache.putUserInCache(mapper.map(principle, PrincipleDTO.class));
    }

    @Transactional
    @Override
    public void updateUser(UserDetails user) {
        Optional<Principle> optionalAuthority = principleRepository.findByEmail(user.getUsername());
        if (optionalAuthority.isEmpty()) {
            log.error("No principle found! Update action is omitted.");
            return;
        }
        Principle principle = optionalAuthority.get();
        principle.setEmail(user.getUsername());
        this.userCache.removeUserFromCache(user.getUsername());
        this.userCache.putUserInCache(mapper.map(principle, PrincipleDTO.class));
    }

    @Transactional
    @Override
    public void deleteUser(String username) {
        principleRepository.findByEmail(username).ifPresentOrElse(
            principle -> {
                principleRepository.delete(principle);
                this.userCache.removeUserFromCache(username);
            },
            () -> log.error("No principle found for username : {}. Deleting action is omitted.", username)
        );
    }

    @Transactional
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            throw new AccessDeniedException("Can't change password as no Authentication object found in context for current user.");
        } else {
            String username = currentUser.getName();

            log.debug("Changing password for user '{}'", username);
            Optional<Principle> optionalPrinciple = principleRepository.findByEmail(username);
            if (optionalPrinciple.isEmpty()) {
                log.error("No principle found. Changing password action is omitted.");
                return;
            }
            Principle principle = optionalPrinciple.get();
            principle.setPassword(newPassword);
            Authentication authentication = this.createNewAuthentication(currentUser, newPassword);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            this.userCache.removeUserFromCache(username);
            this.userCache.putUserInCache(mapper.map(principle, PrincipleDTO.class));
        }
    }

    @Override
    public boolean userExists(String username) {
        return principleRepository.existsByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails cachedUser = userCache.getUserFromCache(username);
        if (cachedUser != null) {
            return cachedUser;
        }
        Optional<Principle> optionalPrinciple = findEntityByEmail(username);
        if (optionalPrinciple.isEmpty()) {
            log.error("No principle found.");
            return null;
        }
        Principle principle = optionalPrinciple.get();
        return mapper.map(principle, PrincipleDTO.class);
    }

    private Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = this.loadUserByUsername(currentAuth.getName());
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(user, newPassword, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());
        return newAuthentication;
    }

    @Override
    public Optional<Principle> findEntityByEmail(String email) {
        return principleRepository.findByEmail(email);
    }
}
