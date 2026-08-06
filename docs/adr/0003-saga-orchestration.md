# ADR-0003: Orchestrated payment saga
Status: Accepted

The payment service owns explicit workflow state, timeouts, commands, and review/compensation decisions. Participants own their domain logic. Choreography was rejected for the core transfer because state visibility and operational recovery matter more than loose coupling.
