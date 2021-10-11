package ru.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.app.models.Message;
import ru.app.services.MessageService;
import ru.app.services.UserService;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/dialogs")
    public String getDialogs(Model model) {
        var dialogs = messageService.getDialogListForUser(userService.getUser());
        model.addAttribute("dialogs", dialogs);
        model.addAttribute("userName", userService.getUser().getUsername());
        return "dialogs";
    }

    @GetMapping("/dialog/{id}")
    public String getDialog(@PathVariable int id, Model model) {
        var dialog = messageService.getDialogById((long) id);
        if (dialog.isPresent()) {
            model.addAttribute("messages", dialog.get().getMessageList());
            var mes = new Message();
            mes.setUser(userService.getUser());
            model.addAttribute("dialogId", id);
            model.addAttribute("message", mes);
            model.addAttribute("toUserName", dialog.get().getUserList().stream()
                    .filter(e -> e != userService.getUser()).collect(Collectors.toList()).get(0).getUsername());
            return "dialog";
        } else {
            model.addAttribute("reason", "Не найден диалог с id " + id);
            return "error";
        }
    }

    @PostMapping("/dialog/{id}")
    public String addMessage(@PathVariable int id, @ModelAttribute("message") Message message, Model model) {
        var dialog = messageService.getDialogById((long) id);
        if (dialog.isPresent()) {
            messageService.addNewMessageToDialog(message, dialog.get());
            return "redirect:/dialog/" + id;
        } else {
            model.addAttribute("reason", "Не найден диалог с id " + id);
            return "error";
        }
    }

    @PostMapping("/dialog/new/{id}")
    public String addDialog(@PathVariable int id, Model model) {
        var user = userService.findById((long) id);
        if (user.isPresent()) {
            if (!userService.getUser().getFriends().contains(user.get())){
                model.addAttribute("reason",
                        "Пользователь " + user.get().getUsername() + " у вас не в друзьях!");
                return "error";
            }
            var dialog = messageService.addNewDialogIfEmpty(userService.getUser(), user.get());
            return "redirect:/dialog/" + dialog.getId();
        } else {
            model.addAttribute("reason", "Не найден пользователь с id " + id);
            return "error";
        }
    }
}
