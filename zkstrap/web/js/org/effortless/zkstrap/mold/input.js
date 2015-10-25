
function (out) {
    var _self = this;
	var uuid = this.uuid;
	var _type = this.getType();
	var _value = this.getEncodedValue();
	
	if (_type == 'text') {
		out.push('<input class="form-control" id="', uuid, '-input" placeholder="" type="text" />');
	}
	else if (_type == 'password') {
		out.push('<input class="form-control" id="', uuid, '-input" placeholder="" type="password" />');
	}
	else if (_type == 'comment') {
		out.push('<textarea id="', uuid, '-input" class="form-control" rows="3" placeholder=""></textarea>');
	}
	else if (_type == 'checkbox') {
		var _skin = this._loadSkin();
/*
		out.push('<div id="', uuid, '" aria-disabled="false" aria-checked="false" style="position: relative;" class="icheckbox_minimal-blue checked">');
			out.push('<input style="position: absolute; opacity: 0;" class="minimal" checked="" type="checkbox">');
			out.push('<ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins>');
		out.push('</div>');
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, ' input").iCheck();');
        out.push('</script>');
*/
		out.push('<input id="', uuid, '-input" type="checkbox"', (_value ? ' checked' : ''), (_value ? ' value="on"' : ' value="off"'), '>');
/*		
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").iCheck({checkboxClass: "icheckbox_', _skin, '", radioClass: "iradio_minimal", increaseArea: "20%"}).on("ifToggled", function() { _self._checkIfToggled(); })');
        out.push('</script>');
*/
	}
	else if (_type == 'radio') {
		var _skin = this._loadSkin();
		var _values = this.getPropertyOptions('values');
		var index = -1;
		
		out.push('<input name="', uuid, '-input" id="', uuid + '-input_' + index, '" type="radio" value="', '' + index ,'">');
		out.push('<label for="', uuid + '-input_' + index, '" class="" style="margin-right: 15px;">', '&empty;', '</label>');
		for	(index = 0; index < _values.length; index++) {
			out.push('<input name="', uuid, '-input" id="', uuid + '-input_' + index, '" type="radio" value="', '' + index ,'">');
			if (index < (_values.length - 1)) {
				out.push('<label for="', uuid + '-input_' + index, '" class="" style="margin-right: 15px;">', _values[index], '</label>');
			}
			else {
				out.push('<label for="', uuid + '-input_' + index, '" class="">', _values[index], '</label>');
			}
		}
/*
        out.push('<script type="text/javascript">');
		for	(var index = 0; index < _values.length; index++) {
            out.push('jq("#', uuid + '-input_' + index, '").iCheck({radioClass: "iradio_', _skin, '", increaseArea: "20%"});');
		} 		
        out.push('</script>');
*/
	}
	else if (_type == 'date') {
	//https://eonasdan.github.io/bootstrap-datetimepicker/#custom-formats
/*
		out.push('<div id="', uuid, '" class="input-group date" data-provide="datepicker">');
//			out.push('<input class="form-control" data-inputmask="\'alias\': \'dd/mm/yyyy\'" data-mask="" type="text" />');
			out.push('<input id="', uuid, '-input" class="form-control" type="text" data-date-format="mm/dd/yyyy" />');
			out.push('<div class="input-group-addon">');
				out.push('<i class="fa fa-calendar"></i>');
			out.push('</div>');
		out.push('</div>');
*/
/*
		out.push('<div id="', uuid, '" class="input-append date">');
			out.push('<input class="form-control" value="12-02-2012" type="text" />');
			out.push('<span class="add-on"><i class="icon-th"></i></span>');
		out.push('</div>');
		
		
		out.push('<div id="', uuid, '" class="input-group date">');
			out.push('<input type="text" class="form-control" />');
			out.push('<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>');
    	out.push('</div>');
*/
		out.push('<div id="', uuid, '" class="input-group date">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<div class="input-group-addon">');
				out.push('<i class="fa fa-calendar"></i>');
			out.push('</div>');
		out.push('</div>');
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").datepicker({');
                out.push('format: "dd/mm/yyyy"');
            out.push('});');
        out.push('</script>');



	}
	else if (_type == 'time') {
		out.push('<div id="', uuid, '" class="input-group date">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<span class="input-group-addon">');
				out.push('<span class="glyphicon glyphicon-time"></span>');
			out.push('</span>');
		out.push('</div>');
			
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").datetimepicker({format: "LT", locale: "es", collapse: false});');
        out.push('</script>');
	}
	else if (_type == 'datetime') {
		out.push('<div id="', uuid, '" class="input-group date">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<span class="input-group-addon">');
				out.push('<i class="fa fa-calendar"></i>');
			out.push('</span>');
		out.push('</div>');
			
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").datetimepicker({format: "DD/MM/YYYY HH:mm", locale: "es", sideBySide: true});');
        out.push('</script>');
	}
	else if (_type == 'select' || _type == 'ref') {
/*
				<select id="combo" multiple="multiple" class="form-control">
				  <option value="AL">Alabama</option>
				  <option value="WY">Wyoming</option>
					<option value="2" selected="selected">duplicate</option>
				</select>
			
			
<script>
var data = [
{ id: 0, text: 'enhancement' }, 
{ id: 1, text: 'bug' }, 
{ id: 2, text: 'duplicate' }, 
{ id: 3, text: 'invalid' }, 
{ id: 4, text: 'wontfix' }
];

$("#combo").select2({
  placeholder: "Select a state",
  //allowClear: true,
  theme: "classic", 
//  dir: "rtl", 
//  width: 'resolve',
//  dropdownAutoWidth : false,
//  minimumResultsForSearch: Infinity, 

//  tags: true,
//  tokenSeparators: [',', ' ']

  width: '100%',
  maximumSelectionLength: 3,
  language: "es", 
  data: data
});
</script>    
*/
		var _values = this.getPropertyOptions('values');

//		out.push('<select id="', uuid, '-input" class="form-control" multiple="multiple">');
		out.push('<select id="', uuid, '-input" class="form-control">');
			out.push('<option value="', '-1', '">', '', '</option>');
		for	(var index = 0; index < _values.length; index++) {
			out.push('<option value="', '' + index, '">', _values[index], '</option>');
		} 		
			//<option value="2" selected="selected">duplicate</option>
		out.push('</select>');

        out.push('<script type="text/javascript">');
//            out.push('jq("#', uuid, '-input").select2();');
            out.push('jq("#', uuid, '-input").select2({');
				out.push('placeholder: "Select...", ');
				out.push('theme: "classic", ');
				out.push('width: "100%", ');
				out.push('language: "es" ');
			out.push('}); ');
        out.push('</script>');
	}
	else if (_type == 'color' || _type == 'colour' || _type == 'colorpicker' || _type == 'colourpicker') {
		out.push('<div id="', uuid, '" class="input-group">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<div class="input-group-addon">');
				out.push('<i></i>');
			out.push('</div>');
		out.push('</div>');
/*
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '").colorpicker();');
        out.push('</script>');
*/
	}	
	else if (_type == 'count' || _type == 'integer') {
		out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
/*
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").inputmask({ alias: "integer"});');
        out.push('</script>');
*/
	}
	else if (_type == 'number' || _type == 'decimal') {
		out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
/*
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").inputmask({ alias: "decimal"});');
        out.push('</script>');
*/
	}
	else if (_type == 'email' || _type == 'mail') {
		out.push('<div id="', uuid, '" class="input-group">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<div class="input-group-addon">');
				out.push('<i class="fa fa-envelope"></i>');
			out.push('</div>');
		out.push('</div>');
/*
        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").inputmask({ alias: "email"});');
        out.push('</script>');
*/
	}
	else if (_type == 'currency') {
		out.push('<div id="', uuid, '" class="input-group">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<div class="input-group-addon">');
				out.push('<i class="fa fa-money"></i>');
			out.push('</div>');
		out.push('</div>');
/*
        out.push('<script type="text/javascript">');
//            out.push('jq("#', uuid, '-input").inputmask({ alias: "currency"});');
            out.push('jq("#', uuid, '-input").inputmask({ alias: "decimal", radixPoint:",", groupSeparator: ".", digits: 2, autoGroup: true, suffix: "€"});');
        out.push('</script>');
*/
/*
$("#amount").inputmask("decimal",{
    radixPoint:",", groupSeparator: ".", digits: 2, autoGroup: true, suffix: "€"
});            
*/        
        
	}
	else if (_type == 'ip') {
		out.push('<div id="', uuid, '" class="input-group">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<div class="input-group-addon">');
				out.push('<i class="fa fa-laptop"></i>');
			out.push('</div>');
		out.push('</div>');

        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").inputmask({ alias: "ip"});');
        out.push('</script>');
	}
	else if (_type == 'phone') {
		out.push('<div id="', uuid, '" class="input-group">');
			out.push('<input id="', uuid, '-input" type="text" class="form-control" />');
			out.push('<div class="input-group-addon">');
				out.push('<i class="fa fa-phone"></i>');
			out.push('</div>');
		out.push('</div>');

        out.push('<script type="text/javascript">');
            out.push('jq("#', uuid, '-input").inputmask({mask: "999 99 99 99"});');
        out.push('</script>');
	}
	else if (_type == 'table') {
		var columnNames = this.getColumnNames();
		var cellValues = this.getCellValues();
		out.push('<div id="', uuid, '" class="input-table">');

		var numCells = (columnNames ? columnNames.length : (cellValues ? cellValues.length : 0));
		if (numCells > 0) {
		    var cellTotal = 10;
			var cellSpan = Math.floor(cellTotal / numCells);
			var cellRestoSpan = (cellTotal % numCells);

			if (columnNames && columnNames.length > 0) {
				out.push('<div class="row">');
					out.push('<div class="col-md-1"></div>');
				
				for (var i = 0; i < columnNames.length; i++) {
					var columnName = columnNames[i];
					var cellItemSpan = cellSpan;
					if ((i + cellRestoSpan) >= columnNames.length) {
						cellItemSpan += 1;
					}
					var cellClass = 'col-md-' + cellItemSpan;
					out.push('<div class="', cellClass, '">', columnName, '</div>');
				}
					out.push('<div class="col-md-1"></div>');
				out.push('</div>');
			}
	
			if (cellValues && cellValues.length > 0) {
				for (var i = 0; i < cellValues.length; i++) {
				out.push('<div class="row">');
					var cellValue = cellValues[i];

					out.push('<div class="col-md-1">');
					out.push('<input name="', uuid, '-radioselect" id="', uuid + '-radioselect_' + i, '" type="radio" value="', '' + i ,'">');
					out.push('</div>');


					
					for (var j = 0; j < columnNames.length; j++) {
						var cellItemValue = cellValue[j];
						var cellItemSpan = cellSpan;
						if ((j + cellRestoSpan) >= columnNames.length) {
							cellItemSpan += 1;
						}
						var cellClass = 'col-md-' + cellItemSpan;
						out.push('<div class="', cellClass, '">', cellItemValue, '</div>');
					}

					out.push('<div class="col-md-1">');
					out.push('</div>');
					
				out.push('</div>');
				}
/*
		        out.push('<script type="text/javascript">');
				for	(var index = 0; index < cellValues.length; index++) {
		            out.push('jq("#', uuid + '-radioselect_' + index, '").iCheck({radioClass: "iradio_minimal", increaseArea: "10%"});');
				} 		
       			out.push('</script>');
*/
			}
		}
		
		if (true) {
		 var modeFinder = this.getPropertyOptions("modeFinder");
		 console.log('modeFinder = ' + modeFinder);
  	     if (modeFinder != "true") {
		 out.push('<div class="btn-group pull-right">');
		    out.push('<button id="', uuid, '-btnCreate" type="button" class="btn btn-default btn-flat"><i class="fa fa-plus-circle"></i></button>');
		    out.push('<button id="', uuid, '-btnRead" type="button" class="btn btn-default btn-flat"><i class="fa fa-eye"></i></button>');
		    out.push('<button id="', uuid, '-btnUpdate" type="button" class="btn btn-default btn-flat"><i class="fa fa-edit"></i></button>');
		    out.push('<button id="', uuid, '-btnDelete" type="button" class="btn btn-default btn-flat"><i class="fa fa-trash"></i></button>');
		 out.push('</div>');
     	 }
		 else {
		  out.push('<div class="pull-right">');
            out.push('<button id="', uuid, '-btnCreate" type="button" class="btn btn-default">Create</button>');
            out.push('<button id="', uuid, '-btnRead" type="button" class="btn btn-default">Read</button>');
            out.push('<button id="', uuid, '-btnUpdate" type="button" class="btn btn-default">Update</button>');
            out.push('<button id="', uuid, '-btnDelete" type="button" class="btn btn-default">Delete</button>');
 		  out.push('</div>');
		 }
		}
		
		out.push('</div>');
	}
	else if (_type == 'web') {
	}
	else if (_type == 'file') {
	}
	else if (_type == 'photo' || _type == 'image') {
	}
	else if (_type == 'icon') {
	}
	
	
/*



//text mask
<div class="input-group">
                      <div class="input-group-addon">
                        <i class="fa fa-phone"></i>
                      </div>
                      <input class="form-control" data-inputmask="&quot;mask&quot;: &quot;(999) 999-9999&quot;" data-mask="" type="text">
                    </div>
                    
                    
//Period
<div class="input-group">
                      <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                      </div>
                      <input class="form-control pull-right" id="reservation" type="text">
                    </div>
                    
                    
//Period + time
<div class="input-group">
                      <div class="input-group-addon">
                        <i class="fa fa-clock-o"></i>
                      </div>
                      <input class="form-control pull-right" id="reservationtime" type="text">
                    </div>
                    
//color
<div class="input-group my-colorpicker2 colorpicker-element">
                      <input class="form-control" type="text">
                      <div class="input-group-addon">
                        <i style="background-color: rgb(176, 39, 39);"></i>
                      </div>
                    </div>
                    
                    
//timepicker
<div class="input-group">
                        <input class="form-control timepicker" type="text">
                        <div class="input-group-addon">
                          <i class="fa fa-clock-o"></i>
                        </div>
                      </div>
                      
                      
//checkbox               
<div aria-disabled="false" aria-checked="false" style="position: relative;" class="icheckbox_minimal-blue checked"><input style="position: absolute; opacity: 0;" class="minimal" checked="" type="checkbox"><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div>                    

//radio
<div aria-disabled="false" aria-checked="true" style="position: relative;" class="iradio_minimal-blue checked"><input style="position: absolute; opacity: 0;" name="r1" class="minimal" checked="" type="radio"><ins style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" class="iCheck-helper"></ins></div>

*/  
}
