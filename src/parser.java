import java.io.File;
import java.util.Arrays; //Using to look at tempArray


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
    
    static String[] getAddressFromConfig(String id, File config) {
        
        
        return [IPAddr, portNum];
    }

    static String[] getLinksFromConfig(String id, File config) {
        
        
        return linksList;
    }
}