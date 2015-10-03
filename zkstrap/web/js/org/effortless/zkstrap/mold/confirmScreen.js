
function (out) {
  var uuid = this.uuid;

  out.push('<div id="', uuid, '" class="confirmScreen">');

  var child = null;
      
  if (this.nChildren > 0) {
    child = this.firstChild;
    child.redraw(out);
  }

  var contentText = this._getContentText();
  if (contentText && contentText.length > 0) {
    out.push('<h3 id="', uuid, '-content">', contentText, '</h3>');
  }

  out.push('<div class="pull-right">');
    if (this.nChildren > 1) {
      child = child.nextSibling;
      for (child = child.firstChild; child; child = child.nextSibling) {
      	child.redraw(out);
      }
    }
    out.push('<button id="', uuid, '-btnOk" type="button" class="btn btn-default">Ok</button>');
    out.push('<button id="', uuid, '-btnCancel" type="button" class="btn btn-default">Cancel</button>');
  out.push('</div>');
  
  
  
  out.push('</div>');
}
