package com.dagdevelop.workTime.util;

import com.dagdevelop.workTime.model.Activity;
import com.dagdevelop.workTime.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {
    private static final String[] paysDuMonde = new String[]{"Afrique du Sud", "Afghanistan", "Albanie", "Algérie", "Allemagne", "Andorre",
            "Angola", "Antigua-et-Barbuda", "Arabie Saoudite", "Argentine", "Arménie", "Australie", "Autriche", "Azerbaïdjan",
            "Bahamas", "Bahreïn", "Bangladesh", "Barbade", "Belgique", "Belize", "Bénin", "Bhoutan", "Biélorussie", "Birmanie",
            "Bolivie", "Bosnie-Herzégovine", "Botswana", "Brésil", "Brunei","Bulgarie", "Burkina Faso", "Burundi", "Cambodge",
            "Cameroun", "Canada", "Cap-Vert", "Chili", "Chine", "Chypre", "Colombie", "Comores", "Corée du Nord", "Corée du Sud", "Costa Rica", "Côte d’Ivoire",
            "Croatie", "Cuba", "Danemark", "Djibouti", "Dominique", "Égypte", "Émirats arabes unis", "Équateur", "Érythrée",
            "Espagne", "Eswatini", "Estonie", "États-Unis", "Éthiopie", "Fidji", "Finlande", "France", "Gabon", "Gambie", "Géorgie",
            "Ghana", "Grèce","Grenade", "Guatemala", "Guinée", "Guinée équatoriale", "Guinée-Bissau", "Guyana", "Haïti", "Honduras", "Hongrie", "Îles Cook", "Îles Marshall", "Inde", "Indonésie", "Irak",
            "Iran", "Irlande", "Islande", "Israël", "Italie", "Jamaïque", "Japon", "Jordanie", "Kazakhstan", "Kenya", "Kirghizistan",
            "Kiribati", "Koweït", "Laos", "Lesotho", "Lettonie", "Liban", "Liberia", "Libye", "Liechtenstein", "Lituanie", "Luxembourg",
            "Macédoine", "Madagascar", "Malaisie", "Malawi", "Maldives", "Mali", "Malte", "Maroc", "Maurice", "Mauritanie", "Mexique",
            "Micronésie", "Moldavie", "Monaco", "Mongolie", "Monténégro", "Mozambique", "Namibie", "Nauru", "Népal", "Nicaragua",
            "Niger", "Nigeria", "Niue", "Norvège", "Nouvelle-Zélande", "Oman", "Ouganda", "Ouzbékistan", "Pakistan", "Palaos", "Palestine",
            "Panama", "Papouasie-Nouvelle-Guinée", "Paraguay", "Pays-Bas", "Pérou", "Philippines", "Pologne", "Portugal", "Qatar",
            "République centrafricaine", "République démocratique du Congo", "République Dominicaine", "République du Congo (Brazzavile)",
            "République tchèque", "Roumanie", "Royaume-Uni", "Russie", "Rwanda", "Saint-Kitts-et-Nevis", "Saint-Vincent-et-les-Grenadines",
            "Sainte-Lucie", "Saint-Marin", "Salomon", "Salvador", "Samoa", "São Tomé-et-Principe", "Sénégal", "Serbie", "Seychelles",
            "Sierra Leone", "Singapour", "Slovaquie", "Slovénie", "Somalie","Soudan", "Soudan du Sud", "Sri Lanka", "Suède", "Suisse",
            "Suriname", "Syrie", "Tadjikistan", "Tanzanie", "Tchad", "Thaïlande", "Timor oriental", "Togo", "Tonga", "Trinité-et-Tobago",
            "Tunisie", "Turkménistan", "Turquie", "Tuvalu", "Ukraine", "Uruguay", "Vanuatu", "Vatican", "Venezuela", "Viêt Nam",
            "Yémen", "Zambie", "Zimbabwe"
    };

    private static final String [] nationalités = new String[]{
            "Afghane", "Albanaise", "Algérienne", "Allemande", "Americaine", "Andorrane", "Angolaise", "Antiguaise-et-Barbudienne", "Argentine",
            "Armenienne", "Australienne", "Autrichienne", "Azerbaïdjanaise", "Bahamienne (Bahamas)", "Bahreinienne (Bahreïn)", "Bangladaise", "Barbadienne",
            "Belge", "Belizienne", "Béninoise", "Bhoutanaise", "Biélorusse", "Birmane", "Bissau-Guinéenne", "Bolivienne", "Bosnienne", "Botswanaise", "Brésilienne",
            "Britannique", "Brunéienne", "Bulgare", "Burkinabée", "Burundaise", "Cambodgienne", "Camerounaise", "Canadienne", "Cap-verdienne", "Centrafricaine",
            "Chilienne", "Chinoise", "Chypriote", "Colombienne", "Comorienne", "Congolaise (Congo-Brazzaville)",  "Congolaise (Congo-Kinshasa)", "Cookienne", "Costaricaine",
            "Croate", "Cubaine", "Danoise", "Djiboutienne", "Dominicaine", "Dominiquaise", "Égyptienne", "Émirienne", "Équato-guineenne", "Équatorienne", "Érythréenne", "Espagnole",
            "Est-timoraise", "Estonienne", "Éthiopienne", "Fidjienne", "Finlandaise", "Française", "Gabonaise", "Gambienne", "Georgienne", "Ghanéenne", "Grenadienne", "Guatémaltèque",
            "Guinéenne", "Guyanienne", "Haïtienne", "Hellénique", "Hondurienne", "Hongroise", "Indienne", "Indonésienne", "Irakienne", "Iranienne", "Irlandaise", "Irlandaise",
            "Islandaise", "Israélienne", "Italienne", "Ivoirienne", "Jamaïcaine", "Japonaise", "Jordanienne", "Kazakhstanaise", "Kenyane", "Kirghize", "Kiribatienne",
            "Kittitienne et Névicienne", "Koweïtienne", "Laotienne", "Lesothane", "Lettone", "Libanaise", "Libérienne", "Libyenne", "Liechtensteinoise", "Lituanienne",
            "Luxembourgeoise", "Macédonienne", "Malaisienne", "Malawienne", "Maldivienne", "Malgache", "Maliennes", "Maltaise", "Marocaine", "Marshallaise", "Mauricienne", "Mauritanienne",
            "Mexicaine", "Micronésienne", "Moldave", "Monegasque", "Mongole", "Monténégrine", "Mozambicaine", "Namibienne", "Nauruane", "Néerlandaise", "Néo-Zélandaise", "Népalaise",
            "Nicaraguayenne", "Nigériane", "Nigérienne", "Niuéenne", "Nord-coréenne", "Norvégienne", "Omanaise", "Ougandaise", "Ouzbéke", "Pakistanaise", "Palaosienne", "Palestinienne",
            "Panaméenne", "Papouane-Néo-Guinéenne", "Paraguayenne", "Péruvienne", "Philippine", "Polonaise", "Portugaise", "Qatarienne", "Roumaine", "Russe", "Rwandaise", "Saint-Lucienne",
            "Saint-Marinaise", "Saint-Vincentaise et Grenadine", "Salomonaise", "Salvadorienne", "Samoane", "Santoméenne", "Saoudienne", "Sénégalaise", "Serbe", "Seychelloise", "Sierra-Léonaise",
            "Sierra-Léonaise", "Slovaque", "Slovène", "Somalienne", "Soudanaise", "Sri-Lankaise", "Sud-Africaine", "Sud-Coréenne", "Sud-Soudanaise", "Suédoise", "Suisse", "Surinamaise",
            "Swazie", "Syrienne", "Tadjike", "Tanzanienne", "Tchadienne", "Tchèque", "Thaïlandaise", "Togolaise", "Tonguienne", "Trinidadienne", "Tunisienne", "Turkmène", "Turque", "Tuvaluane",
            "Ukrainienne", "Uruguayenne", "Vanuatuane", "Vaticane", "Vénézuélienne", "Vietnamienne", "Yéménite", "Zambienne", "Zimbabwéenne"
    };
    public static String RACINE_DIRECTORY = "C:/WorkTime_1.0";
    public static String USER_FILE = "/users_infos.txt";
    public static String ACTIVITY_FILE = "/activities_data";
    public static User admin = new User("vially418", "Vially", "Dag", "Jesuis418", "dagdevelopper@gmail.com", "Cameroun", "Camerounais", "Homme", true);

    private static final String[] genres = new String[]{"Homme", "Femme"};
    public static final String DAGDEV_EMAIL = "dagdevelopper@gmail.com";

    public static String[] pays(){
        return paysDuMonde;
    }
    public static String [] nationalités(){
        return nationalités;
    }
    public static String [] getGenres(){
        return genres;
    }

    public static String [] days = new String[] {
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
    };

    public static String PATH_IMAGE = "src/com/dagdevelop/workTime/images/";

    public static String [] months = new String[] {
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"
    };

    public static String addZero(int nb){
        return (nb > 9 ? "" : "0") + nb;
    }

    public static int getPosition (String [] array, String key){
        int  i = 0;
        while (i < array.length && !key.equalsIgnoreCase(array[i])){
            i++;
        }
        return i;
    }

    public static final String VALID_EMAIL_PATTERN =  "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
            "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|" +
            "[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-" +
            "\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static final String VALID_LAST_NAME_PATTERN = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])$";

    public static boolean isValidEmail(String email){
        return email.matches(VALID_EMAIL_PATTERN);
    }

    public static boolean isValidName(String name){
        return name.matches(VALID_LAST_NAME_PATTERN);
    }

    public static void defaultScreenDimension(JPanel panel){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setBounds(screenSize.width/10,screenSize.height/4,4 * screenSize.width/5, 2* screenSize.height/3);
    }

    public static boolean adminExists (ArrayList<User> users){
       int i = 0;
       while (i < users.size() && (!users.get(i).getUsername().equals(admin.getUsername()) && !users.get(i).getEmail().equals(admin.getEmail()))){
           i++;
       }
       return i < users.size();
    }

    public static Font fontTextField (int taille){
        return new Font("Arial Rounded MT Bold", Font.ITALIC, taille);
    }

    public static Font fontTitle (int taille){
        return new Font("Arial Rounded MT Bold", Font.PLAIN, taille);
    }
    public static Font fontTitle (int taille, String fontName){
        return new Font(fontName, Font.BOLD, taille);
    }

    public static void setColorAndFontButton(JButton button, Color bgColor, Color fgColor, Font font){
        if (button != null){
            if (bgColor != null)
                button.setBackground(bgColor);
            if (fgColor != null)
                button.setForeground(fgColor);
            if (font != null)
                button.setFont(font);
        }
    }

    public static String percentage(Activity activity, String jour, String mois){
        int nbFois = 0;
        int nbFoisTotal = 0;
        HashMap<String, Integer> nombreFois = new HashMap<>();

        if (mois != null) {
            nombreFois = activity.getMetaData().getStatisticsMonth().getNombreFois();
            if (nombreFois.containsKey(mois))
                nbFois = activity.getMetaData().getStatisticsMonth().getNombreFois().get(mois);
            else
                nbFois = 0;
        }else if (jour != null) {

            nombreFois = activity.getMetaData().getStatisticsDay().getNombreFois();
            if (nombreFois.containsKey(jour))
                nbFois = activity.getMetaData().getStatisticsDay().getNombreFois().get(jour);
            else
                nbFois = 0;
        }

        for (int nb : nombreFois.values()){
            nbFoisTotal += nb;
        }
        String percent = String.format("%.2f", (nbFois / (double)nbFoisTotal) * 100) + " %" ;
        return nbFois != 0 ? percent: "0.00 %";
    }

    public static String percentValue (int nbFois, int total){
        String percent = String.format("%.2f", (nbFois / (double)total) * 100) + " %" ;
        return nbFois != 0 ? percent: "0.00 %";
    }


}
