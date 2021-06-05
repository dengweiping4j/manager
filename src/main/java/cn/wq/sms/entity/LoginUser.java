package cn.wq.sms.entity;

import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;

/**
 * @author DengWeiPing
 * @version 1.0
 * @date 2021/6/5 15:24
 */
@Data
public class LoginUser implements Serializable {

    private long serialVersionUID = 1L;

    private String userId;

    private String userName;

    private String name;

    private String password;

    private String roleId;

}
