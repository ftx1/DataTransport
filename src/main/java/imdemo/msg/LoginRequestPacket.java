package imdemo.msg;

import lombok.Data;

import static imdemo.msg.Command.LOGIN_REQUEST;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tenshawfeng
 * @create 2018/11/16下午8:12
 * @since 1.0.0
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

}
