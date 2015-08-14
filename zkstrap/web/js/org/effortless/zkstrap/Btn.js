org.effortless.zkstrap.Btn = zk.$extends(zk.Widget, {
  _label : '', // default value

  getLabel : function() {
    return this._label;
  },

  setLabel : function(newValue) {
    if (this._label != newValue) {
      this._label = newValue;
      if (this.desktop) {
        this._updateMainBtn();
      }
    }
  },
  
  _name : '', // default value

  getName : function() {
    return this._name;
  },

  setName : function(newValue) {
    if (this._name != newValue) {
      this._name = newValue;
      if (this.desktop) {
        this._updateMainBtn();
      }
    }
  },
  
  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    this.domListen_(this.$n('mainBtn'), "onClick", '_doRunBtnBs');
//    this.domListen_(this.$n('menuitem0'), "onClick", '_doRunBtnOptBs');
//    this.domListen_(this.$n('menuitem1'), "onClick", '_doRunBtnOptBs');
//    this.domListen_(this.$n('menuitem2'), "onClick", '_doRunBtnOptBs');
//    this.domListen_(this.$n('menuitem3'), "onClick", '_doRunBtnOptBs');
  },
  
  unbind_ : function(evt) {
    this.domUnlisten_(this.$n('mainBtn'), "onClick", '_doRunBtnBs');
//    this.domUnlisten_(this.$n('menuitem0'), "onClick", '_doRunBtnOptBs');
//    this.domUnlisten_(this.$n('menuitem1'), "onClick", '_doRunBtnOptBs');
//    this.domUnlisten_(this.$n('menuitem2'), "onClick", '_doRunBtnOptBs');
//    this.domUnlisten_(this.$n('menuitem3'), "onClick", '_doRunBtnOptBs');

    this.$supers('unbind_', arguments);
  },
  
  getMainBtnLabel: function () {
  	var result = '';
  	result = (this._label != null ? this._label.trim() : '');
  	if (result.length <= 0 && this._name != null && this._name.length > 0) {
  	    var _fName = this._name;
  	    _fName = (_fName != null ? _fName.trim() : '');
	   	_fName = (_fName.indexOf('#') == 0 ? _fName.substring(1) : _fName);
  		var _fNode = _APP_I18N[_fName];
		if (!(typeof _fNode === 'undefined' || _fNode === null)) {
	  		result = _fNode['label'];
  		}
		result = (result.length > 0 ? result : _fName.capitalize());
  	}
  	result = zUtl.encodeXML(result);
  	return result;
  },
  
  _getMainBtnNode: function() {
    return jq('#' + this.uuid + '-mainBtn').get()[0];
  },
  
  _updateMainBtn: function() {
  	var node = this._getMainBtnNode();
  	var label = this.getMainBtnLabel();
  	node.innerHTML = label;
  },

  
  
  _doRunBtnBs: function(evt) {
//	var locale = 'es';
//	jq.getJSON('locales/' + locale + '.json', function(data) {
//		console.log('>>>>>>>>>>>>>>>> DFG');
//    	window.polyglot = new Polyglot({phrases: data});
//		console.log('>>>>>>>>>>>>>>>> ' + window.polyglot.t('page'));
// 	});
    this.fire('onClick', {name: this._name}, {toServer: true});
  }, 
  
  _doRunBtnOptBs: function(evt) {
    console.log('run opt');
    this.setLabel(evt.domTarget.innerHTML);
    console.log('run opt2');
//    this._doRunBtnBs(evt);
    console.log('run opt3');
  }

});
