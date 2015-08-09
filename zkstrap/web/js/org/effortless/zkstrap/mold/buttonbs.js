
/*
  <div class="btn-group">
       <button type="button" class="btn btn-default">Action
        <span class="badge">4</span>
       </button>
       <button aria-expanded="false" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
           <span class="caret"></span>
           <span class="sr-only">Toggle Dropdown</span>
       </button>
       <ul class="dropdown-menu" role="menu">
          <li><a href="#"><i class="fa fa-bitbucket"></i>Action <span class="badge">4</span></a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
       </ul>
  </div>
*/

function (out) {
 out.push('<div class="btn-group">');
   out.push('<button id="', this.uuid, '-mainBtn" type="button"', this.domAttrs_(), '>');
     out.push('<i class="fa fa-bitbucket"></i>');
     out.push(this.getLabel());
     out.push('<span class="badge">4</span>');
   out.push('</button>');
   out.push('<button aria-expanded="false" type="button" class="', this.domClass_(), ' dropdown-toggle" data-toggle="dropdown">');
     out.push('<span class="caret"></span>');
     out.push('<span class="sr-only">Toggle Dropdown</span>');
   out.push('</button>');
   out.push('<ul id="', this.uuid, '-menu" class="dropdown-menu" role="menu">');
     out.push('<li><a id="', this.uuid, '-menuitem0" href="#"><i class="fa fa-bitbucket"></i>');
     out.push(this.getLabel());
     out.push('<span class="badge">4</span></a></li>');
     out.push('<li><a id="', this.uuid, '-menuitem1" href="#">Another action</a></li>');
     out.push('<li><a id="', this.uuid, '-menuitem2" href="#">Something else here</a></li>');
     out.push('<li class="divider"></li>');
     out.push('<li><a id="', this.uuid, '-menuitem3" href="#">Separated link</a></li>');
   out.push('</ul>');
 out.push('</div>');
}
