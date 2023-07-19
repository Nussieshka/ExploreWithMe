package ru.practicum.main_service.model;

import lombok.Getter;

@Getter
public enum StateAction {
    SEND_TO_REVIEW(false),
    CANCEL_REVIEW(false),

    PUBLISH_EVENT(true),
    REJECT_EVENT(true);

    private final Boolean requiresAdminPrivileges;

    StateAction(Boolean requiresAdminPrivileges) {
        this.requiresAdminPrivileges = requiresAdminPrivileges;
    }
}


