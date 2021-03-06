org.effortless.zkstrap.ConfirmScreen = zk.$extends(org.effortless.zkstrap.Screen, {

  _type : '', // default value
  getType : function() {
    return this._type;
  },

  setType : function(newValue) {
    if (this._type != newValue) {
      this._type = newValue;
//      if (this.desktop) {
//      }
	}
  },

  _getContentText: function () {
  	var result = null;
    if (this._type == 'delete') {
      result = zUtl.encodeXML("¿Está seguro que desea eliminar el registro?");
    }
    return result;
  },

  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    
//    this.appendChild(new org.effortless.zkstrap.Layout());    
    
		if (true) {
    	  this.domListen_(jq('#' + this.uuid + '-btnOk').get()[0], 'onClick', '_doClickBtnOk');
    	  this.domListen_(jq('#' + this.uuid + '-btnCancel').get()[0], 'onClick', '_doClickBtnCancel');
		}
    
    
  },

  _doClickBtnOk: function(evt) {
    this.fire('onReq', {command:'ok'}, {toServer: true});
  }, 

  _doClickBtnCancel: function(evt) {
    this.fire('onReq', {command:'cancel'}, {toServer: true});
  }, 

  unbind_ : function(evt) {
		if (true) {
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnOk').get()[0], 'onClick', '_doClickBtnOk');
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnCancel').get()[0], 'onClick', '_doClickBtnCancel');
		}
  
  
    this.$supers('unbind_', arguments);
  }
  
/*
  _layout: null, 

  _addChild: function (child) {
    if (this._layout != null) {
      this._layout = new org.effortless.zkstrap.Layout();
      this.$supers('appendChild', this._layout);
    }
	jq(this._layout.$n()).append(child.$n());
  },

  insertBefore: function (child, sibling, ignoreDom) {
    this.$supers('insertBefore', arguments);
    this._addChild(child);
	return true;
  },
  
  appendChild: function (child, ignoreDom) {
    this.$supers('appendChild', arguments);
    this._addChild(child);
	return true;
  }
*/

  
  
});
