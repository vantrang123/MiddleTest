package com.trangdv.example_android.listeners;

public interface OnDatabaseChangedListeners {
    void onNewDatabaseEntryAdded();
    void onNewDatabaseEntryRemoved();
    void onNewDatabaseEntryRenamed();
}
