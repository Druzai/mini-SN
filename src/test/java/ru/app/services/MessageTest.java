package ru.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import ru.app.ProgramTests;
import ru.app.models.Dialog;
import ru.app.models.Message;
import ru.app.models.Role;
import ru.app.models.User;
import ru.app.repositories.DialogRepository;

import java.util.HashSet;

public class MessageTest extends ProgramTests {
    @Mock
    private DialogRepository dialogRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Test
    public void addDialogAndMessage() {
        var set = new HashSet<Role>();
        set.add(new Role(2L, "ROLE_USER"));
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password1");
        user.setRoles(set);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setRoles(set);

        userService.save(user);
        userService.save(user2);
        userService.addFriend(user, user2);
        userService.addFriend(user2, user);

        var dialog = new Dialog();
        dialog.addUser(user);
        dialog.addUser(user2);
        dialogRepository.save(dialog);
        var message = new Message();
        message.setContent("test");

        messageService.addNewMessageToDialog(message, dialog);

        Assertions.assertFalse(dialog.getMessageList().isEmpty());
    }
}
