org.effortless.zkstrap.SimpleButton = zk.$extends(zk.Widget, {
  _value : '', // default value
  _cleared : false, // default value

  getValue : function() {
    return this._value;
  },

  setValue : function(value) {
    if (this._value != value) {
      this._value = value;
      if (this.desktop)
        this.$n().innerHTML = zUtl.encodeXML(value);
    }
  },
  
  getCleared : function() {
    return this._cleared;
  },

  setCleared : function(cleared) {
    if (this._cleared != cleared) {
      this._cleared = cleared;
    }
  },
  
  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    this.domListen_(this.$n().lastChild, "onClick", '_doClear');
  },
  
  unbind_ : function(evt) {
    this.domUnlisten_(this.$n().lastChild, "onClick", '_doClear');
    this.$supers('unbind_', arguments);
  },
  
  _doClear: function(evt) {
    this._cleared = !(this._cleared);
    if(this._cleared) {
      this.$n().firstChild.innerHTML = this._value;
    } else {
      this.$n().firstChild.innerHTML = "";
    }
//    console.log('holita4');
//    this.fire('onClear', {cleared: this._cleared});
//      this.fire("onClear", {cleared: 'perico'});
//    console.log('holita4 end');
//    this.fire('onClear');
//    evt.stop();
    this.fire('onClear', {cleared: this._cleared}, {toServer: true});
//      this.smartUpdate('cleared', this._cleared, 10);
//    zAu.send(new zk.Event(wgt, "onClear", {cleared: this._cleared}, {toServer:true}));    
  }
  
});
