/*
  Copyright 2016 Kim UNG

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
var argscheck = require('cordova/argscheck')
  , exec = require('cordova/exec');

var generateCertificate = function(alias, successCallback, errorCallback) {
    argscheck.checkArgs('SFF', 'security.certificate.generate', arguments);
    exec(successCallback, errorCallback, "Security", "generate", [alias]);
};

var signMessage = function(textToSign, alias, successCallback, errorCallback) {
    argscheck.checkArgs('SSFF', 'security.message.sign', arguments);
    exec(successCallback, errorCallback, "Security", "getSignature", [textToSign, alias]);
};

var verifyMessage = function(message, signature, alias, successCallback, errorCallback) {
    argscheck.checkArgs('SSSFF', 'security.message.verify', arguments);
    exec(successCallback, errorCallback, "Security", "verifySignature", [message, signature, alias]);
};

var securityExport = {
    certificate: {
        generate: generateCertificate
    },
    message: {
        sign: signMessage,
        verify: verifyMessage
    }
};

module.exports = securityExport;
