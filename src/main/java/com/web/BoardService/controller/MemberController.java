package com.web.BoardService.controller;

import com.web.BoardService.domain.Member;
import com.web.BoardService.service.MemberService;
import exceptions.MemberAlreadyExistsException;
import exceptions.MemberInvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String createLoginForm() {
        return "member/createMemberLoginForm";
    }

    @PostMapping("/login")
    public String login(MemberLoginDTO form) {
        try {
            Member m = memberService.processLogin(form);
            return "redirect:/";
        }

        catch (IllegalStateException e) {
            return "redirect:/login";
        }
    }
    @GetMapping("/signup")
    public String createSignUpForm(Model model) {
        MemberSignUpDTO memberSignUpDTO = new MemberSignUpDTO();
        model.addAttribute("member", memberSignUpDTO);
        return "member/createMemberSignUpForm";
    }

    @PostMapping("/signup")
    public String create(@ModelAttribute("member") MemberSignUpDTO form) {

        try {
            memberService.join(form);
        }

        catch (MemberInvalidInputException | MemberAlreadyExistsException e) {
            return "redirect:/signup";
        }

        return "redirect:/login";
    }
}
