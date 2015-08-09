/*

    <div class="wrapper">
      
      <header class="main-header">
        <!-- Logo -->
        <a href="index2.html" class="logo">
          <span class="logo-mini"><b>A</b>LT</span>
          <span class="logo-lg"><b>Admin</b>LTE</span>
        </a>
        
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <!-- User Account: style can be found in dropdown.less -->
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image"/>
                  <span class="hidden-xs">Alexander Pierce</span>
                </a>
                <ul class="dropdown-menu">
                  <!-- User image -->
                  <li class="user-header">
                    <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                    <p>
                      Alexander Pierce - Web Developer
                      <small>Member since Nov. 2012</small>
                    </p>
                  </li>
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="#" class="btn btn-default btn-flat">Profile</a>
                    </div>
                    <div class="pull-right">
                      <a href="#" class="btn btn-default btn-flat">Sign out</a>
                    </div>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </nav>
      </header>
      
      
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <!-- Sidebar user panel -->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
            </div>
            <div class="pull-left info">
              <p>Alexander Pierce</p>

              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div>
          
          <!-- search form -->

            <div class="input-group">
              <input type="text" name="q" class="form-control" placeholder="Search..."/>
              <span class="input-group-btn">
                <button type='submit' name='search' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
            
          <!-- /.search form -->
          
          <!-- sidebar menu: : style can be found in sidebar.less -->
          
          <!-- MENU -->
          
          
          
        </section>
        <!-- /.sidebar -->
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Dashboard
            <small>Control panel</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li class="active">Dashboard</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">

        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b> 2.0
        </div>
        <strong>Copyright &copy; 2014-2015 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong> All rights reserved.
      </footer>
      
      
    </div><!-- ./wrapper -->

*/


function (out) {
	var uuid = this.uuid;

 	var wMiniTitle = '<b>A</b>LT';//this.getEncodedMiniTitle();//<b>A</b>LT
 	var wAppTitle = '<b>Admin</b>LTE';//this.getEncodedAppTitle();//<b>Admin</b>LTE
 	var wUserIcon = 'https://almsaeedstudio.com/themes/AdminLTE/dist/img/user2-160x160.jpg';//this.getUserIcon();//dist/img/user2-160x160.jpg
 	var wUserName = 'Jesús Ramos';//this.getUserName();//Alexander Pierce
 	var wUserProfile = 'Programador';//this.getUserProfile();//Web Developer
 	var wUserInfo = 'Idecnet desde julio 2007';//this.getUserInfo();//Member since Nov. 2012
	
	var wAuthorYear = '2014-2015';//this.getAuthorYear();//2014-2015
	var wAuthorWeb = 'http://almsaeedstudio.com';//this.getAuthorWeb();//http://almsaeedstudio.com
	var wAuthorName = 'Almsaeed Studio';//this.getAuthorName();//Almsaeed Studio
	var wAppVersion = '2.0';//this.getAppVersion();//2.0
	
    out.push('<div id="', uuid, '" class="wrapper">');
      
      out.push('<header class="main-header">');
        // Logo
        out.push('<a href="#" class="logo">');
		  out.push('<span class="logo-mini">', wMiniTitle, '</span>');
		  out.push('<span class="logo-lg">', wAppTitle, '</span>');
		out.push('</a>');
        
        // Header Navbar: style can be found in header.less
		out.push('<nav class="navbar navbar-static-top" role="navigation">');
          // Sidebar toggle button
		  out.push('<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">');
		    out.push('<span class="sr-only">Toggle navigation</span>');
		  out.push('</a>');
		  out.push('<div class="navbar-custom-menu">');
		    out.push('<ul class="nav navbar-nav">');
              // User Account: style can be found in dropdown.less
		      out.push('<li class="dropdown user user-menu">');
		        out.push('<a href="#" class="dropdown-toggle" data-toggle="dropdown">');
		          out.push('<img src="', wUserIcon, '" class="user-image" alt="User Image"/>');
		          out.push('<span class="hidden-xs">', wUserName, '</span>');
		        out.push('</a>');
		        out.push('<ul class="dropdown-menu">');
                  // User image
		          out.push('<li class="user-header">');
		            out.push('<img src="', wUserIcon, '" class="img-circle" alt="User Image" />');
		            out.push('<p>');
		              out.push('', wUserName, ' - ', wUserProfile, '');
		              out.push('<small>', wUserInfo, '</small>');
		            out.push('</p>');
		          out.push('</li>');
                  // Menu Footer-->
		          out.push('<li class="user-footer">');
		            out.push('<div class="pull-left">');
		              out.push('<a href="#" class="btn btn-default btn-flat">Profile</a>');
		            out.push('</div>');
		            out.push('<div class="pull-right">');
		              out.push('<a href="#" class="btn btn-default btn-flat">Sign out</a>');
		            out.push('</div>');
		          out.push('</li>');
		        out.push('</ul>');
		      out.push('</li>');
		    out.push('</ul>');
		  out.push('</div>');
		out.push('</nav>');
      out.push('</header>');
      
      
      // Left side column. contains the logo and sidebar
      out.push('<aside class="main-sidebar">');
        // sidebar: style can be found in sidebar.less
        out.push('<section id="', uuid , '-menu" class="sidebar">');
          // Sidebar user panel
		  out.push('<div class="user-panel">');
		    out.push('<div class="pull-left image">');
		      out.push('<img src="', wUserIcon, '" class="img-circle" alt="User Image" />');
		    out.push('</div>');
		    out.push('<div class="pull-left info">');
		      out.push('<p>', wUserName, '</p>');
		      out.push('<a href="#"><i class="fa fa-circle text-success"></i> Online</a>');
		    out.push('</div>');
		  out.push('</div>');
          
          // search form
		  out.push('<div class="input-group sidebar-form">');
		    out.push('<input type="text" name="q" class="form-control" placeholder="Search..."/>');
		    out.push('<span class="input-group-btn">');
		      out.push('<button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>');
		    out.push('</span>');
		  out.push('</div>');

          // MENU
          if (this.nChildren > 0) {
            for (var child = this.firstChild; child; child = child.nextSibling) {
			  if (child instanceof org.effortless.zkstrap.MenuBs) {
                child.redraw(out);
              }
            }
          }

		out.push('</section>');
      out.push('</aside>');

      // Content Wrapper. Contains page content
      out.push('<div class="content-wrapper">');
        //Content Header (Page header)
		out.push('<section class="content-header">');
		  out.push('<h1>');
		    out.push('Dashboard');
		    out.push('<small>Control panel</small>');
		  out.push('</h1>');
		  out.push('<ol class="breadcrumb">');
		    out.push('<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>');
		    out.push('<li class="active">Dashboard</li>');
		  out.push('</ol>');
		out.push('</section>');

        // Main content
		out.push('<section id="', uuid , '-main-content" class="content">');

        if (this.nChildren > 0) {
          for (var child = this.firstChild; child; child = child.nextSibling) {
		 	if (!(child instanceof org.effortless.zkstrap.MenuBs)) {
              child.redraw(out);
            }
          }
        }


		out.push('</section>');
      out.push('</div>');
      
      out.push('<footer class="main-footer">');
	    out.push('<div class="pull-right hidden-xs">');
		  out.push('<b>Version</b> ', wAppVersion, '');
		out.push('</div>');
	  out.push('<strong>Copyright &copy; ', wAuthorYear, ' <a href="', wAuthorWeb, '" target="_blank">', wAuthorName, '</a>.</strong> All rights reserved.');

    out.push('</footer>');

  out.push('</div>');
}
