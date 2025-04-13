package com.example.csc311finalcapstoneprojectgroup04.NetworkMessagesandUpdate;
/// ping enum for sending pings
public class Ping {
    enum PingStatus {
        UserPing, //standard user ping
        UserJoinPing,//if the
        //server responses
        GameInProgress,
        GameFull,
        GameNotStarted,
        LobbyFull,
        HostDoesNotMatch,//not sure if this is necessary
        SuccessfulGameJoin,
    }
}
