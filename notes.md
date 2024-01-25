ml-gradle provides a set of properties for each connection that it makes to MarkLogic that allow for an SSL connection
to be configured (note that "SSL" is used in this documentation to imply the usage of either SSL or TLS for secure
connections). The properties support the following scenarios:

1. "One way" SSL, where the client only needs to trust the certificate presented by the MarkLogic app server.
2. "Two way" SSL, where the client must also present a certificate to the server.
3. "Simple" SSL, where the client does not need to trust the server certificate. This approach is only recommended for development and not for production use cases. 

The [MarkLogic documentation](https://docs.marklogic.com/11.0/guide/security-guide/en/configuring-ssl-on-app-servers.html) 
describes how to configure a MarkLogic app server to require SSL. For the purpose of deploying an application, the following 
app server settings, visible in the MarkLogic Admin web application, are the ones to verify are configured correctly:

1. The "Ssl Certificate Template" field must be configured in order for the app server to require SSL.
2. The "Ssl Require Client Certificate" field should be set to `true` to require 2-way SSL.
3. If the "Ssl Client Issuer Authority Verification" field is set to `true`, then the client must present a certificate
signed by one of the authorities selected under "Ssl Client Certificate Authorities".

## Configuring one-way SSL

For one-way SSL, the certificate presented by the MarkLogic app server must be trusted by the client. 

Starting with ml-gradle 4.7.0, you can now configure 

Scenarios to document:
1. You have a keystore with both a client certificate and a server certificate.
2. You have separate keystore / truststore.
3. You only need a truststore (note that it's fine to configure a keystore still, just won't have an option).
4. You want "simple" SSL for development purposes. 
5?? Then there's the poorly named "useDefaultKeystore" = "I only need a truststore, and my JVM truststore has the necessary certificates in it."
- This seems rare and perhaps not worth documenting yet. 
- Needs to be deprecated and need a better name for it. 

