cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "cordova-plugin-device.device",
        "file": "plugins/cordova-plugin-device/www/device.js",
        "pluginId": "cordova-plugin-device",
        "clobbers": [
            "device"
        ]
    },
    {
        "id": "com.napolitano.cordova.plugin.intent.IntentPlugin",
        "file": "plugins/com.napolitano.cordova.plugin.intent/www/android/IntentPlugin.js",
        "pluginId": "com.napolitano.cordova.plugin.intent",
        "clobbers": [
            "IntentPlugin"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.2",
    "cordova-plugin-device": "1.1.6",
    "cordova-custom-config": "3.1.4",
    "com.napolitano.cordova.plugin.intent": "0.1.3",
    "cordova-plugin-console": "1.0.7"
};
// BOTTOM OF METADATA
});