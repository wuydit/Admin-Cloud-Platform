package org.wuyd.acp.oauthservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuYd
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role_privilege")
public class RolePrivilegeEntity extends Model<RolePrivilegeEntity> {

    private static final long serialVersionUID=1L;

    @TableId("id")
    private Integer id;

    @TableField("role_id")
    private Integer roleId;

    /**
     * 权限ID
     */
    @TableField("privilege_id")
    private Integer privilegeId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
