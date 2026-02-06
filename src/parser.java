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

    static HashMap<String, String[]> parseConfig(String id) throws IOException {
        //path is hardcoded in, I know I know
        Path configPath = Paths.get("C:\\Users\\ethan\\Documents\\Github\\cs416project1\\src\\config.txt");
        String fileString;
        BufferedReader br = new BufferedReader(new FileReader(configPath.toFile()));
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

        String[] lines = fileString.split(System.lineSeparator());

        for(String line : lines){
            if(line.isBlank()) continue; //if blank, who cares keep pushin
            String[] parts = line.split(";");

            if (!parts[0].equals(id)) continue;

            if(parts.length == 3){
                idHash.put("IP", new String[]{ parts[1] });
                idHash.put("Port", new String[]{ parts[2] });
            } else if (parts.length == 2) {
                idHash.put("Links", parts[1].split(","));
            }//end else-if
        }//end for

        /*
        String[] strArr = fileString.split(id+";");
        for (String str : strArr) {
            if (str.equals(id)) {
                if ((str+1).contains(".")) {
                    idHash.put("IP", str+1);
                    idHash.put("Port", str+2);
                } else {
                    idHash.put("Links", str+1);//needs tweaking
                }
            }
        }

         */
        return idHash;
    }//end parseConfig

    public static void main(String[] args) throws IOException {
        HashMap<String, String[]> result = parseConfig("B");
        for (String key : result.keySet()) {
            System.out.println(key + " = " + Arrays.toString(result.get(key)));
        }

    }//endMain
}//end Parser Interface