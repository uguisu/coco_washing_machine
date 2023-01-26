package com.uguisu.github;

import com.google.gson.Gson;
import com.uguisu.github.entity.CocoEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * CocoWashingMachineApp
 * @author kakin
 */
public class CocoWashingMachineApp {

    public static void main(String[] args) {

        // verify
        assert 2 == args.length;

        String fileName = args[0];
        String outputFileName = args[1];

        StringBuilder sb = new StringBuilder();

        try {
            List<String> lists = Files.readAllLines(Paths.get(fileName));

            for(String ln : lists) {
                sb.append(ln);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        CocoEntity cocoEntity = gson.fromJson(sb.toString(), CocoEntity.class);

        CleanSingleDataSet cleanSingleDataSet = new CleanSingleDataSet(cocoEntity);
        cleanSingleDataSet.displayStatus();
        CocoEntity cleanEntity = cleanSingleDataSet.clean();
        String output = gson.toJson(cleanEntity);

        try {
            Files.writeString(Paths.get(outputFileName), output, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
