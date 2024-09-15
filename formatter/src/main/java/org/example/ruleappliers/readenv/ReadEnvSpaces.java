package org.example.ruleappliers.readenv;

import org.example.ruleappliers.ApplicableSpaces;

public enum ReadEnvSpaces implements ApplicableSpaces {
	SPACE_BEFORE_READENV_IDENTIFIER,
	SPACE_AFTER_READENV_IDENTIFIER,
	SPACE_BEFORE_READENV_PARAMETERS,
	SPACE_AFTER_READENV_PARAMETERS,
	SPACE_AFTER_READENV_CALL,
}
