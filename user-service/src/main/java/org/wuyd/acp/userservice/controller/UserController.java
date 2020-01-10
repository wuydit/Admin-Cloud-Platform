package org.wuyd.acp.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author wuyd
 * 2019/8/9 17:59
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("hello")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','user:hello')")
    public ResponseEntity hello(){
        return ResponseEntity.ok("hello");
    }
    @GetMapping("hello1")
    public ResponseEntity hello1(){
        return ResponseEntity.ok("hello");
    }

    @GetMapping("current")
    public ResponseEntity user(Principal principal) {
        return ResponseEntity.ok(principal);
    }

    @GetMapping("query")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity query() {
        return ResponseEntity.ok("具有query权限");
    }
}
