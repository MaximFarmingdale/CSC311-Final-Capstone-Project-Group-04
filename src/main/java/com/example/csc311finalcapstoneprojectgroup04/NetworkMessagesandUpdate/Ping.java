package com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate;

import java.io.Serializable;

/// ping enum for sending pings
    public enum Ping implements Serializable {
        UserPing, //standard user ping
        //server responses
        GameInProgress,
        LobbyFull
    }
