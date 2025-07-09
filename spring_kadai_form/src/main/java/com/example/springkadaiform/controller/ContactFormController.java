package com.example.springkadaiform.controller;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springkadaiform.form.ContactForm;

import jakarta.validation.Valid;

@Controller
public class ContactFormController {

    @GetMapping("/form")
    public String showForm(Model model) {
        // すでにFlashAttributeにフォームがあればそれを使う
        if (!model.containsAttribute("contactForm")) {
            model.addAttribute("contactForm", new ContactForm());
        }
        return "contactFormView"; // フォーム画面
    }

    @PostMapping("/form")
    public String submitForm(
            @Valid ContactForm contactForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // バリデーションエラー時：フォームとBindingResultをFlash属性にセットしてリダイレクト
            redirectAttributes.addFlashAttribute("contactForm", contactForm);
            redirectAttributes.addFlashAttribute(
                    BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(contactForm),
                    bindingResult
            );
            return "redirect:/form"; // フォーム画面へリダイレクト
        }

        // 正常時：確認画面へリダイレクト（PRGパターン）
        redirectAttributes.addFlashAttribute("contactForm", contactForm);
        return "redirect:/confirm";
    }

    @GetMapping("/confirm")
    public String showConfirmPage() {
        return "confirmView"; // 確認画面
    }
}