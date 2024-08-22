package org.example;

import org.example.io.AstBuilder;
import org.example.io.FileParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatterTestFramework {

    public void testRules(String path) throws IOException {
        AstBuilder astBuilder = new AstBuilder();
        MapFromFile mapFromFile = new MapFromFile();
        TestRuleMapFactory testRuleMapFactory = new TestRuleMapFactory(path + "/rules.json");
        Formatter formatter = new PrintScriptFormatter(testRuleMapFactory);
        Map<String, String> codes = mapFromFile.getMapFromFile(path + "/codes.json");
        List<Path> cases = getAllFiles("src/test/resources/asts");
        for (Path testCase : cases) {
            String jsonPath = testCase.toString();
            System.out.println(jsonPath);
            String code = formatter.format(astBuilder.buildFromJson(jsonPath));
            assertEquals(codes.get(extractFileNameWithoutExtension(testCase)), code);
        }
    }

    private List<Path> getAllFiles(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
    }

    private String extractFileNameWithoutExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }
}
