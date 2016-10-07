# RDUK - Security Cordova Plugin

## Installation

```
cordova plugin add https://github.com/rd-uk/cordova-plugin-rduksecurity.git
```
---

# API Reference <a name="reference"></a>


* [security](#module_security)
    * [.generate(name, successCallback, errorCallback)](#module_security.generate)


---

<a name="module_security"></a>

## security
<a name="module_security.generate"></a>

### security.generate(name, successCallback, errorCallback)
Generates a certificate. The public key is passed to the success callback as a
Base64-encoded `String`.