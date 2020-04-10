package serveur;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matière.UE;
import user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class UtilServeur {

    /**
     * Fais une hashmap avec les code de matières comme clé et l'Objet UE qui correspond à la matière
     * @param listUE La liste des UE
     * @return HashMap de type <"CodeUE", UE>
     */
    public static final HashMap<String, UE> initDictUE(ArrayList<UE> listUE) {
        HashMap<String, UE> dicoUE = new HashMap<>();
        if (listUE != null) {
            for (UE ue : listUE) {
                dicoUE.put(ue.getCode(), ue);
            }
            return dicoUE;
        }
        return dicoUE;
    }

    /**initialisation de la liste de toutes les matières
     * @param list Liste de Arraylist d'UE*/
    public static final ArrayList<UE> initListeUE(Object... list){
        ArrayList<UE> listUE = new ArrayList<>();
        if (list != null) {
            for (Object l: list){
                if (l != null) {
                    listUE.addAll((ArrayList<UE>) l);
                }
            }
            return listUE;
        }
        return listUE;
    }


    /**
     * Cherche si un utilisateur s'est deja connecté une fois
     * @param listUsers Une liste d'Users
     * @param user Un User
     * @return Un booléen vrai si l'user est dans la liste, faux dans le cas contraire
     */
    public static final boolean userExist(ArrayList<User> listUsers, User user) {
        if (listUsers == null) {
            return false;
        }
        if (user == null) {
            return false;
        }
        for (User utilisateur: listUsers) {
            if (utilisateur.equals(user)){
                return true;
            }
        }
        return false;
    }


    /**
     * crée un fichier nomDefichier.json (dans serveur/target/generated-sources/ ), convertit l'objet en json dans le fichier et le renvoie .
     * @param nomDeFichier nom du ficher json (Ajouter l'extension .json !!)
     * @param object l'objet à convertir
     * @return Le fichier json créé
     */
    public static final  File writeToJSON(String nomDeFichier, Object object){
        File file = new File(Serveur.PATH_JSON_FILES+nomDeFichier);
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("Le fichier "+nomDeFichier+" a été créé avec succès !");
//            else {
//                System.out.println(nomDeFichier+".json existe et sera écrasé");
//            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, object);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static final ArrayList<UE> JSONFileToListUE(File fileName){
        ArrayList<UE> ueArrayList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            ueArrayList = mapper.readValue(fileName, new TypeReference<ArrayList<UE>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ueArrayList;
    }
    public static final ArrayList<User> JSONFileToListUsers(File fileName){
        ArrayList<User> userArrayList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            userArrayList = mapper.readValue(fileName, new TypeReference<ArrayList<User>>(){});
        } catch (IOException e) {
            System.out.println(fileName+" existe mais est vide !");
            //e.printStackTrace();
        }
        return userArrayList;
    }

    /**
     * Renvoie l'index de l'User dans userArrayList avec son champs adresse ip égale à ip
     * Renvoie -1 si l'user n'est pas dans l'ArrayList
     * @param ip String address ip, l'adresse ip de l'user dont on cherche l'index
     * @param userArrayList ArrayList des users où on cherche l'index
     * @return
     */
    public static final int getIndexUser(String ip, ArrayList<User> userArrayList){
        int index = 0;
        for (User user: userArrayList) {
            if (user.getAddress_ip().equals(ip)){
                return index;
            }
            else if (index == userArrayList.size()-1){
                return -1;
            }
            index++;
        }
        return index;
    }

}
