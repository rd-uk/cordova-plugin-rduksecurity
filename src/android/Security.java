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
package com.rduk.security;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.util.Base64;

import com.rduk.security.util.Digest;
import com.rduk.security.util.Signing;

public class Security extends CordovaPlugin {
    
    private interface SecurityOp {
        void run(JSONArray args) throws Exception;
    }

    /**
     * Ctor.
     */
    public Security() {
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d("Rduk.Security", action);

        if (action.equals("generate")) {
            this.generate(args, callbackContext);
        }
        else if (action.equals("getSignature")) {
            this.getSignature(args, callbackContext);
        }
        else if (action.equals("verifySignature")) {
            this.verifySignature(args, callbackContext);
        }
        else if (action.equals("md5digest")) {
            this.md5digest(args, callbackContext);
        }
        else if (action.equals("sha1digest")) {
            this.sha1digest(args, callbackContext);
        }
        else if (action.equals("sha256digest")) {
            this.sha256digest(args, callbackContext);
        }
        else {
            callbackContext.error("Rduk Security Exception: unknown action '" + action + "'!");
            return false;
        }

        return true;
    }

    private void generate(final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        final Context context = this.cordova.getActivity().getApplicationContext();

        this.threadHelper(new SecurityOp() {
            public void run(JSONArray args) throws Exception {
                String alias = args.getString(0);

                byte[] publicKey = Signing.generate(alias, context);
                String base64EncodedPublicKey = Base64.encodeToString(publicKey, Base64.NO_WRAP);

                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, base64EncodedPublicKey));
            }
        }, args, callbackContext);
    }

    private void getSignature(final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.threadHelper(new SecurityOp() {
            public void run(JSONArray args) throws Exception {
                String data = args.getString(0);
                String alias = args.getString(1);

                byte[] signature = Signing.sign(data, alias);
                String base64EncodedSignature = Base64.encodeToString(signature, Base64.NO_WRAP);

                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, base64EncodedSignature));
            }
        }, args, callbackContext);
    }

    private void verifySignature(final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.threadHelper(new SecurityOp() {
            public void run(JSONArray args) throws Exception {
                String message = args.getString(0);
                String signature = args.getString(1);
                String alias = args.getString(2);

                boolean valid = Signing.verify(message, Base64.decode(signature, Base64.NO_WRAP), alias);

                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, valid));
            }
        }, args, callbackContext);
    }

    private void md5digest(final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.threadHelper(new SecurityOp() {
            public void run(JSONArray args) throws Exception {
                String message = args.getString(0);

                callbackContext.sendPluginResult(
                    new PluginResult(
                        PluginResult.Status.OK,
                        Base64.encodeToString(Digest.digest(message, Digest.Algorithm.MD5), Base64.NO_WRAP)));
            }
        }, args, callbackContext);
    }

    private void sha1digest(final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.threadHelper(new SecurityOp() {
            public void run(JSONArray args) throws Exception {
                String message = args.getString(0);

                callbackContext.sendPluginResult(
                    new PluginResult(
                        PluginResult.Status.OK,
                        Base64.encodeToString(Digest.digest(message, Digest.Algorithm.SHA1), Base64.NO_WRAP)));
            }
        }, args, callbackContext);
    }

    private void sha256digest(final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.threadHelper(new SecurityOp() {
            public void run(JSONArray args) throws Exception {
                String message = args.getString(0);

                callbackContext.sendPluginResult(
                    new PluginResult(
                        PluginResult.Status.OK,
                        Base64.encodeToString(Digest.digest(message, Digest.Algorithm.SHA256), Base64.NO_WRAP)));
            }
        }, args, callbackContext);
    }

    private void threadHelper(final SecurityOp c, final JSONArray args, final CallbackContext callbackContext){
        this.cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    c.run(args);
                } catch ( Exception e) {
                    Log.d("Rduk Security Exception: ", e.getMessage(), e);
                    callbackContext.error("Rduk Security Exception: " + e.getMessage());
                }
            }
        });
    }
}
