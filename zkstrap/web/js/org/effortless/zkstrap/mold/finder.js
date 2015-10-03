
function (out) {
  var uuid = this.uuid;

  out.push('<div id="', uuid, '" class="finder">');
  
  var child = null;
  if (this.nChildren > 0) {
    child = this.firstChild;
    child.redraw(out);
  }
  
  out.push('<div class="pull-right">');
    if (this.nChildren > 1) {
    	child = child.nextSibling;
        child.redraw(out);
      child.redraw(out);
    }
  
    out.push('<button id="', uuid, '-btnSearch" type="button" class="btn btn-default">Search</button>');
  out.push('</div>');
  
  if (this.nChildren > 2) {
   	child = child.nextSibling;
    child.redraw(out);
  
//    for (var child = this.firstChild; child; child = child.nextSibling) {
//      child.redraw(out);
//    }
  }
  
  out.push('<div class="pull-right">');
   	child = child.nextSibling;
    for (; child; child = child.nextSibling) {
      child.redraw(out);
    }

    out.push('<button id="', uuid, '-btnCreate" type="button" class="btn btn-default">Create</button>');
    out.push('<button id="', uuid, '-btnRead" type="button" class="btn btn-default">Read</button>');
    out.push('<button id="', uuid, '-btnUpdate" type="button" class="btn btn-default">Update</button>');
    out.push('<button id="', uuid, '-btnDelete" type="button" class="btn btn-default">Delete</button>');
  out.push('</div>');

  out.push('</div>');
}
