
function (out) {
  var uuid = this.uuid;

  out.push('<div id="', uuid, '" class="editor">');
      
        //Content Header (Page header)
		out.push('<section class="content-header">');
		  out.push('<h1>');
		    out.push('Registro de código 34235');
		    out.push('<small>Editor</small>');
		  out.push('</h1>');
		out.push('</section>');

        // Main content
		out.push('<section class="content">');
  
      
  var child = null;
  if (this.nChildren > 0) {
    child = this.firstChild;
    child.redraw(out);
  }

//  for (var child = this.firstChild; child; child = child.nextSibling) {
//    child.redraw(out);
//  }
  
  out.push('<div class="pull-right">');
    if (this.nChildren > 1) {
      child = child.nextSibling;
      for (child = child.firstChild; child; child = child.nextSibling) {
      	child.redraw(out);
      }
    }
    out.push('<button id="', uuid, '-btnOk" type="button" class="btn btn-default">Save</button>');
    out.push('<button id="', uuid, '-btnCancel" type="button" class="btn btn-default">Cancel</button>');
  out.push('</div>');
  
  
		out.push('</section>');
  out.push('</div>');
}
