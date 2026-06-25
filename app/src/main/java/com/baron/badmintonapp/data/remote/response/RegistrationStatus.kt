package com.baron.badmintonapp.data.remote.response

// Matches the "status" string your Express POST /social/:id/register controller
// returns. Gson maps JSON strings to enum constants by EXACT name match
// (case-sensitive) — so the backend must send back the literal strings
// "CONFIRMED" / "WAITLISTED" / "ALREADY_REGISTERED", not lowercase or anything
// reworded, or Gson will throw a parse exception at runtime with no compile-time warning.
enum class RegistrationStatus {
    CONFIRMED,         // registration created, under capacity
    WAITLISTED,        // registration created, but event was full (registration_waiting = true)
    ALREADY_REGISTERED // player already had a non-cancelled registration for this event — not an error
}