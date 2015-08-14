org.effortless.zkstrap.MenuBs = zk.$extends(zk.Widget, {
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
  
  _name : '', // default value

  getName : function() {
    return this._name;
  },

  setName : function(newValue) {
    if (this._name != newValue) {
      this._name = newValue;
    }
  },
  
  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    if (!this.isMainMenu()) {
	  var _link = this._getNodeLink();
      if (this.nChildren > 0) {
        this.domListen_(_link, "onClick", '_doOpenCloseMenuBs');
      }
      else {
        this.domListen_(_link, "onClick", '_doOpenOptionMenuBs');
      }
    }
  },

  unbind_ : function(evt) {
    if (!this.isMainMenu()) {
	  var _link = this._getNodeLink();
      if (this.nChildren > 0) {
        this.domUnlisten_(_link, "onClick", '_doOpenCloseMenuBs');
      }
      else {
        this.domUnlisten_(_link, "onClick", '_doOpenOptionMenuBs');
      }
    }

    this.$supers('unbind_', arguments);
  },

  _doOpenOptionMenuBs: function(evt) {
    //this.$supers('doClick_', arguments);
    this.doClick_(arguments);
//    console.log('clik option');
//    console.log('clik option ' + evt.domTarget.innerHTML);
  },
  
  doClick_: function (evt) {
    if (!(!this.isMainMenu() && this.nChildren <= 0)) {
      evt.stop();
    }
    this.$supers('doClick_', arguments);
  },
  
  _getNodeLink: function() {
    return jq('#' + this.uuid + ' > a').get()[0];
  },
  
  _getNodeSubmenu: function() {
    return jq('#' + this.uuid + ' > .treeview-menu').get()[0];  
  },
  
  _doOpenCloseMenuBs: function(evt) {
    console.log('clik menu');
    console.log('clik menu ' + evt.domTarget.innerHTML);
    
    var _nMenu = this._getNodeSubmenu();
    
    var _isVisible = jq(_nMenu).is(':visible');
    
    if (_isVisible) {
      jq(_nMenu).slideUp('normal', function () {
        jq(_nMenu).removeClass('menu-open');
      });
      jq(_nMenu).parent("li").removeClass("active");
    }
    else {
//      var parent = jq(evt.domTarget).parents('ul').first();
//      var ul = jq(parent).find('ul:visible').slideUp('normal');
//      jq(ul).removeClass('menu-open');
//      var parent_li = jq(evt.domTarget).parent("li");

      jq(_nMenu).slideDown('normal', function () {
        jq(_nMenu).addClass('menu-open');
//        jq(parent).find('li.active').removeClass('active');
//        jq(parent_li).addClass('active');
      });
    }

    evt.stop();
  },
  
  isMainMenu: function () {
  	return !(this.parent.className == 'org.effortless.zkstrap.MenuBs');
  },
  
  getEncodedLabel: function () {
  	var result = '';
  	result = (this._label != null ? this._label.trim() : '');
  	if (result.length <= 0 && this._name != null && this._name.length > 0) {
  		var _fNode = _APP_I18N[this._name];
		if (!(typeof _fNode === 'undefined' || _fNode === null)) {
	  		result = _fNode['menu'];
  		}
		result = (result.length > 0 ? result : this._name.capitalize());
  	}
  	result = zUtl.encodeXML(result);
  	return result;
  }
  
});


/*    
    var self = this;
	jq('#' + this.uuid + " > .treeview").each(function(index, el) {
      console.log( index + ": " + self.$n().innerHTML);
      console.log( index + ": " + el.innerHTML);
      self.domListen_(this, 'onClick', '_doOpenCloseMenuBs');
    });
*/



/*

jq(this.$n()).find('.treeview').on('click', function() {
  
});

treeview-menu

jq(n).find('img:first')


$.AdminLTE = {};

$.AdminLTE.options = {

};


$(function () {


  //Set up the object
  _init();

  //Enable sidebar tree view controls
  $.AdminLTE.tree('.sidebar');

});

function _init() {



  $.AdminLTE.tree = function (menu) {
    var _this = this;

    jq("li a", this.$n()).on('click', function (e) {
      var $this = $(this);
      var checkElement = $this.next();
      
      Close Menu
      //Check if the next element is a menu and is visible
      if ((checkElement.is('.treeview-menu')) && (checkElement.is(':visible'))) {
        //Close the menu
        checkElement.slideUp('normal', function () {
          checkElement.removeClass('menu-open');
        });
        checkElement.parent("li").removeClass("active");
      }
      //If the menu is not visible
      else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
        //Get the parent menu
        var parent = $this.parents('ul').first();
        //Close all open menus within the parent
        var ul = parent.find('ul:visible').slideUp('normal');
        //Remove the menu-open class from the parent
        ul.removeClass('menu-open');
        //Get the parent li
        var parent_li = $this.parent("li");

        //Open the target menu and add the menu-open class
        checkElement.slideDown('normal', function () {
          //Add the class active to the parent li
          checkElement.addClass('menu-open');
          parent.find('li.active').removeClass('active');
          parent_li.addClass('active');
        });
      }
      //if this isn't a link, prevent the page from being redirected
      if (checkElement.is('.treeview-menu')) {
        e.preventDefault();
      }
    });
  };

}



*/

