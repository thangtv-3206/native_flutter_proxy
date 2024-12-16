package com.victorblaess.native_flutter_proxy

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.*

/** FlutterProxyPlugin */
public class FlutterProxyPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var mMethodChannel : MethodChannel

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mMethodChannel = MethodChannel(binding.binaryMessenger, "native_flutter_proxy")
        mMethodChannel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        mMethodChannel.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "getProxySetting") {
            result.success(getProxySetting())
        } else {
            result.notImplemented()
        }
    }

    private fun getProxySetting(): Any? {
        val map = LinkedHashMap<String, Any?>()
        map["host"] = System.getProperty("http.proxyHost")
        map["port"] = System.getProperty("http.proxyPort")
        return map
    }

}
