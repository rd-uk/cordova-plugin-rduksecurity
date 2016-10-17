# RDUK - Security Cordova Plugin

## Supported OS
* Android _(api level **18+**)_

## Installation

```
cordova plugin add https://github.com/rd-uk/cordova-plugin-rduksecurity.git
```
---

# API Reference <a name="reference"></a>


* [security](#module_security)
    * [.certificate](#module_security.certificate)
        * [.generate(name, successCallback, errorCallback)](#module_security.certificate.generate)
    * [.digest](#module_security.digest)
        * [.md5digest(message, successCallback, errorCallback)](#module_security.digest.md5digest)    
        * [.sha1digest(message, successCallback, errorCallback)](#module_security.digest.sha1digest)    
        * [.sha256digest(message, successCallback, errorCallback)](#module_security.digest.sha256digest)    
    * [.message](#module_security.message)
        * [.sign(messageToSign, keyAlias, successCallback, errorCallback)](#module_security.message.sign)    
        * [.verify(messageToVerify, signature, keyAlias, successCallback, errorCallback)](#module_security.message.verify)    

---

<a name="module_security"></a>
## security

<a name="module_security.certificate"></a>
### security.certificate

<a name="module_security.certificate.generate"></a>
#### security.certificate.generate(name, successCallback, errorCallback)
Generates a certificate stored in KeyStore for android device. The public key is passed to the success callback as a Base64-encoded `String`.

__Note__:
if alias already exists, the existing public key is passed to the success callback.

```js
var onSuccess = function(data) {
    /* ... */
};

var onError = function(message) {
    /* ... */
};

security.certificate.generate('alias...', onSuccess, onError);
```

<a name="module_security.digest"></a>
### security.digest

<a name="module_security.digest.md5digest"></a>
#### security.digest.md5digest(message, successCallback, errorCallback)

Generates a MD5 hash of message, passed to the success callback as Base64-encoded `String`.

```js
var onSuccess = function(data) {
    console.log(data); // will output: uVkG1pQIp3QD8df1FdvMBw==
};

var onError = function(message) {
    /* Oops, something goes wrong... */
};

security.digest.md5digest('rduk', onSuccess, onError);
```

<a name="module_security.digest.sha1digest"></a>
#### security.digest.sha1digest(message, successCallback, errorCallback)

Generates a SHA-1 hash of message, passed to the success callback as Base64-encoded `String`.

```js
var onSuccess = function(data) {
    console.log(data); // will output: xhZASyUDRjjsAZVzRKp16OpERK0=
};

var onError = function(message) {
    /* Oops, something goes wrong... */
};

security.digest.sha1digest('rduk', onSuccess, onError);
```

<a name="module_security.digest.sha256digest"></a>
#### security.digest.sha256digest(message, successCallback, errorCallback)

Generates a SHA-256 hash of message, passed to the success callback as Base64-encoded `String`.

```js
var onSuccess = function(data) {
    console.log(data); // will output: qoNyp5fmrKx26h69RVjP/H4TtPQtxGXTWlDC/MRiCjo=
};

var onError = function(message) {
    /* Oops, something goes wrong... */
};

security.digest.sha256digest('rduk', onSuccess, onError);
```

<a name="module_security.message"></a>
### security.message

<a name="module_security.message.sign"></a>
#### security.message.sign(messageToSign, keyAlias, successCallback, errorCallback)

```js
var onSuccess = function(data) {
    /* ... */
};

var onError = function(message) {
    /* ... */
};

security.digest.sha256digest(
    'my message to sign',
    function(digest) {
        security.message.sign(
            digest, 'certificate alias...', onSuccess, onError);
    },
    , onError);
```

<a name="module_security.message.verify"></a>
#### security.message.verify(messageToVerify, signature, keyAlias, successCallback, errorCallback)

```js
var onSuccess = function(data) {
    /* ... */
};

var onError = function(message) {
    /* ... */
};

security.digest.sha256digest(
    'my message to verify',
    function(digest) {
        security.message.verify(
            digest, 'signature...', 'certificate alias...', onSuccess, onError);
    },
    , onError);
```
