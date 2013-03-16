package newlaw.util;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;

public class SQLServerDialectExt extends SQLServerDialect {
	public SQLServerDialectExt() {
		super();
		registerColumnType( Types.BOOLEAN, "bit" );
	}
}
