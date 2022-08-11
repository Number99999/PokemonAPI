package api.Controller;

import api.Model.User;
import api.Service.SendMailService;
import api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailController {
    @Autowired
    private SendMailService service;
    @Autowired
    private UserService userService;

    @GetMapping("/sent")
    public String SentMail(){
        service.sendSimpleEmail("aster01257099353@gmail.com",
                "This is email body",
                "This is email subject");
        return "sent";
    }

    @GetMapping("/")
    public User getUser(){
        return userService.findUserById("2");
    }
}
