/**
 * MIT License
 *
 * Copyright (c) 2016 Kim UNG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
var argscheck = require('cordova/argscheck'), 
    exec = require('cordova/exec');

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

var md5digest = function(message, successCallback, errorCallback) {
    argscheck.checkArgs('SFF', 'security.digest.md5digest', arguments);
    exec(successCallback, errorCallback, "Security", "md5digest", [message]);
};

var sha1digest = function(message, successCallback, errorCallback) {
    argscheck.checkArgs('SFF', 'security.digest.sha1digest', arguments);
    exec(successCallback, errorCallback, "Security", "sha1digest", [message]);
};

var sha256digest = function(message, successCallback, errorCallback) {
    argscheck.checkArgs('SFF', 'security.digest.sha256digest', arguments);
    exec(successCallback, errorCallback, "Security", "sha256digest", [message]);
};

var securityExport = {
    certificate: {
        generate: generateCertificate
    },
    message: {
        sign: signMessage,
        verify: verifyMessage
    },
    digest: {
        md5digest: md5digest,
        sha1digest: sha1digest,
        sha256digest: sha256digest
    }
};

module.exports = securityExport;
