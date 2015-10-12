org.effortless.zkstrap.AdminApp = zk.$extends(org.effortless.zkstrap.AbstractComponent, {
  _label : '', // default value

  getLabel : function() {
    return this._label;
  },

  setLabel : function(newValue) {
    if (this._label != newValue) {
      this._label = newValue;
//      if (this.desktop) {
//        this.$n('mainBtn').innerHTML = zUtl.encodeXML(this._label);
        //this.$n('mainOpt').innerHTML = zUtl.encodeXML(this._label);
//      }
    }
  },
  
  _adminPage : '', // default value

  getAdminPage : function() {
    return this._adminPage;
  },

  setAdminPage : function(newValue) {
    if (this._adminPage != newValue) {
      this._adminPage = newValue;
      if (this.desktop) {
      console.log('app rerender begin');
//        this.rerenderNow_();
        this.rerender(-1);
      console.log('app rerender end');
      }
    }
  },
  
  _skin : '', // default value

  getSkin : function() {
    return this._skin;
  },

  setSkin : function(newValue) {
    if (this._skin != newValue) {
      this._skin = newValue;
      this.updateSkin_();
    }
  },  
  
  updateSkin_ : function() {
    var _skin = this._skin;
    if (!_skin) {
      _skin = 'skin-blue';
    }
    document.body.className = _skin + ' ' + 'sidebar-mini' + ' ' + 'wysihtml5-supported';
  },
  
  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    this.updateSkin_();
    this._loadI18nLocale();
    this._manageNavigateButtons();
    this._bindLoginPage();
  },

  unbind_ : function(evt) {
    this._unbindLoginPage();
    this.$supers('unbind_', arguments);
  },

  _getNodeInputLogin: function() {
    return jq('#' + this.uuid + '-inputlogin').get()[0];
  },
  
  _getNodeInputPassword: function() {
    return jq('#' + this.uuid + '-inputpassword').get()[0];
  },
  
  _getNodeInputRememberMe: function() {
    return jq('#' + this.uuid + '-inputrememberme').get()[0];
  },
  
  _getNodeBtnLogin: function() {
    return jq('#' + this.uuid + '-btnlogin').get()[0];
  },

  _bindLoginPage : function() {
    this.domListen_(this.$n('btnlogin'), "onClick", '_doRunCheckLogin');
  },

  _doRunCheckLogin: function(evt) {
    var _login = this._getNodeInputLogin().value;
    var _password = this._getNodeInputPassword().value;
    var _rememberMe = (this._getNodeInputRememberMe().checked ? 'true' : 'false');
    this.fire('onLogin', {login: _login, password: _password, rememberMe: _rememberMe}, {toServer: true});
  },

  _unbindLoginPage : function() {
    this.domUnlisten_(this.$n('btnlogin'), "onClick", '_doRunCheckLogin');
  },

  _manageNavigateButtons : function () {
    window.onpopstate = function(event) {
      alert("location: " + document.location + ", state: " + JSON.stringify(event.state));
    };
  },
  
  _loadI18nLocale : function () {
//	var locale = 'es';
//	jq.getJSON('locales/' + locale + '.json', function(data) {
//    	window.polyglot = new Polyglot({phrases: data});
// 	});
  	//console.log('>>>>>>>>>>>>>>>> ' + window.polyglot.t('page'));
  },

  _getMenuNode: function() {
    return jq('#' + this.uuid + '-menu').get()[0];
  },

  _getContentNode: function() {
    return jq('#' + this.uuid + '-main-content').get()[0];
  },

  _listContent: [],

  _addChild: function (child) {
  console.log('addChild');
    var checkMenu = (child instanceof org.effortless.zkstrap.MenuBs);
    var _parentNode = null;
	if (checkMenu) {
	  _parentNode = this._getMenuNode();
	}
	else {
	  _parentNode = this._getContentNode();
	}
	if (!checkMenu) {
//		if (this._listContent.length > 0) {
//		  this._listContent[this._listContent.length - 1].setVisible(false);
//		}
		this._listContent.push(child);
	}
//	this.removeChildHTML_(child);
//	jq(_parentNode).append(child.redrawHTML_());
	jq(_parentNode).append(child.$n());
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
  
});
