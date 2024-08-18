package org.example.parameters;

import java.util.List;
import org.example.Resolution;
import org.example.Result;
import org.example.ast.DeclarationType;

public record ParametersResolution(Result result, List<DeclarationType> types)
		implements Resolution {}
