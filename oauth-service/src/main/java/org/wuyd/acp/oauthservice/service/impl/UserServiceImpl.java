package org.wuyd.acp.oauthservice.service.impl;

import org.wuyd.acp.oauthservice.entity.UserEntity;
import org.wuyd.acp.oauthservice.mapper.UserMapper;
import org.wuyd.acp.oauthservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuYd
 * @since 2020-01-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
