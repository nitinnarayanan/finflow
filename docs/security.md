# Security Model
Production target: OAuth2/OIDC Authorization Code with PKCE, short-lived JWT access tokens, controlled refresh, issuer/audience/signature checks at gateway and services, scopes plus resource ownership, mTLS or workload identity for service calls, managed secrets, rotation, encryption, tokenization, secure headers, validation, and immutable privileged audit events.

The local auth service intentionally issues demo tokens and is not production identity infrastructure. Replace it with Keycloak or a managed IdP profile before security testing. CSRF is disabled only for stateless bearer-token APIs; browser cookie flows require CSRF protection and SameSite strategy.
