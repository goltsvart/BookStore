package com.store.BookStore.rest.controller;

import com.store.BookStore.data.domain.User;
import com.store.BookStore.rest.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {

        if (userService.findByUsername(user.getUsername()) != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }else if (StringUtils.isBlank(user.getUsername())) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "Cannot be blank!");
        }else if (StringUtils.isBlank(user.getAddress())) {
            bindingResult
                    .rejectValue("address", "error.user",
                            "Cannot be blank!");
        }else if (StringUtils.isBlank(user.getEmail())) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Cannot be blank!");
        }else if (StringUtils.isBlank(user.getPassword())) {
            bindingResult
                    .rejectValue("password", "error.user",
                            "Cannot be blank!");
        }else if (StringUtils.isBlank(user.getSurname())) {
            bindingResult
                    .rejectValue("surname", "error.user",
                            "Cannot be blank!");
        }else if (StringUtils.isBlank(user.getPaymentMethod())) {
            bindingResult
                    .rejectValue("paymentMethod", "error.user",
                            "Cannot be blank!");
        }

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/registration");
        } else {
            // Registration successful, save user
            // Set user role to USER and set it as active
            userService.saveUser(user);

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/registration");
        }
        return modelAndView;
    }
}