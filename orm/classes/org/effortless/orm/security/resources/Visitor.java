package org.effortless.orm.security.resources;

import org.effortless.orm.security.Resource;

public interface Visitor {

	public boolean contains(Resource rs, AdditionResourceSet rd);

	public boolean contains(Resource rs, AllResourceSet rd);

	public boolean contains(Resource rs, EntityResourceSet rd);

	public boolean contains(Resource rs, IncludeDeleteInnerResourceSet rd);

	public boolean contains(Resource rs, ModuleResourceSet rd);

	public boolean contains(Resource rs, NoneResourceSet rd);

	public boolean contains(Resource rs, NotResourceSet rd);

	public boolean contains(Resource rs, OperationResourceSet rd);

	public boolean contains(Resource rs, PropertyResourceSet rd);

	public boolean contains(Resource rs, ReadOperationsResourceSet rd);

	public boolean contains(Resource rs, ResourceResourceSet rd);

	public boolean contains(Resource rs, SubtractResourceSet rd);

	public boolean contains(Resource rs, UnitResourceSet rd);

	public boolean contains(Resource rs, WriteOperationsResourceSet rd);

	public boolean contains(Resource rs, MenuResourceSet rd);

}
