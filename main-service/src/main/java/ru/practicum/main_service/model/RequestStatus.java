package ru.practicum.main_service.model;

public enum RequestStatus {
    PENDING(false),
    CONFIRMED(true),
    CANCELED(false),
    REJECTED(true);

    final boolean isEventOwnerAction;

    RequestStatus(boolean isEventOwnerAction) {
        this.isEventOwnerAction = isEventOwnerAction;
    }

    public boolean isEventOwnerAction() {
        return isEventOwnerAction;
    }
}
