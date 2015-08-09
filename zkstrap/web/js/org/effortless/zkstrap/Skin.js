org.effortless.zkstrap.Skin = zk.$extends(zk.Widget, {
  _name : '', // default value

  getName : function() {
    return this._name;
  },

  setName : function(newValue) {
    if (this._name != newValue) {
      this._name = newValue;
//      console.log('HLEOOO!2');
//       	this.parent.parent.setSclass(this._name);
//       	this.parent.parent.setSclass('skin-blue');
//        document.body.addClass(this._name);
//        document.body.addClass('skin-blue');
        document.body.className = this._name;
//      console.log('HLEOOO!3');
//      if (this.desktop) {
//      }
    }
  }
  
});
