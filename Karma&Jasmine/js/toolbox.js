// js module
exports.toolbox = function() {
  // some sample function - this function will not be exported
    

    let censor = function(x){
      let ret = "";
      let original;
      original = x;
      if(x == 'kurka'){
        for (let i = 0; i < x.length; i++)
          ret = '' + 'x' + ret;
        return ret;
      }
      else{
        return original;
      }
    };

    let checklenght = function(x){
      let ret = "";
      let original = "";
      original = x;

      if(x.lenght < 1 || x.length > 4){
        ret = 'wrong number';
        return ret;
      }
      else{
        return original;
      }

    };

    let check_username_lenght = function(x){
      let ret = "";
      let original = "";
      original = x;

      if(x.length > 10){
        ret = 'wrong';
        return ret;
      }
      else{
        return original;
      }

    };

    let lowercase = function(x){
      let ret = x.toLowerCase();
      return ret;
    };


    let validate_username = function(x){
      original = x;
      user = x;
      val = x.value;
      
      if(censor(val) == 'xxxxx'){
        user.value = 'Wrong username';
        user.class = 'Invalid'
        return user;
      }
      else if(check_username_lenght(val) == 'wrong'){
        user.value = 'Wrong username';
        user.class = 'Invalid'
        return user;
      }
      else{
        return original;
      }
      
    };
    
    return {


      censor:function(x){
        if (x.selector) {
          document.querySelectorAll(x.selector).forEach(
            (element) => {
              element.innerHTML = censor(element.innerHTML);
            }
          );
          return {};
        } else {
          return censor(x);
        }
      },

      checklenght:function(x){
        if (x.selector) {
          document.querySelectorAll(x.selector).forEach(
            (element) => {
              element.innerHTML = checklenght(element.innerHTML);
            }
          );
          return {};
        } else {
          return checklenght(x);
        }
      },

      lowercase:function(x){
        if (x.selector) {
          document.querySelectorAll(x.selector).forEach(
            (element) => {
              element.innerHTML = lowercase(element.innerHTML);
            }
          );
          return {};
        } else {
          return lowercase(x);
        }
      },

      validate_username:function(x){
        if (x.selector) {
          document.querySelectorAll(x.selector).forEach(
            (element) => {
              element.innerHTML = validate_username(element.innerHTML);
            }
          );
          return {};
        } else {
          return validate_username(x);
        }
      }
    };
  };
