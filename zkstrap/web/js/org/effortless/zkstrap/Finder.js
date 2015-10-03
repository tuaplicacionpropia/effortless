org.effortless.zkstrap.Finder = zk.$extends(org.effortless.zkstrap.Screen, {

  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    
//    this.appendChild(new org.effortless.zkstrap.Layout());    
    
		if (true) {
    	  this.domListen_(jq('#' + this.uuid + '-btnSearch').get()[0], 'onClick', '_doClickBtnSearch');

    	  this.domListen_(jq('#' + this.uuid + '-btnCreate').get()[0], 'onClick', '_doClickBtnCreate');
    	  this.domListen_(jq('#' + this.uuid + '-btnRead').get()[0], 'onClick', '_doClickBtnRead');
    	  this.domListen_(jq('#' + this.uuid + '-btnUpdate').get()[0], 'onClick', '_doClickBtnUpdate');
    	  this.domListen_(jq('#' + this.uuid + '-btnDelete').get()[0], 'onClick', '_doClickBtnDelete');
		}
  },

  _doClickBtnSearch: function(evt) {
    this.fire('onRequest', {command:'search'}, {toServer: true});
  }, 

  _doClickBtnCreate: function(evt) {
    this.fire('onRequest', {command:'create'}, {toServer: true});
  }, 

  _doClickBtnRead: function(evt) {
    this.fire('onRequest', {command:'read'}, {toServer: true});
  }, 

  _doClickBtnUpdate: function(evt) {
    this.fire('onRequest', {command:'update'}, {toServer: true});
  }, 

  _doClickBtnDelete: function(evt) {
    this.fire('onRequest', {command:'delete'}, {toServer: true});
  }, 

  unbind_ : function(evt) {
		if (true) {
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnSearch').get()[0], 'onClick', '_doClickBtnSearch');

    	  this.domUnlisten_(jq('#' + this.uuid + '-btnCreate').get()[0], 'onClick', '_doClickBtnCreate');
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnRead').get()[0], 'onClick', '_doClickBtnRead');
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnUpdate').get()[0], 'onClick', '_doClickBtnUpdate');
    	  this.domUnlisten_(jq('#' + this.uuid + '-btnDelete').get()[0], 'onClick', '_doClickBtnDelete');
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
