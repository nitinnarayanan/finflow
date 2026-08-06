# 10. Security Documentation


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Current versus target security

The current auth service issues demo tokens and is explicitly not production authentication. The target design uses an enterprise OAuth2/OIDC provider, Authorization Code with PKCE for browser/mobile clients, short-lived access tokens, rotated refresh tokens, and rotation-aware JWK validation.

## Authentication flow

* User authenticates with the identity provider.
* Client exchanges authorization code using PKCE.
* Access token contains narrow audience/scopes and short expiry.
* Gateway validates signature, issuer, audience, expiry, and coarse scopes.
* Resource service validates token/service identity and enforces ownership/business authorization.
* Refresh token rotation detects reuse and revokes the token family.

## Authorization

RBAC is insufficient alone. Combine roles/scopes with resource ownership, account delegation, transfer limits, risk tier, channel, and step-up authentication. Denials are auditable without logging full tokens.

## Service-to-service identity

Target options: mTLS via service mesh, SPIFFE/SPIRE, or cloud workload identity with signed service tokens. Internal network location is not identity. Internal posting endpoints require explicit service authorization and restrictive NetworkPolicies.

## Secrets management

Secrets come from Vault/AWS Secrets Manager/Kubernetes CSI integration, not source control or plain ConfigMaps. Rotation uses dual-valid credentials or coordinated rollout, connection refresh, and authentication-failure monitoring.

## Encryption and keys

* TLS 1.2+ in transit; mTLS for sensitive east-west paths.
* Managed encryption at rest for database, Kafka, Redis snapshots, and object storage.
* KMS-backed envelope encryption for sensitive fields.
* Signing/encryption key rotation with versioned key IDs and overlap windows.
* Certificates monitored for expiry and rotated automatically.

## PII and data minimization

Do not put full account numbers, tokens, passwords, or unnecessary customer attributes in logs, metrics, traces, Redis keys, or Kafka events. Tokenize/mask identifiers. Classify fields and define retention/access controls.

## Audit logging

Audit records answer who, what, resource, when, channel, decision, correlation ID, and policy/rule version. They are immutable/tamper-evident and access-restricted. Debug logs are not audit evidence.

## Threat model highlights

| Threat | Control |
|---|---|
| Credential stuffing | Rate limits, MFA/step-up, anomaly detection |
| Broken object authorization | Service-side ownership checks |
| Duplicate/replay payment | Idempotency scope, request hash, nonce/time policy |
| Token theft | Short lifetime, secure storage, rotation, audience restriction |
| SSRF through adapters | Egress allow-list, URL validation, metadata blocking |
| Injection | Parameterized JPA/SQL, validation, output encoding |
| Event tampering | TLS, ACLs, schema validation, producer identity |
| Insider access | Least privilege, separation of duties, immutable audit |
| Secret leakage | Secret manager, scanning, log masking, rotation |

## OWASP considerations

Validate all input, enforce object-level authorization, use secure headers, restrict CORS, avoid verbose errors, patch dependencies, rate-limit abuse, and protect administrative endpoints. CSRF is relevant when browser credentials are cookie-based; bearer tokens in headers change the threat but require XSS-resistant storage and secure refresh design.
