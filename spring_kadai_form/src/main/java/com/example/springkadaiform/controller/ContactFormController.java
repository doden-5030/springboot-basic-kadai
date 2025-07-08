package com.example.springkadaiform.controller;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springkadaiform.form.ContactForm;

import jakarta.validation.Valid;

@Controller
public class ContactFormController {

    @GetMapping("/form")
    public String showForm(ContactForm contactForm) {
        return "contactFormView";
    }

    @PostMapping("/form")
    public String submitForm(
            @Valid ContactForm contactForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // バリデーションエラー時はリダイレクトせず、フォーム画面を再表示
            return "contactFormView";
        }

        // フォームの内容を処理（例: メール送信など）

        // リダイレクト時に一度だけ表示するメッセージ
        redirectAttributes.addFlashAttribute("successMessage", "お問い合わせありがとうございます。");
        return "redirect:/form/complete"; // PRGパターンにより二重送信防止
    }

    @GetMapping("/form/complete")
    public String showCompletePage() {
        return "contactCompleteView";
    }
}