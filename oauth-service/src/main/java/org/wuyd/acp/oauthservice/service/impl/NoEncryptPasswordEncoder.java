package org.wuyd.acp.oauthservice.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wuyd
 * 创建时间：2020/1/9 15:49
 */
public class NoEncryptPasswordEncoder  implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return (String) charSequence;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals((String) charSequence);
    }
}
