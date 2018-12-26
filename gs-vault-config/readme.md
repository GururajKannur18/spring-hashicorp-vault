# Vault Configuration
This guide walks you through the process of using Spring Cloud Vault to build an application that retrieves its configuration properties from HashiCorp Vault.

# What you’ll build
You’ll start up Vault, store configuration properties inside Vault, build a Spring application and connect it with Vault.

# What you’ll need
- About 15 minutes
- favorite text editor or IDE
- JDK 1.8 or later
- Gradle 4+ or Maven 3.2+

You can also import the code straight into your IDE:
Spring Tool Suite (STS)
IntelliJ IDEA

# How to complete this guide
Like most Spring Getting Started guides, you can start from scratch and complete each step, or you can bypass basic setup steps that are already familiar to you. Either way, you end up with working code.

# Install and launch HashiCorp Vault
With your project set up, you can install and launch HashiCorp Vault.

If you are using a Mac with homebrew, this is as simple as:

```
$ brew install vault
```

Alternatively, download Vault for your operating system from https://www.vaultproject.io/downloads.html:

```
$ https://releases.hashicorp.com/vault/0.8.3/vault_0.8.3_darwin_amd64.zip
$ unzip vault_0.8.3_darwin_amd64.zip
```

For other systems with package management, such as Redhat, Ubuntu, Debian, CentOS, and Windows, see instructions at ``https://www.vaultproject.io/docs/install/index.html`.

After you install Vault, launch it in a console window. This command also starts up a server process.

```
$ vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"
```

You should see the following as one of the last output lines:

[INFO ] core: post-unseal setup complete

# Store configuration in Vault
Vault is a secrets management system allowing you to store sensitive data which is encrypted at rest. It’s ideal to store sensitive configuration details such as passwords, encryption keys, API keys.

Launch another console window to store application configuration in Vault using the Vault command line.

First, you need to set two environment variables to point the Vault CLI to the Vault endpoint and provide an authentication token.

```
$ set VAULT_TOKEN=00000000-0000-0000-0000-000000000000
$ set VAULT_ADDR=http://127.0.0.1:8200
```

```
>vault kv put secret/gs-vault-config example.username=demouser example.password=demopassword
Key              Value
---              -----
created_time     2018-12-26T14:25:07.5400739Z
deletion_time    n/a
destroyed        false
version          1

>vault kv put secret/gs-vault-config/cloud example.username=clouduser example.password=cloudpassword
Key              Value
---              -----
created_time     2018-12-26T14:25:53.0980305Z
deletion_time    n/a
destroyed        false
version          1
```
Now you have written two entries in Vault secret/gs-vault-config and secret/gs-vault-config/cloud.

# Configure your application
Here you configure your application with bootstrap.properties. Spring Cloud Vault operates in the bootstrap context to initially obtain configuration properties so it can provide these to the auto-configuration and your application itself.

src/main/resources/bootstrap.properties

```
spring.application.name=gs-vault-config
spring.cloud.vault.token=00000000-0000-0000-0000-000000000000
spring.cloud.vault.scheme=http
spring.cloud.vault.kv.enabled=true
```

Spring Cloud Vault uses VaultOperations to interact with Vault. Properties from Vault get mapped to MyConfiguration for type-safe access. ``@EnableConfigurationProperties(MyConfiguration.class)`` enables configuration property mapping and registers a MyConfiguration bean.

Application includes a main() method that autowires an instance of MyConfiguration.


As our Application implements CommandLineRunner, the run method is invoked automatically when boot starts. You should see something like this:

```
----------------------------------------
Configuration properties
        example.username is demouser
        example.password is demopassword
----------------------------------------
Now start your application with the cloud profile activated. You should see something like this:

----------------------------------------
Configuration properties
        example.username is clouduser
        example.password is cloudpassword
----------------------------------------
```

Configuration properties are bound according to the activated profiles. Spring Cloud Vault constructs a Vault context path from spring.application.name which is gs-vault and appends the profile name (cloud) so enabling the cloud profile will fetch additionally configuration properties from secret/gs-vault-config/cloud.

