org.effortless.zkstrap.Input = zk.$extends(zk.Widget, {
  _settings : {},
  getSettings : function () {
  	return this._settings;
  },
  setSettings : function (newValue) {
  console.log('setSettings = ' + newValue);
    this._settings = jq.evalJSON(newValue);
  	return this._settings;
  },
  
  getPropertySettings: function (name) {
    var result = null;
    if (this._settings && name) {
      var arrayName = name.split(".");
      if (arrayName.length > 0) {
        result = this._settings;
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
  },

  _value : '', // default value
  getValue : function() {
    return this._value;
  },

  setValue : function(value) {
//    console.log('SET VALUE START');
    if (this._value != value) {
//	    console.log('SET VALUE NE');
//	    console.log('SET VALUE NE value = ' + value);
      this._value = value;
//	    console.log('SET VALUE ASSIGN value = ' + value);
      if (this.desktop) {
//	    console.log('SET VALUE DESKTOP');
	  	var _textNode = this._getTextNode();
//	    console.log('SET VALUE GetTEXTNODE');
	  	var _type = this.getType();
//	    console.log('SET VALUE GETTYPE');
		  	
//	    console.log('SET VALUE value = ' + value);
	  	if (_type == 'checkbox') {
	  		_textNode.checked = value;
		}
		else if (!(_type == 'table')) {
	  		_textNode.value = value;
        }
      }
	}
//    console.log('SET VALUE END');
  },
  
  _skin : '',

  getSkin : function() {
    return this._skin;
  },

  setSkin : function(newValue) {
    if (this._skin != newValue) {
      this._skin = newValue;
    }
  },
  
  _loadSkin : function() {
  	var result = this._skin;
  	result = (result.length > 0 ? result : 'minimal');
  	return result;
  },

  getEncodedValue : function(value) {
  	return this.getValue();
  },

  _type : '',

  getType : function() {
    return this._type;
  },

  setType : function(newValue) {
    if (this._type != newValue) {
      this._type = newValue;
    }
  },

  _label : '',

  getLabel : function() {
    return this._label;
  },

  setLabel : function(newValue) {
    if (this._label != newValue) {
      this._label = newValue;
    }
  },

  _properties : '',

  getProperties : function() {
    return this._properties;
  },

  setProperties : function(newValue) {
    if (this._properties != newValue) {
      this._properties = newValue;
    }
  },

  _name : '',

  getName : function() {
    return this._name;
  },

  setName : function(newValue) {
    if (this._name != newValue) {
      this._name = newValue;
    }
  },

  getTagLabel: function () {
  	var result = '';
  	result = (this._label != null ? this._label.trim() : '');
  	if (result.length <= 0 && this._name != null && this._name.length > 0) {
  		var _fNode = _APP_I18N[this._name];
		if (!(typeof _fNode === 'undefined' || _fNode === null)) {
	  		result = _fNode['label'];
  		}
		result = (result.length > 0 ? result : this._name.capitalize());
  	}
  	result = zUtl.encodeXML(result);
  	return result;
  },
  
  getLabelColumn: function (property) {
  	var result = '';
	result = property.capitalize();
  	result = zUtl.encodeXML(result);
  	return result;
  },
  
  getColumnNames: function () {
  	var result = [];
  	var properties = this._properties.split(',');
  	for (var i = 0; i < properties.length; i++) {
  		var label = this.getLabelColumn(properties[i]);
	  	result.push(label);
  	}
//  	result.push('Columna2');
  	return result;
  },
  
  getCellValues: function () {
  	var result = [];
  	result = this._value;
/*
  	for (var i = 1; i <= 5; i++) {
	  	var item = [];
	  	item.push('Valor' + i + '.1');
	  	item.push('Valor' + i + '.2');
	  	item.push('Valor' + i + '.3');
	  	item.push('Valor' + i + '.4');
	  	item.push('Valor' + i + '.5');
	  	result.push(item);
	}
*/
  	return result;
  },
  
  
  
  _values : [],

  getValues : function() {
    return this._values;
  },

  setValues : function(newValue) {
    if (this._values != newValue) {
      this._values = newValue;
    }
  },
  
  
  _getTextNode: function() {
    return jq('#' + this.uuid + '-input').get()[0];
  },
  
  
  
  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    
  	var _type = this.getType();
  	
  	if (_type == 'checkbox') {
		var _skin = this._loadSkin();
	    var options = {checkboxClass: "icheckbox_" + _skin, radioClass: "iradio_minimal", increaseArea: "20%"};
		jq('#' + this.uuid + '-input').iCheck(options);
		var _self = this;
		jq('#' + this.uuid + '-input').on("ifToggled", function() { _self._checkIfToggled(); });
	}
	else if (_type == 'radio') {
		var _skin = this._loadSkin();
		var _values = this.getValues();
		for	(var index = 0; index < _values.length; index++) {
            jq('#' + this.uuid + '-input_' + index).iCheck({radioClass: "iradio_" + _skin, increaseArea: "20%"});
			var _self = this;
			jq('#' + this.uuid + '-input_' + index).on("ifToggled", function() { _self._checkIfToggled(); });
		}
/*		
		for	(var index = 0; index < _values.length; index++) {
			var _inputNode = jq('#' + this.uuid + '-input_' + index).get()[0];
	    	this.domListen_(_inputNode, "onChange", '_doBlur');
		}
*/
	}	
	else if (_type == 'color' || _type == 'colour' || _type == 'colorpicker' || _type == 'colourpicker') {
        jq('#' + this.uuid).colorpicker();
    	this.domListen_(this._getTextNode(), "onBlur", '_doBlur');
	}	
	else if (!(_type == 'table')) {
	    var _node = this._getTextNode();
	    _node.value = this.getValue();
    	this.domListen_(_node, "onBlur", '_doBlur');
	}
	else if (_type == 'table') {
		var cellValues = this.getCellValues();
		for	(var index = 0; index < cellValues.length; index++) {
            jq('#' + this.uuid + '-radioselect_' + index).iCheck({radioClass: "iradio_minimal", increaseArea: "10%"});
			var _self = this;
			jq('#' + this.uuid + '-radioselect_' + index).on("ifToggled", function() { _self._selectIndex(this.value); });
		}

		if (true) {
    	  this.domListen_(jq('#' + this.uuid + '-btnCreate').get()[0], 'onClick', '_doClickBtnCreateTable');
    	  this.domListen_(jq('#' + this.uuid + '-btnRead').get()[0], 'onClick', '_doClickBtnReadTable');
    	  this.domListen_(jq('#' + this.uuid + '-btnUpdate').get()[0], 'onClick', '_doClickBtnUpdateTable');
    	  this.domListen_(jq('#' + this.uuid + '-btnDelete').get()[0], 'onClick', '_doClickBtnDeleteTable');
		}
		
	}
  },
  
  unbind_ : function(evt) {
  	var _type = this.getType();
  	
  	if (_type == 'checkbox') {
	}
	else if (_type == 'table') {
		if (true) {
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnCreate').get()[0], 'onClick', '_doClickBtnCreateTable');
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnRead').get()[0], 'onClick', '_doClickBtnReadTable');
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnUpdate').get()[0], 'onClick', '_doClickBtnUpdateTable');
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnDelete').get()[0], 'onClick', '_doClickBtnDeleteTable');
		}
	}
	else {
	    this.domUnlisten_(this._getTextNode(), "onBlur", '_doBlur');
	}
    this.$supers('unbind_', arguments);
  },
  
  _checkIfToggled : function () {
  	this._doBlur();
  }, 

  _selectIndex: function(idx) {
  	var _type = this.getType();
  	if (_type == 'table') {
    	this.fire('onReq', {command:'onSelect', value: idx}, {toServer: true});
	}
  },

  _doClickBtnCreateTable: function(evt) {
    this.fire('onReq', {command:'onCreateItem'}, {toServer: true});
  }, 

  _doClickBtnReadTable: function(evt) {
    this.fire('onReq', {command:'onReadItem'}, {toServer: true});
  }, 

  _doClickBtnUpdateTable: function(evt) {
    this.fire('onReq', {command:'onUpdateItem'}, {toServer: true});
  }, 

  _doClickBtnDeleteTable: function(evt) {
    this.fire('onReq', {command:'onDeleteItem'}, {toServer: true});
  }, 

  _doBlur: function(evt) {
  	var _textNode = this._getTextNode();
  	var _textNodeValue = null;
  	var _type = this.getType();
  	var flag = false;
  	
  	if (_type == 'checkbox') {
  		_textNodeValue = _textNode.checked;
  		flag = true;
	}
  	else if (_type == 'table') {
	}
	else {
  		_textNodeValue = _textNode.value;
  		flag = true;
	}
	if (flag && this._value != _textNodeValue) {
		this._value = _textNodeValue;
    	this.fire('onReq', {command:'onChange', value: _textNodeValue}, {toServer: true});
    }
  }

});
