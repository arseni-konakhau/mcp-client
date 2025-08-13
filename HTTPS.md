# Настройка HTTPS для приложения

## Общие требования
- При генерации SSL-сертификата дополнительную информацию можно пропускать (нажимая Enter)
- Обязательные поля:
  - Пароль
  - DNS (в поле "first and last name" должен совпадать с DNS приложения)

## Для Spring Boot приложения

### Генерация keystore
```bash
keytool -genkeypair -alias {придуманный alias} -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore {имя keystore}.p12 -validity 3650 -storepass {придуманный пароль}
```

### Генерация сертификата
```bash
keytool -export -keystore {имя keystore}.p12 -alias {придуманный alias} -file {имя сертификата}.crt
```

### Настройка проекта
1. Разместить файлы в папке `resources`:
   - `keystore.p12`
   - `{имя сертификата}.crt`

2. Добавить в `application.properties`/`application.yml`:
```properties
server.ssl.enabled=true
server.ssl.protocol=TLS
server.ssl.enabled-protocols=TLSv1.3
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password={придуманный пароль}
server.ssl.key-store-type=PKCS12
server.ssl.key-alias={придуманный alias}
```

3. Добавить сертификат в JDK:
```bash
keytool -import -alias {придуманный alias} -keystore "{путь к JDK}\lib\security\cacerts" -file {имя сертификата}.crt
```

## Для Keycloak

### Генерация сертификата
```bash
keytool -genkeypair -alias {придуманный alias} -keyalg RSA -keysize 2048 -validity 365 -keystore server.keystore -keypass {придуманный пароль} -storepass {придуманный пароль}
```

### Настройка
1. Разместить `server.keystore` в папку `conf` Keycloak
2. Добавить в `conf/keycloak.conf`:
```properties
http-enabled=false
http-port={some other port}
https-port={desirable for Keycloak port}
https-key-store-password={придуманный пароль}
```

## Для React приложения

### Генерация сертификата
```bash
openssl req -x509 -sha256 -nodes -days 3650 -newkey rsa:2048 -keyout private.key -out cert.crt
openssl x509 -req -signkey private.key -in cert.crt -out certificate.crt
```

### Настройка проекта
1. Разместить файлы в корне проекта:
   - `certificate.crt`
   - `private.key`

2. Добавить в `.env`:
```ini
HTTPS=TRUE
SSL_CRT_FILE=certificate.crt
SSL_KEY_FILE=private.key
```

### Для Vite (vite.config.ts)
```javascript
// Добавить в настройки сервера:
server: {
  https: {
    key: fs.readFileSync('private.key'),
    cert: fs.readFileSync('certificate.crt')
  }
}
```
```