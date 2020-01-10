package org.wuyd.acp.oauthservice.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import netscape.security.Privilege;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wuyd.acp.oauthservice.entity.*;
import org.wuyd.acp.oauthservice.service.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wuyd
 * 创建时间：2020/1/8 17:28
 */
@Slf4j
@Service
public class SysUserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    private UserRoleService userRoleService;

    private RoleService roleService;

    private PrivilegeService privilegeService;

    private RolePrivilegeService rolePrivilegeService;

    public SysUserDetailsServiceImpl(UserService userService,
                                     UserRoleService userRoleService,
                                     RoleService roleService,
                                     PrivilegeService privilegeService,
                                     RolePrivilegeService rolePrivilegeService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.rolePrivilegeService = rolePrivilegeService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, s));
        if(userEntity == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new User(
                userEntity.getUsername(), userEntity.getPassword(), true, true, true,
                true, getAuthorities(getRoles(userEntity.getId())));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleEntity> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private Collection<RoleEntity> getRoles(Integer userId) {
        Collection<RoleEntity> roles = new ArrayList<>();
        List<UserRoleEntity> userRoleEntities = userRoleService.list(Wrappers.<UserRoleEntity>lambdaQuery().eq(UserRoleEntity::getUserId, userId));
        userRoleEntities.forEach(userRoleEntity -> {
            RoleEntity roleEntity = roleService.getById(userRoleEntity.getRoleId());
            roles.add(roleEntity);
        });
        return roles;
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {
        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();
        for (RoleEntity role : roles) {
            privileges.add(role.getName());
            rolePrivilegeService.list(Wrappers.<RolePrivilegeEntity>lambdaQuery()
                    .eq(RolePrivilegeEntity::getRoleId, role.getId()))
                    .forEach(rolePrivilegeEntity -> {
                collection.add(privilegeService.getById(rolePrivilegeEntity.getId()));
            });
        }
        for (PrivilegeEntity item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
