import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        VolDAO dao = new VolDAO();
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Afficher tous les vols");
            System.out.println("2. Ajouter un nouveau vol");
            System.out.println("3. Modifier la destination d’un vol");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");
            while (!scanner.hasNextInt()) {
                System.out.print("Entrée invalide. Veuillez entrer un nombre : ");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("\n Liste des vols :");
                    dao.getAllVols().forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("Numéro du vol : ");
                    String numero = scanner.nextLine();
                    System.out.print("Ville de départ : ");
                    String depart = scanner.nextLine();
                    System.out.print("Ville d’arrivée : ");
                    String arrivee = scanner.nextLine();

                    Vol vol = new Vol(numero, depart, arrivee);
                    dao.ajouterVol(vol);
                    break;

                case 3:
                    System.out.print("Numéro du vol à modifier : ");
                    String numVol = scanner.nextLine();
                    Vol volExistant = dao.chercherVol(numVol);
                    if (volExistant != null) {
                        System.out.println("Vol trouvé : " + volExistant);
                        System.out.print("Nouvelle ville d’arrivée : ");
                        String nouvelleDestination = scanner.nextLine();
                        dao.modifierDestination(numVol, nouvelleDestination);
                    } else {
                        System.out.println("Ce vol n'existe pas.");
                    }
                    break;

                case 4:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println(" Choix invalide ! Veuillez réessayer.");
            }

        } while (choix != 4);

        scanner.close();
    }
}
