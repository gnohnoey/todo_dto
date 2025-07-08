package com.gnohnoey.todoDTO.controller;

import com.gnohnoey.todoDTO.dto.SignupDto;
import com.gnohnoey.todoDTO.model.User;
import com.gnohnoey.todoDTO.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignup(Model model) { //signup 보여주기
        //모델은 타임리프한테 값 전달하는 역할
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup( //signup 실행하기, 폼에다 값 전달
                            @Valid @ModelAttribute SignupDto signupDTO, //@Valid가 있어야 유효한지 아닌지 확인할 수 있음
                            BindingResult bindingResult, //두 signup이 실행됐을 때의 결과값이 담겨 있음 --> valid 검사 결과
                            Model model
            ) {
        if(bindingResult.hasErrors()){
            return "signup";
        }
        //중복 가입 여부 - 여기서 조회해보고 에러 나면 db에 안 넣음
        User user = User.builder()
                .username(signupDTO.getUsername())
                .password(signupDTO.getPassword())
                .build();
        userRepository.save(user);
        return "redirect:/login?registerd";
    }

}
