org.effortless.zkstrap.Screen = zk.$extends(org.effortless.zkstrap.AbstractComponent, {

  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    
//    this.appendChild(new org.effortless.zkstrap.Layout());    
    
  },

  unbind_ : function(evt) {
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
