
function (out) {
  var uuid = this.uuid;

  out.push('<div id="', uuid, '" class="finder">');
      
  if (this.nChildren > 0) {
    for (var child = this.firstChild; child; child = child.nextSibling) {
      child.redraw(out);
    }
  }
  out.push('</div>');
}
