org.effortless.zkstrap.ButtonBs = zk.$extends(zk.Widget, {
  _label : '', // default value

  getLabel : function() {
    return this._label;
  },

  setLabel : function(newValue) {
    if (this._label != newValue) {
      this._label = newValue;
      if (this.desktop) {
        this.$n('mainBtn').innerHTML = zUtl.encodeXML(this._label);
        //this.$n('mainOpt').innerHTML = zUtl.encodeXML(this._label);
      }
    }
  },
  
  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    this.domListen_(this.$n('mainBtn'), "onClick", '_doRunBtnBs');
    this.domListen_(this.$n('menuitem0'), "onClick", '_doRunBtnOptBs');
    this.domListen_(this.$n('menuitem1'), "onClick", '_doRunBtnOptBs');
    this.domListen_(this.$n('menuitem2'), "onClick", '_doRunBtnOptBs');
    this.domListen_(this.$n('menuitem3'), "onClick", '_doRunBtnOptBs');
  },
  
  unbind_ : function(evt) {
    this.domUnlisten_(this.$n('mainBtn'), "onClick", '_doRunBtnBs');
    this.domUnlisten_(this.$n('menuitem0'), "onClick", '_doRunBtnOptBs');
    this.domUnlisten_(this.$n('menuitem1'), "onClick", '_doRunBtnOptBs');
    this.domUnlisten_(this.$n('menuitem2'), "onClick", '_doRunBtnOptBs');
    this.domUnlisten_(this.$n('menuitem3'), "onClick", '_doRunBtnOptBs');


    this.$supers('unbind_', arguments);
  },
  
  _doRunBtnBs: function(evt) {
//	var locale = 'es';
//	jq.getJSON('locales/' + locale + '.json', function(data) {
//		console.log('>>>>>>>>>>>>>>>> DFG');
//    	window.polyglot = new Polyglot({phrases: data});
//		console.log('>>>>>>>>>>>>>>>> ' + window.polyglot.t('page'));
// 	});
    this.fire('onClick', {label: this._label}, {toServer: true});
  }, 
  
  _doRunBtnOptBs: function(evt) {
    console.log('run opt');
    this.setLabel(evt.domTarget.innerHTML);
    console.log('run opt2');
//    this._doRunBtnBs(evt);
    console.log('run opt3');
  }
  
});
