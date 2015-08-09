
/*
<ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-dashboard"></i> <span>Dashboard</span> <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="../index.html"><i class="fa fa-circle-o"></i> Dashboard v1</a></li>
                <li><a href="../index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>
              </ul>
            </li>
</ul>
*/
/*
<menuBs label="MAIN NAVIGATION">
  <menuBs label="Dashboard" icon="dashboard">
    <menuBs label="Dashboard v1" icon="circle-o" />
    <menuBs label="Dashboard v2" icon="circle-o" />
  </menuBs>
</menuBs>




<ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>    MAIN BS
            
            
            
            <li class="">
              <a href="widgets.html">
                <i class="fa fa-th"></i> <span>Widgets</span> 
                <small class="label pull-right bg-green">new</small>
              </a>
            </li>
            
            <li class="treeview active">
              <a href="#">
                <i class="fa fa-share"></i> <span>Multilevel</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              
              <ul style="display: block;" class="treeview-menu menu-open">
                <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>
                <li class="active">
                  <a href="#"><i class="fa fa-circle-o"></i> Level One <i class="fa fa-angle-left pull-right"></i></a>
                  <ul style="display: block;" class="treeview-menu menu-open">
                    <li><a href="#"><i class="fa fa-circle-o"></i> Level Two</a></li>
                    <li>
                      <a href="#"><i class="fa fa-circle-o"></i> Level Two <i class="fa fa-angle-left pull-right"></i></a>
                      <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                        <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                      </ul>
                    </li>
                  </ul>
                </li>
                <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>
              </ul>
              
            </li>
            <li><a href="../documentation/index.html"><i class="fa fa-book"></i> <span>Documentation</span></a></li>
            
    </ul>


*/

/*
function (out) {
	var mainMenu = !(this.parent.className == 'org.effortless.zkstrap.MenuBs');
	var uuid = this.uuid;
	this._numMenus = 0;
	
	if (mainMenu) {
  	  var mainLabel = zUtl.encodeXML(this.getLabel());
      out.push('<ul class="sidebar-menu">');
        out.push('<li class="header">', mainLabel, '</li>');
    }
    
    if (this.nChildren > 0) {
		for (var w = this.firstChild; w; w = w.nextSibling) {
	      var hasChildren = (w.nChildren > 0);
		  var wLabel = zUtl.encodeXML(w.getLabel());
	      
	      if (hasChildren) {
	        this._numMenus += 1;
	        out.push('<li id="', uuid, '-menu', this._numMenus, '" class="treeview">');
	          out.push('<a href="#">');
	            out.push('<i class="fa fa-dashboard"></i> <span>', wLabel, '</span> <i class="fa fa-angle-left pull-right"></i>');
	          out.push('</a>');
	          out.push('<ul class="treeview-menu">');
	          
	          for (var child = w.firstChild; child; child = child.nextSibling) {
	//          out.push('<a class="', child.className, '" />');
	            child.redraw(out);
	          }
	          
	          out.push('</ul>');
	      }
	      else {
	            out.push('<li><a href="../index.html"><i class="fa fa-circle-o"></i> ', wLabel, '</a></li>');
	      }
	      if (hasChildren) {
	          out.push('</ul>');
	        out.push('</li>');
	      }
		}
	}
	else {
	  var wLabel = zUtl.encodeXML(this.getLabel());
      out.push('<li><a href="../index.html"><i class="fa fa-circle-o"></i> ', wLabel, '</a></li>');
	}    
    
    if (mainMenu) {
      out.push('</ul>');
    }
}
*/

function (out) {
	var _mainMenu = this.isMainMenu();
 	var wLabel = this.getEncodedLabel();
	var uuid = this.uuid;
	if (_mainMenu) {
		out.push('<ul id="', uuid, '" class="sidebar-menu">');
			out.push('<li class="header">', wLabel, '</li>');
			if (this.nChildren > 0) {
				for (var child = this.firstChild; child; child = child.nextSibling) {
					child.redraw(out);
				}
			}
		out.push('</ul>');
	}
    else {
    	if (this.nChildren <= 0) {
      		out.push('<li id="', uuid, '" class="">');
				out.push('<a href="#">');
					out.push('<i class="fa fa-th"></i> <span>', wLabel, '</span>');
					if (false) { 
						out.push('<small class="label pull-right bg-green">new</small>');
					}
				out.push('</a>');
			out.push('</li>');
    	}
    	else {
			out.push('<li id="', uuid, '" class="treeview">');
				out.push('<a href="#">');
					out.push('<i class="fa fa-share"></i> <span>', wLabel, '</span>');
					out.push('<i class="fa fa-angle-left pull-right"></i>');
				out.push('</a>');
              
				out.push('<ul class="treeview-menu">');
              
				for (var child = this.firstChild; child; child = child.nextSibling) {
					child.redraw(out);
				}
                
				out.push('</ul>');
              
			out.push('</li>');
    	}
    }
}




