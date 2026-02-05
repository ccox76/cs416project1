import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays; 
import java.util.HashMap;

public interface parser {
    //functions: getRouteAndSplitMessage -> Take IDSender, IDRecipient, Message divide by commas and return array
    static String[] getRouteFromMessage(String message){
        String[] tempArray = message.split(",");
        tempArray[0] = tempArray[0].trim();
        tempArray[1] = tempArray[1].trim();
        tempArray[2] = tempArray[2].trim();

        //System.out.println(Arrays.toString(tempArray));
        return tempArray;
    }

    static HashMap<String, String[]> parseConfig(String id) {
        Path configPath = Paths.get("src/config.txt");
        String fileString;
        BufferedReader br = new BufferedReader(new FileReader(configPath));
        try {
            StringBuilder sb = new StringBuilder();
            String currLine = br.readLine();

            while (currLine != null) {
                sb.append(currLine);
                sb.append(System.lineSeparator());
                currLine = br.readLine();
            }
            fileString = sb.toString();
        } finally {
            br.close();
        }
        HashMap idHash = new HashMap<String, String[]>();


            
        return idHash;
    }
}