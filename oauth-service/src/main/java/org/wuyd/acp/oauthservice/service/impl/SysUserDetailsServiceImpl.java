package org.wuyd.acp.oauthservice.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wuyd.acp.oauthservice.entity.RoleEntity;
import org.wuyd.acp.oauthservice.entity.UserEntity;
import org.wuyd.acp.oauthservice.entity.UserRoleEntity;
import org.wuyd.acp.oauthservice.service.RoleService;
import org.wuyd.acp.oauthservice.service.UserRoleService;
import org.wuyd.acp.oauthservice.service.UserService;

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

    public SysUserDetailsServiceImpl(UserService userService, UserRoleService userRoleService, RoleService roleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        UserEntity userEntity = userService.getOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, s));
        if(userEntity == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<UserRoleEntity> userRoleEntities = userRoleService.list(Wrappers.<UserRoleEntity>lambdaQuery().eq(UserRoleEntity::getUserId, userEntity.getId()));
        userRoleEntities.forEach(userRoleEntity -> {
            RoleEntity roleEntity = roleService.getById(userRoleEntity.getRoleId());
            authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
        });
        return new User(userEntity.getUsername(), userEntity.getPassword(),authorities);
    }
}
