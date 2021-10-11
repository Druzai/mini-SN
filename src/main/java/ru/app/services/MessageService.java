package ru.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.app.models.Dialog;
import ru.app.models.Message;
import ru.app.models.User;
import ru.app.repositories.DialogRepository;
import ru.app.repositories.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DialogRepository dialogRepository;

    @Transactional(readOnly = true)
    public List<Dialog> getDialogListForUser(User currentUser) {
        return dialogRepository.findAll().stream()
                .filter(m -> m.getUserList().contains(currentUser)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Dialog> getDialogById(Long id) {
        return dialogRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Dialog> getDialogForUser(User currentUser, User messagingToUser) {
        var dialog = dialogRepository.findAll().stream()
                .filter(m -> m.getUserList().contains(currentUser) && m.getUserList().contains(messagingToUser))
                .collect(Collectors.toList());
        if (dialog.isEmpty())
            return Optional.empty();
        else
            return Optional.of(dialog.get(0));
    }

    @Transactional
    public Dialog addNewDialogIfEmpty(User currentUser, User messagingToUser) {
        var dialog = getDialogForUser(currentUser, messagingToUser);
        if (dialog.isEmpty()) {
            var d = new Dialog();
            d.addUser(currentUser);
            d.addUser(messagingToUser);
            dialogRepository.save(d);
            return d;
        } else {
            return dialog.get();
        }
    }

    @Transactional
    public void addNewMessageToDialog(Message message, Dialog dialog) {
        message.setId(messageRepository.count() + 1);
        messageRepository.save(message);
        dialog.addMessage(message);
        dialogRepository.save(dialog);
    }
}
