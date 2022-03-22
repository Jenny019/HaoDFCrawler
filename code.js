system = require('system')  
address = system.args[1];
var page = require('webpage').create();  
var url = address;  
page.open(url, function (status) {  
    //Page is loaded!  
    if (status !== 'success') {  
        console.log('Unable to post!');  
    } else {  // Page is loaded successfully
            console.log(page.content);
            phantom.exit();
    } 
  });