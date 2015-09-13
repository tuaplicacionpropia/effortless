package org.effortless.gencore.mapping;

import java.util.List;

import org.effortless.core.StringUtils;
import org.effortless.jast.GClass;
import org.effortless.jast.GField;
import org.effortless.jast.GMethod;
import org.effortless.jast.GNode;

public abstract class ColumnsLevelsTransform extends AbstractBaseTransform {

	public ColumnsLevelsTransform () {
		super();
	}

	protected void add_columnsEagerLazy (GClass cg, boolean eager) {
		String columns = "";
		
		List fields = cg.getProperties();
		if (fields != null) {
			int fieldsSize = (fields != null ? fields.size() : null);
			for (int i = 0; i < fieldsSize; i++) {
				GField field = (GField)fields.get(i);
				if (true || _isSimpleColumn(field)) {
					boolean fieldEager = isEager(field);
					if (eager == fieldEager) {
						String columnName = _getColumnName(field);
						columns += (columns.length() > 0 && columnName.length() > 0 ? ", " : "") + columnName;
					}
				}
			}
		}
		
		if (columns.length() > 0) {
			String methodName = (eager ? "_columnsEager" : "_columnsLazy");
			GMethod mg = cg.addMethod(methodName).setProtected(true).setReturnType(String.class);
			
//			return StringUtils.concat(super._columnsEager(), "NAME, SURNAMES, COMMENT, AGE, ENABLED", ", ");
			mg.addReturn(mg.callStatic(StringUtils.class, "concat", mg.call(mg.cteSuper(), methodName), mg.cte(columns), mg.cte(", ")));
//			mg.addReturn(mg.call("_concatPropertiesLoader", mg.cte(columns), mg.call(mg.cteSuper(), methodName)));
		}
	}
	


}
