
function (out) {
	var uuid = this.uuid;
	var _type = this.getType();
	var index = 0;
	if (true || _type == 'grid') {
//<div class="col-xs-12 col-sm-6 col-lg-8">	
//<div sclass="col-xs-6 col-lg-4">
		out.push('<div id="', uuid, '" class="row">');
		  for (var w = this.firstChild; w; w = w.nextSibling) {
			var index = index + 1;
			if (w.$instanceof(org.effortless.zkstrap.Input)) {
				var tagLabel = w.getTagLabel();
			    out.push('<div>');
		    	out.push('<label for="', w.uuid + '-input', '">', tagLabel + ':', '</label>');
//		    	out.push('<label>', tagLabel + ':', '</label>');
		    	out.push('</div>');
		    }
		    out.push('<div>');
		    w.redraw(out);
		    out.push('</div>');
		  }
		out.push('</div>');        
	}
}
	