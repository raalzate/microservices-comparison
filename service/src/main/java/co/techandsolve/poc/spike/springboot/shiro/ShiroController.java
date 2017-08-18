package co.techandsolve.poc.spike.springboot.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin on 18/08/2017.
 */

@RestController
public class ShiroController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:index";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUser loginUser) {
        Subject currentUser = SecurityUtils.getSubject();
        if (StringUtils.hasText(loginUser.getUser()) && StringUtils.hasText(loginUser.getPassword())) {
            try {
                currentUser.login(new UsernamePasswordToken(loginUser.getUser(), loginUser.getPassword()));
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
                return "login";
            }
            return "redirect:index";
        } else {
            return "login";
        }
    }
}