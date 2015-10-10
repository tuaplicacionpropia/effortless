org.effortless.zkstrap.AbstractComponent = zk.$extends(zk.Widget, {
  _options : {},
  getOptions : function () {
  	return this._options;
  },
  setOptions : function (newValue) {
  console.log('setOptions = ' + newValue);
    this._options = jq.evalJSON(newValue);
  	return this._options;
  },
  
  getPropertyOptions: function (name) {
    var result = null;
    if (this._options && name) {
      var arrayName = name.split(".");
      if (arrayName.length > 0) {
        result = this._options;
        for (var i = 0; i < arrayName.length; i++) {
          if (result) {
            var itemName = arrayName[i];
          	result = result["" + itemName];
          }
          else {
          	break;
          }
        }
      }
    }
    return result;
  }

});
