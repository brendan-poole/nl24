package newlaw;

import javax.validation.constraints.Size;


public privileged aspect Jsr303 {

	// Default all strings to 254 chars. It can be overridden in the classes
	declare @field : private String *.* : @Size(max=254);
	declare @field : protected String *.* : @Size(max=254);

}
