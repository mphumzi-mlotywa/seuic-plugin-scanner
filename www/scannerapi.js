cordova.define("seuic-plugin-scanner.scanner", function(require, exports, module) {   
      
    var exec = require('cordova/exec');
    module.exports = {  
      
        /** 
         * 一共5个参数 
           第一个 :成功会掉 
           第二个 :失败回调 
           第三个 :将要调用的类的配置名字(在config.xml中配置 稍后在下面会讲解) 
           第四个 :调用的方法名(一个类里可能有多个方法 靠这个参数区分) 
           第五个 :传递的参数  以json的格式 
         */  
        openScanner: function() {  
            exec(null, null, "Scanner", "openScanner", []);  
        },
		closeScanner: function() {  
            exec(null, null, "Scanner", "closeScanner", []);  
        },
        startScan: function() {  
            exec(null, null, "Scanner", "startScan", []);  
        },
		stopScan: function() {  
            exec(null, null, "Scanner", "stopScan", []);  
        },
		setDecodeCallback: function(decodeCallback){
			exec(decodeCallback, null, "Scanner", "setDecodeCallback", []);  
		}
    };  
      
    }); 
