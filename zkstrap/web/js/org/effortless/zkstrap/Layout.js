org.effortless.zkstrap.Layout = zk.$extends(org.effortless.zkstrap.AbstractComponent, {
  _type : '',
  getType : function() {
    return this._type;
  },

  setType : function(newValue) {
    if (this._type != newValue) {
      this._type = newValue;
    }
  },
  
  _size : '', // default value
  getSize : function() {
    return this._size;
  },

  setSize : function(newValue) {
    if (this._size != newValue) {
      this._size = newValue;
    }
  },
  
  _loadSize: function() {
  	var result = 0;
  	result = this.getSize();
	result = (result > -1 ? result : 12);
  	return result;
  },
  
  _align: '',
  
  getAlign: function() {
  	return this._align;
  },
  
  setAlign: function(newValue) {
  	if (this._align != newValue) {
  		this._align = newValue;
  	}
  },
  
  _spans: '',
  
  getSpans: function() {
  	return this._spans;
  },
  
  setSpans: function(newValue) {
  	if (this._spans = newValue) {
  		this._spans = newValue;
  	}
  },
  
  _removeAllClasses: function (child, sclass) {
    jq(child).removeClass();
	//jq(child).removeClass(function(index, className) {
	//	return jq(this).prev().attr("class");
	//});
  },

  _applySpan: function (colspan, node) {
    	colspan = Math.min(colspan, 12);
    	if (node != null && colspan > 0) {
		    //var node = jq('#' + child.uuid + '').get()[0];
    		this._removeAllClasses(node, "col-");
    		var newClass = "col-md-" + colspan;
    		jq(node).addClass(newClass);
    	}
  },
    
  _applyOffset: function (offset, node) {
    	offset = Math.min(offset, 12);
    	if (node != null && offset > 0) {
		    //var node = jq('#' + child.uuid + '').get()[0];
    		this._removeAllClasses(node, "col-md-offset");
    		var newClass = "col-md-offset-" + offset;
    		jq(node).addClass(newClass);
    	}
  },

  bind_ : function(evt) {
    this.$supers('bind_', arguments);
    this._updateSpans();
  },

  _updateSpans: function () {
    	this._spans = (this._spans != null ? this._spans.trim() : "");
    	
    	var childrenDiv = [];
    	var children = [];
		jq('#' + this.uuid + " > div").each(function(index, el) {
			childrenDiv.push(el);
	    });
	    for (var w = this.firstChild; w; w = w.nextSibling) {
			children.push(w);
	    }
	    var widgets = [];
	    for (var i = 0; i < children.length; i++) {
	    	var widget = children[i];
	    	if (widget.$instanceof(org.effortless.zkstrap.Input)) {
	    		widgets.push(null);
	    	}
	    	widgets.push(widget);
	    }
    	
    	var defaultSpans = (this._spans.length <= 0 ? this._loadDefaultSpans(childrenDiv, widgets) : null);
    	this._spans = (defaultSpans != null ? defaultSpans.trim() : this._spans);
    	
    	if (this._spans.length > 0) {
    		var spans = this._spans.trim().split(",");
    		var childrenSize = (childrenDiv != null ? childrenDiv.length : 0);
    		var min = Math.min(childrenSize, spans.length);
    		var sum = 0;
    		var limitGrid = 12;
    		for (var i = 0; i < min; i++) {
    			var child = childrenDiv[i];
       			var colspan = 1;
       			colspan = parseInt(spans[i]);
       			limitGrid = limitGrid - colspan;
       			this._applySpan(colspan, child);
       			if (jq(child).is(":visible")) {
       				sum += colspan;
       			}
       			
       			if (i < (min - 1) && limitGrid > 0) {
       				colspan = parseInt(spans[i + 1]);
       				if ((limitGrid - colspan) < 0) {
	        			//this._applyOffset(limitGrid, child);
						jq(child).before('<div class="col-md-' + (limitGrid + 1) + '" style="height: 34px;"></div>');	        			
	        			limitGrid = 12;
       				}
       				else if (i < (min - 2) && this._checkTag(childrenDiv[i + 2].firstChild)) {
	       				colspan = colspan + parseInt(spans[i + 2]);
	       				if ((limitGrid - colspan) < 0) {
		        			//this._applyOffset(limitGrid, child);
							jq(child).before('<div class="col-md-' + (limitGrid + 1) + '" style="height: 34px;"></div>');	        			
		        			limitGrid = 12;
	       				}
       				}
       			}
       			else {
       				limitGrid = 12;
       			}
    		}
    		var idx = 0;
    		for (var i = min; i < childrenSize; i++) {
    			var child = childrenDiv[i];
       			var colspan = 1;
       			colspan = parseInt(spans[idx]);
       			this._applySpan(colspan, child);
       			idx += 1;
       			idx = (idx < spans.length ? idx : 0);
       			if (jq(child).is(":visible")) {
       				sum += colspan;
       			}
    		}

    		if (false && childrenSize > 0 && sum < 12 && "right" == this._align) {
    			var offset = 12 - sum;

    			var firstVisible = null;
    			var firstIdx = -1;
        		for (var i = 0; i < childrenSize; i++) {
        			var child = childrenDiv[i];
        			this._removeAllClasses(child, "col-md-offset");
           			if (jq(child).is(":visible") && firstVisible == null) {
           				firstVisible = child;
           				firstIdx = i;
           			}
        		}
    			
    			if (firstVisible != null) {
        			this._applyOffset(offset, childrenDiv[firstIdx]);
    			}
    		}
    	}
    },

	_checkTag: function (node) {
		var result = false;
		var nodeClass = jq(node).attr("class");
		if ("tag" == nodeClass) {
			result = true;
		}
		return result;
	},

    _loadDefaultSpans: function (children, widgets) {
    	var result = null;
    	var length = (children != null ? children.length : 0);
    	if (length > 0) {
    		result = "";
    		for (var i = 0; i < length; i++) {
    			var child = children[i];
    			if (child != null) {
	    			var colspan = this._loadDefaultColspan(child, i, widgets);
	    			colspan = Math.min(12, Math.max(1, colspan));
    				result += (result.length > 0 ? "," : "") + colspan;
    			}
    		}
    	}
		return result;
	},

 	_getCellWidget: function (child, index, widgets) {
 		var result = null;
 		result = widgets[index];
 		return result;
	},

    _loadDefaultColspan: function (child, index, widgets) {
    	var result = 1;
    	if (child != null) {
    		var widget = this._getCellWidget(child, index, widgets);
    		if (widget != null) {
	    		if (widget.$instanceof(org.effortless.zkstrap.Input)) {
	    			var inputType = widget.getType();
					if (inputType == 'text') {
						result = 3;
					} else if (inputType == 'password') {
						result = 3;
					} else if (inputType == 'comment') {
						result = 11;
					} else if (inputType == 'checkbox') {
						result = 1;
					} else if (inputType == 'select') {
						result = 5;
					} else if (inputType == 'radio') {
						result = 5;
					} else if (inputType == 'table') {
						result = 12;
					} else if (inputType == 'date') {
						result = 2;
					} else if (inputType == 'time') {
						result = 1;
					} else if (inputType == 'datetime') {
						result = 3;
					} else if (inputType == 'count' || inputType == 'integer') {
						result = 2;
					} else if (inputType == 'number' || inputType == 'decimal') {
						result = 2;
					} else if (inputType == 'currency') {
						result = 2;
					} else if (inputType == 'email' || inputType == 'mail') {
						result = 3;
					} else if (inputType == 'color' || inputType == 'colour' || inputType == 'colorpicker' || inputType == 'colourpicker') {
						result = 2;
					} else if (inputType == 'ip') {
						result = 2;
					} else if (inputType == 'phone') {
						result = 2;
					}
	//			(CountField)child; result = 2
	//			(NumberField)child; result = 2
	    		}
	    		else if (widget.$instanceof(org.effortless.zkstrap.Layout)) {
	    			result = 12;
	    		}
	    		else if (widget.$instanceof(org.effortless.zkstrap.Btn)) {
	    			result = 2;
	    		}
	    		else if (widget.$instanceof(org.effortless.zkstrap.MenuBs)) {
	    			result = 2;
	    		}
	    		else {
	    			result = 1;
	    		}
    		}
    		
// 			(TextField)child; result = 3
//			(PasswordField)child; result = 3
// 			(CommentField)child; result = 11

//			(BoolField)child; result = 1
//			(EnumField)child; result = 5

//			(DateField)child; result = 2
//			(TimeField)child; result = 2
//			(TimestampField)child; result = 2

//			(CountField)child; result = 2
//			(NumberField)child; result = 2

// 			(Tag)child; result = 1

// 			(RefField)child; result = 5
//			(LabelField)child; result = 2

//			(ListField)child; result = 11
//			(FileField)child; result = 5
//			(PhotoField)child; result = 5
//			(AbstractLayout)child; result = 12
//			(IconField)child; result = 1
//			(TreeField)child; result = 11
    	}
		return result;
	}

});
