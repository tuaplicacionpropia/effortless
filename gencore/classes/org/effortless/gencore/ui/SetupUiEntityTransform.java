package org.effortless.gencore.ui;

import org.effortless.gencore.InfoModel;
import org.effortless.jast.Expression;
import org.effortless.jast.GClass;
import org.effortless.jast.GCode;
import org.effortless.jast.GField;
import org.effortless.jast.GNode;
import org.effortless.jast.transforms.AbstractTransform;
import org.effortless.jast.transforms.Transforms;

/**
 * 

 *
 */
public class SetupUiEntityTransform extends AbstractTransform {

	public SetupUiEntityTransform () {
		super();
	}
	
	@Override
	public void process(GNode node) {
		GClass clazz = (GClass)node;
		GClass cg = (GClass)clazz.getApplication().getAttribute("_MAIN_UI_");
		if (cg != null) {
			{
				java.util.List fields = InfoModel.listNotNullUnique(clazz);
				int length = (fields != null ? fields.size() : 0);
				String properties = "";
				for (int i = 0; i < length; i++) {
					GField field = (GField)fields.get(i);
					if (!field.isStatic() && !field.isFinal()) {
						String fName = field.getName();
						properties += (properties.length() > 0 && fName.length() > 0 ? "," : "") + fName;
					}
				}
				if (properties.length() > 0) {
			        //MetaEntity.__DEFINITION__.setFinderProperties("p1,p2,p3");
					GCode codeDef = (GCode)clazz.getAttribute(Transforms.DEFINITION_CODE);
					Expression expr = codeDef.realProperty(codeDef.clazz(clazz), "__DEFINITION__");		
					codeDef.add(cg.call(expr, "setFinderProperties", codeDef.cte(properties)));
				}
			}
//				}
//			}
			
			
			
		}
	}

}
