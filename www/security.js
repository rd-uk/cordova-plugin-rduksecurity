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
var argscheck = require('cordova/argscheck')
  , exec = require('cordova/exec');

/**
 * @description
 * Generates a certificate. The public key is passed to the success callback as a Base64-encoded `String`.
 *
 * __Note__: If a certificate with given alias already exists, the existing public key is returned.
 * 
 * __Supported Platforms__
 *
 * - Android
 *
 * @example
 * var success = function(publicKeyData) {
 *     console.log(publicKeyData); // base64
 * }
 *
 * var error = function(message) {
 *     console.log(message); // The message is provided by the device's native code.
 * }
 *
 * security.certificate.generate('myAlias', success, error);
 */
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

/**
 * @exports security
 */
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
