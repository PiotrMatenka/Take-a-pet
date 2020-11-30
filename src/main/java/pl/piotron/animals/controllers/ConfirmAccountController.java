package pl.piotron.animals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.piotron.animals.services.UserConfirmService;

@Controller
public class ConfirmAccountController {
    private final UserConfirmService userConfirmService;
    @Autowired
    public ConfirmAccountController(UserConfirmService userConfirmService) {
        this.userConfirmService = userConfirmService;
    }

    @RequestMapping("/confirmAccount")
    public String confirmAccount (@RequestParam("token") String confirmToken, Model model)
    {
        if (confirmToken!=null)
        {
            userConfirmService.confirmAccount(confirmToken);
            model.addAttribute("message", "Gratulacje, konto jest aktywne!");
            return "confirmAccount";
        }else {
            model.addAttribute("message", "Coś poszło nie tak");
        }
        return "confirmAccount";
    }

    @GetMapping("/user-login")
    public String backToLogin()
    {
        return "redirect:/#!/user-login";
    }
}
