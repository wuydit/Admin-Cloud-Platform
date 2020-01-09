package org.wuyd.acp.oauthservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wuyd.acp.oauthservice.service.impl.SysUserDetailsServiceImpl;
import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuYd
 * @since 2020-01-09
 */
@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/member")
    public ResponseEntity user(Principal member) {
        return ResponseEntity.ok(member);
    }

    @DeleteMapping(value = "/exit")
    public ResponseEntity revokeToken(String access_token) {
        if (consumerTokenServices.revokeToken(access_token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}

