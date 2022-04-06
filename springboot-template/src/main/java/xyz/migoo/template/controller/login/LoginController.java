package xyz.migoo.template.controller.login;

import com.lease.evehicle.controller.login.vo.AuthLoginReqVO;
import com.lease.evehicle.controller.login.vo.AuthLoginRespVO;
import com.lease.evehicle.dal.dataobject.Customer;
import com.lease.evehicle.service.CustomerService;
import com.lease.evehicle.service.TokenService;
import com.lease.evehicle.util.PasswordUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.framework.security.core.LoginUser;
import xyz.migoo.framework.security.core.annotations.CurrentUser;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private TokenService tokenService;

    @Resource
    private CustomerService customerService;

    @PostMapping("/sign-in")
    public Result<AuthLoginRespVO> login(@RequestBody AuthLoginReqVO req) {
        AuthLoginRespVO resp = new AuthLoginRespVO();
        resp.setToken(tokenService.signIn(req));
        return Result.getSuccessful(resp);
    }

    @PostMapping("/sign-out")
    public Result<?> logout() {
        // 框架层已经做了退出 这里只需要提供一个接口，不需要做实际业务
        return Result.getSuccessful();
    }

    @PostMapping("/signup")
    public Result<?> signup(@RequestBody Customer customer) {
        customerService.verify(customer.getPhone());
        customer.setPassword(PasswordUtils.encode(customer.getPassword()));
        customerService.add(customer);
        return Result.getSuccessful();
    }

    @GetMapping("/user-info")
    public Result<?> getCaptcha(@CurrentUser LoginUser user) {

        return Result.getSuccessful(user);
    }
}
